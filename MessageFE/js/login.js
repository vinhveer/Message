document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    // Ngăn chặn hành vi mặc định của form
    event.preventDefault();

    // Lấy dữ liệu từ form
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Tạo đối tượng request
    const requestData = {
      email: email,
      password: password,
    };

    // Thực hiện fetch API
    fetch("http://localhost:8080/auth/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestData),
    })
      .then((response) => {
        if (!response.ok || response.status !== 200) {
          throw new Error(
            '<div class="alert alert-danger" role="alert"><strong>Thông báo!</strong> Email hoặc mật khẩu không đúng.</div>'
          );
        }
        return response.json();
      })
      .then((data) => {
        if (data && data.token) {
          // Lưu token vào Storage
          localStorage.setItem("token", data.token);

          // Chuyển hướng trang
          window.location.href = "app/index.html";
        } else {
          // Hiển thị thông báo lỗi
          document.querySelector(".notification").innerHTML =
            '<div class="alert alert-danger" role="alert"><strong>Thông báo!</strong> Email hoặc mật khẩu không đúng.</div>';
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        document.querySelector(".notification").innerHTML = error.message;

        // Xóa token khỏi Local Storage
        localStorage.removeItem("token");
      });
  });
