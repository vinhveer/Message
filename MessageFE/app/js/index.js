//UI LOGIC
const chatArea = document.querySelector(".messages");
if (chatArea) {
  document.querySelector(".btn-search").addEventListener("click", function () {
    document.querySelector(".input-search").focus();
  });

  document.getElementById("toggleInfo").addEventListener("click", function () {
    const info = document.querySelector(".info");
    if (window.innerWidth > 768 && document.querySelector(".userChat.active")) {
      info.classList.toggle("active");
    } else {
      info.style.display = info.style.display === "block" ? "none" : "block";
    }
  });
}

document.querySelectorAll(".btn-switch").forEach((btn) => {
  btn.addEventListener("click", function () {
    document
      .querySelectorAll(".btn-switch")
      .forEach((b) => b.classList.remove("active"));
    this.classList.add("active");

    const spanText = this.querySelector("span").textContent;
    if (spanText === "Chat") {
      fetchData()
        .then(() => {
          document.getElementById("chats").style.display = "block";
          document.getElementById("contacts").style.display = "none";
        })
        .catch((error) => console.error(error));
    } else if (spanText === "Contacts") {
      document.getElementById("chats").style.display = "none";
      document.getElementById("contacts").style.display = "block";
    } else {
      document.getElementById("chats").style.display = "none";
      document.getElementById("contacts").style.display = "none";
    }
  });
});

const btnsFriend = document.getElementsByClassName("btns-friend");

for (let i = 0; i < btnsFriend.length; i++) {
  btnsFriend[i].addEventListener("mouseover", function (event) {
    event.target.closest(".user-friend").style.backgroundColor = "transparent";
  });
  btnsFriend[i].addEventListener("mouseout", function (event) {
    event.target.closest(".user-friend").style.backgroundColor = "";
  });
}

let logoutButton = document.querySelector(".dropdown-menu a:last-child");

logoutButton.addEventListener("click", function (event) {
  event.preventDefault();

  localStorage.removeItem("token");

  window.location.href = "../login.html";
});
//End UI LOGIC

//FETCH API
function fetchApi(url, method = "GET", body = null) {
  let token = localStorage.getItem("token");

  let headers = new Headers();
  headers.append("Authorization", "Bearer " + token);

  let options = {
    method: method,
    headers: headers,
  };

  if (body) {
    options.body = JSON.stringify(body);
    headers.append("Content-Type", "application/json");
  }

  let request = new Request(url, options);

  return fetch(request)
    .then((response) => response.json())
    .catch((error) => console.error("Error:", error));
}

function fetchApiAsText(url, method = "GET", body = null) {
  let token = localStorage.getItem("token");

  let headers = new Headers();
  headers.append("Authorization", "Bearer " + token);

  let options = {
    method: method,
    headers: headers,
  };

  if (body) {
    options.body = JSON.stringify(body);
    headers.append("Content-Type", "application/json");
  }

  let request = new Request(url, options);

  return fetch(request)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.text();
    })
    .catch((error) => console.error("Error:", error));
}

function parseJwt(token) {
  let base64Url = token.split(".")[1];
  let base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  let jsonPayload = decodeURIComponent(
    atob(base64)
      .split("")
      .map(function (c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join("")
  );

  return JSON.parse(jsonPayload);
}

let token = localStorage.getItem("token");

let email = parseJwt(token).sub;

let currentUserId;

async function displayChatInfo(friendId) {
  const friend = await fetchApi(
    `http://localhost:8080/user/get_id?userId=${friendId}`
  );

  const chatInfoText = document.querySelector(".chatInfo-text");
  const nameSpan = chatInfoText.children[0];
  // const statusSpan = chatInfoText.children[1];
  const chatInfoAvatar = document.querySelector(".chatInfo-avt img");

  nameSpan.textContent = friend[0].fullName;
  // statusSpan.textContent = friend.enabled ? "Đang hoạt động" : "Không hoạt động";

  chatInfoAvatar.src = friend[0].avatar
    ? friend[0].avatar
    : "./img/noavatar.png";
}

// Fetch thông tin người dùng từ server
async function fetchData() {
  const user = await fetchApi(
    `http://localhost:8080/user/get_email?email=${email}`
  );

  document.querySelector(".nav-user span").textContent = user.fullName;
  document.querySelector(".nav-user img").src = user.avatar
    ? user.avatar
    : "img/noavatar.png";

  currentUserId = user.id;

  const friendList = await fetchApi(
    `http://localhost:8080/friendship/friends/${currentUserId}`
  );

  const friendDetails = await Promise.all(
    friendList.map((friend) => {
      const friendId =
        friend.receiverId === currentUserId
          ? friend.senderId
          : friend.receiverId;
      return fetchApi(`http://localhost:8080/user/get_id?userId=${friendId}`);
    })
  );

  const chatsContainer = document.querySelector("#chats");

  chatsContainer.innerHTML = "";

  if (friendDetails.length === 0) {
    return;
  }

  const firstFriend = friendDetails[0];
  let friendName = document.querySelector(".friend-name");
  let friendAvatar = document.querySelector(".friend-avt img");
  friendName.textContent = firstFriend[0].fullName;
  friendAvatar.src = firstFriend[0].avatar
    ? firstFriend[0].avatar
    : "./img/noavatar.png";

  displayChatInfo(firstFriend[0].id);
  displayChatMessages(firstFriend[0].id, firstFriend[0].avatar);

  // Thêm từng bạn bè vào danh sách
  friendDetails.forEach(async (friend, index) => {
    let friendElement = document.createElement("div");
    friendElement.classList.add("userChat");
    if (friendDetails.length > 0 && index === 0) {
      friendElement.classList.add("active");
    }

    friendElement.addEventListener("click", function () {
      document.querySelectorAll(".userChat.active").forEach((activeElement) => {
        activeElement.classList.remove("active");
      });

      this.classList.add("active");

      displayChatInfo(friend[0].id);
      displayChatMessages(friend[0].id, friend[0].avatar);

      friendName.textContent = friend[0].fullName;
      friendAvatar.src = friend[0].avatar
        ? friend[0].avatar
        : "./img/noavatar.png";
    });
    if (friend[0]) {
      friendElement.dataset.friendId = friend[0].id;

      let imgElement = document.createElement("div");
      imgElement.classList.add("userChat-img");
      imgElement.classList.add("online");

      let imgTag = document.createElement("img");
      imgTag.src = friend[0].avatar ? friend[0].avatar : "./img/noavatar.png";
      imgTag.alt = "User Photo";

      imgElement.appendChild(imgTag);

      let infoElement = document.createElement("div");
      infoElement.classList.add("userChatInfo");

      let nameElement = document.createElement("span");
      nameElement.classList.add("userChatInfo-name");
      nameElement.textContent = friend[0].fullName;

      const conservation = await getConservation(friend[0].id);

      const messages = await fetchApi(
        `http://localhost:8080/message/get?conversationId=${conservation.id}`
      );

      let lastMessage = messages[messages.length - 1];

      let textElement = document.createElement("div");
      textElement.classList.add("userChatInfo-text");

      let p1 = document.createElement("p");
      p1.textContent = lastMessage ? lastMessage.content : "Chưa có tin nhắn";

      let messageDate = new Date(lastMessage.timestamp);

      let p2 = document.createElement("p");
      p2.textContent = messageDate.toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit",
      });

      textElement.appendChild(p1);
      textElement.appendChild(p2);

      infoElement.appendChild(nameElement);
      infoElement.appendChild(textElement);

      friendElement.appendChild(imgElement);
      friendElement.appendChild(infoElement);

      chatsContainer.appendChild(friendElement);
    }
  });

  if (friendDetails.length === 0) {
    document.querySelector(".info").classList.remove("active");
  } else {
    document.querySelector(".info").classList.add("active");
  }
}

fetchData().catch((error) => console.error(error));

// SEND MESSAGE
var messageInput = document.getElementById("i");
var sendButton = document.querySelector(".input button");

sendButton.addEventListener("click", async function (event) {
  event.preventDefault(); // Ngăn form gửi đi

  // Kiểm tra xem một cuộc trò chuyện có được chọn không
  var activeUserChat = document.querySelector(".userChat.active");
  if (!activeUserChat) {
    alert("Vui lòng chọn một cuộc trò chuyện để gửi tin nhắn.");
    return;
  }

  // Lấy nội dung tin nhắn từ input
  var message = messageInput.value;
  if (message === "") {
    alert("Vui lòng nhập nội dung tin nhắn.");
    return;
  }

  // Lấy id của người nhận tin nhắn
  var friendId = activeUserChat.dataset.friendId;

  const conservation = await getConservation(friendId);

  // console.log(conservation);
  // Tạo đối tượng tin nhắn mới
  var newMessage = {
    conservationId: conservation.id,
    userId: currentUserId,
    type: "TEXT",
    content: message,
  };

  fetch("http://localhost:8080/message/send", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      authorization: "Bearer " + localStorage.getItem("token"),
    },
    body: JSON.stringify(newMessage),
  })
    .then((response) => {
      if (response.ok) {
        messageInput.value = "";
        //tà đạo
        // displayChatMessages(friendId, activeUserChat.dataset.friendAvatar);
      } else {
        alert("Không thể gửi tin nhắn. Vui lòng thử lại sau.");
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("Không thể gửi tin nhắn.");
    });
});

//Get conversation
async function getConservation(friendId) {
  const conservations = await fetchApi(
    `http://localhost:8080/conversation/getByUserId?userId=${currentUserId}`
  );

  const conservation = conservations.filter((c) =>
    c.participantId.includes(friendId)
  );

  return conservation[0];
}

//DISPLAY MESSAGE
async function displayChatMessages(friendId, friendAvatar) {
  // console.log(friendId);
  const conservation = await getConservation(friendId);
  // console.log(conservation.id, friendId);
  const messages = await fetchApi(
    `http://localhost:8080/message/get?conversationId=${conservation.id}`
  );

  const messagesContainer = document.querySelector(".messages");

  messagesContainer.innerHTML = "";

  messages.forEach((message) => {
    const messageDiv = document.createElement("div");
    messageDiv.classList.add("message");

    const messageInfoDiv = document.createElement("div");
    messageInfoDiv.classList.add("messageInfo");

    const avatarImg = document.createElement("img");
    avatarImg.src = friendAvatar ? friendAvatar : "./img/noavatar.png";
    avatarImg.alt = "User Photo";

    messageInfoDiv.appendChild(avatarImg);
    messageDiv.appendChild(messageInfoDiv);

    const messageContentDiv = document.createElement("div");
    messageContentDiv.classList.add("messageContent");

    const contentParagraph = document.createElement("p");
    contentParagraph.textContent = message.content;

    const timestampSpan = document.createElement("span");
    const messageDate = new Date(message.timestamp);
    timestampSpan.textContent = messageDate.toLocaleTimeString([], {
      hour: "2-digit",
      minute: "2-digit",
    });

    messageContentDiv.appendChild(contentParagraph);
    messageContentDiv.appendChild(timestampSpan);

    messageDiv.appendChild(messageContentDiv);
    if (message.userId === currentUserId) {
      messageDiv.classList.add("owner");
    }

    // Thêm tin nhắn vào phần tử gốc
    messagesContainer.prepend(messageDiv);
  });

  messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

//SEARCH USER
var searchInput = document.getElementById("search");
var userNotFound = document.getElementById("userNotFound");
var findUserContainer = document.querySelector(".find-user");

searchInput.addEventListener("input", async function () {
  var searchValue = this.value;

  if (searchValue === "") {
    findUserContainer.innerHTML = "";
    findUserContainer.style.display = "none";
    userNotFound.style.display = "none";
    return;
  }

  var searchValueAtRequestTime = searchValue;

  try {
    const users = await fetchApi(
      `http://localhost:8080/user/search?keyword=${searchValue}`
    );
    const friends = await fetchApi(
      `http://localhost:8080/friendship/friends/${currentUserId}`
    );

    const friendIds = friends.map((friendship) => {
      return friendship.senderId === currentUserId
        ? friendship.receiverId
        : friendship.senderId;
    });

    const nonFriends = users.filter((user) => !friendIds.includes(user.id));

    if (searchInput.value !== searchValueAtRequestTime) {
      return;
    }

    findUserContainer.innerHTML = "";
    findUserContainer.style.display = "block";

    if (nonFriends.length === 0) {
      userNotFound.style.display = "block";
    } else {
      userNotFound.style.display = "none";

      nonFriends.forEach(async (user) => {
        if (user.id !== currentUserId) {
          var userFriendElement = document.createElement("div");
          userFriendElement.classList.add("user-friend");
          userFriendElement.dataset.userId = user.id;

          var userChatImgElement = document.createElement("div");
          userChatImgElement.classList.add("userChat-img");

          var imgElement = document.createElement("img");
          imgElement.src = user.avatar ? user.avatar : "img/noavatar.png";
          imgElement.alt = "User Photo";

          var userChatInfoElement = document.createElement("div");
          userChatInfoElement.classList.add("userChatInfo");

          var userNameElement = document.createElement("span");
          userNameElement.classList.add("userChatInfo-name");
          userNameElement.textContent = user.fullName;

          var btnsFriendElement = document.createElement("div");
          btnsFriendElement.classList.add("btns-friend");

          var addButton = document.createElement("button");

          const sendRequest = await fetchApi(
            `http://localhost:8080/friendship/requests/${user.id}`
          );

          // console.log(sendRequest[0]);
          if (sendRequest[0] && sendRequest[0].status === "PENDING") {
            addButton.textContent = "Đã gửi";
            addButton.disabled = true;
            addButton.classList.add("btn-friend-decline");
          } else {
            addButton.textContent = "Thêm bạn";
            addButton.classList.add("btn-friend-accept");
          }

          addButton.addEventListener("click", function () {
            var receiverId =
              this.parentElement.parentElement.parentElement.dataset.userId;
            fetchApiAsText(
              `http://localhost:8080/friendship/request?senderId=${currentUserId}&receiverId=${receiverId}`,
              "POST"
            )
              .then((message) => {
                if (message === "Friend request sent successfully.") {
                  addButton.textContent = "Đã gửi";
                  addButton.disabled = true;
                  addButton.classList.add("btn-friend-decline");
                  console.log("Friend request sent successfully");
                } else if (
                  message ==
                  "A friendship or request already exists between these users."
                ) {
                  addButton.textContent = "Đã gửi";
                  addButton.disabled = true;
                  addButton.classList.add("btn-friend-decline");
                } else {
                  alert("Không thể gửi yêu cầu kết bạn. Vui lòng thử lại sau.");
                }
              })
              .catch((error) => console.error("Error:", error));
          });

          var deleteButton = document.createElement("button");
          deleteButton.textContent = "Xóa";
          deleteButton.classList.add("btn-friend-decline");

          btnsFriendElement.appendChild(addButton);
          btnsFriendElement.appendChild(deleteButton);

          userChatImgElement.appendChild(imgElement);
          userChatInfoElement.appendChild(userNameElement);
          userChatInfoElement.appendChild(btnsFriendElement);
          userFriendElement.appendChild(userChatImgElement);
          userFriendElement.appendChild(userChatInfoElement);
          findUserContainer.appendChild(userFriendElement);
        }
      });
    }
  } catch (error) {
    console.error("Error:", error);
  }
});

//Fetch data for contacts
let contactsBtn = document.querySelector(".btn-switch:nth-child(3)");

contactsBtn.addEventListener("click", fetchAndDisplayContacts);

function fetchAndDisplayContacts() {
  var contacts = document.getElementById("contacts");
  contacts.innerHTML = "";

  contactsBtn.disabled = true;

  fetchApi(`http://localhost:8080/friendship/requests/${currentUserId}`)
    .then((requests) => {
      requests.forEach((request) => {
        fetchApi(`http://localhost:8080/user/get_id?userId=${request.senderId}`)
          .then((sender) => {
            var userFriendElement = document.createElement("div");
            userFriendElement.classList.add("user-friend");
            userFriendElement.dataset.userId = sender[0].id;

            var userChatImgElement = document.createElement("div");
            userChatImgElement.classList.add("userChat-img");

            var imgElement = document.createElement("img");
            imgElement.src = sender[0].avatar
              ? sender[0].avatar
              : "img/noavatar.png";
            userChatImgElement.appendChild(imgElement);

            var userChatInfoElement = document.createElement("div");
            userChatInfoElement.classList.add("userChatInfo");

            var nameElement = document.createElement("span");
            nameElement.classList.add("userChatInfo-name");
            nameElement.textContent = sender[0].fullName;
            userChatInfoElement.appendChild(nameElement);

            var btnsFriendElement = document.createElement("div");
            btnsFriendElement.classList.add("btns-friend");

            var addButton = document.createElement("button");
            addButton.textContent = "Chấp nhận";
            addButton.classList.add("btn-friend-accept");

            addButton.addEventListener("click", function () {
              var senderId =
                this.parentElement.parentElement.parentElement.dataset.userId;
              fetchApiAsText(
                `http://localhost:8080/friendship/accept?senderId=${senderId}&receiverId=${currentUserId}`,
                "POST"
              )
                .then((message) => {
                  if (
                    message === "friend request have been accepted successfully"
                  ) {
                    var newConversation = {
                      conservationName: "Chat",
                      participantId: [senderId, currentUserId],
                    };
                    console.log(JSON.stringify(newConversation));
                    fetch("http://localhost:8080/conversation/create", {
                      method: "POST",
                      headers: {
                        "Content-Type": "application/json",
                        authorization:
                          "Bearer " + localStorage.getItem("token"),
                      },
                      body: JSON.stringify(newConversation),
                    })
                      .then((response) => {
                        if (response.ok) {
                          userFriendElement.remove();
                          console.log("Conversation created successfully");
                        } else {
                          alert(
                            "Không thể tạo cuộc trò chuyện. Vui lòng thử lại sau."
                          );
                        }
                      })
                      .catch((error) => {
                        console.error("Error:", error);
                        alert("Không thể tạo cuộc trò chuyện.");
                      });
                  } else {
                    alert(
                      "Không thể chấp nhận yêu cầu kết bạn. Vui lòng thử lại sau."
                    );
                  }
                })
                .catch((error) => console.error("Error:", error));
            });

            btnsFriendElement.appendChild(addButton);

            var deleteButton = document.createElement("button");
            deleteButton.textContent = "Xóa";
            deleteButton.classList.add("btn-friend-decline");
            btnsFriendElement.appendChild(deleteButton);

            userChatInfoElement.appendChild(btnsFriendElement);

            userFriendElement.appendChild(userChatImgElement);
            userFriendElement.appendChild(userChatInfoElement);

            contacts.appendChild(userFriendElement);
          })
          .catch((error) => console.error("Error:", error));
      });
      return Promise.all(fetchPromises);
    })
    .catch((error) => {
      console.error("Error:", error);
    })
    .finally(() => {
      contactsBtn.disabled = false;
    });
}

//display info user
async function displayInfo(userId) {
  document.querySelector(".info-acc").style.display = "block";
  const user = await fetchApi(
    `http://localhost:8080/user/get_id?userId=${userId}`
  );

  console.log(user);
  document.querySelector(".info-avt-wrap span").textContent = user[0].fullName;
  document.querySelector(".avt-user img").src = user[0].avatar
    ? user[0].avatar
    : "img/noavatar.png";
  document.getElementById("dob").textContent = user[0].dateOfBirth;
  document.getElementById("gender").textContent = user[0].gender ? "Nam" : "Nữ";
  document.getElementById("email").textContent = user[0].email;
}

const infoUserBtn = document.querySelector("#info-user");
infoUserBtn.addEventListener("click", function () {
  displayInfo(currentUserId);
});

const infoFriendBtn = document.querySelector("#info-friend");
infoFriendBtn.addEventListener("click", function () {
  let friendId = document.querySelector(".userChat.active").dataset.friendId;
  displayInfo(friendId);
});

//close modal info-user
document.querySelector("#close-info").addEventListener("click", function () {
  document.querySelector(".info-acc").style.display = "none";
});
