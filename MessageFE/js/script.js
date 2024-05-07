document
  .getElementById("register")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password1 = document.getElementById("password1").value;
    var password2 = document.getElementById("password2").value;
    var file = document.getElementById("file").files[0];

    if (password1 !== password2) {
      alert("Mật khẩu không khớp!");
      return;
    }

    var formData = new FormData();
    formData.append("file", file);

    fetch("http://localhost:8080/upload", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Lỗi khi tải lên ảnh");
        }
        // console.log(response.text());
        return response.text();
      })
      .then((avatar) => {
        var registerData = {
          fullName: username,
          gender: false,
          dateOfBirth: "2004-07-01",
          email: email,
          avatar: avatar,
          password: password1,
        };

        return fetch("http://localhost:8080/auth/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(registerData),
        });
      })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Lỗi khi đăng ký");
        }
        return response.json();
      })
      .then((data) => {
        // alert('Đăng ký thành công!');
        localStorage.setItem("token", data.token);
        // Chuyển hướng sang trang đăng nhập
        window.location.href = "login.html";
      })
      .catch((error) => {
        console.error("Error:", error);
        alert(error.message);
      });
  });
