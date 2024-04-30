const chatArea = document.querySelector(".messages");
chatArea.scrollTop = chatArea.scrollHeight;
document.querySelector(".btn-search").addEventListener("click", function () {
    document.querySelector(".input-search").focus();
});
document.getElementById("toggleInfo").addEventListener("click", function () {
    const info = document.querySelector(".info");
    if (window.innerWidth > 768) {
        info.classList.toggle("active");
    } else {
        info.style.display = info.style.display === "block" ? "none" : "block";
    }
});

document.querySelectorAll(".btn-switch").forEach((btn) => {
    btn.addEventListener("click", function () {
        document
            .querySelectorAll(".btn-switch")
            .forEach((b) => b.classList.remove("active"));
        this.classList.add("active");

        const spanText = this.querySelector("span").textContent;
        if (spanText === "Chat") {
            document.getElementById("chats").style.display = "block";
            document.getElementById("contacts").style.display = "none";
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
