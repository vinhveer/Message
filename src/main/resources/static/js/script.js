function resetForm() {
    // Lấy danh sách tất cả các trường input trong form
    const inputFields = document.querySelectorAll('input');

    // Duyệt qua từng trường input và đặt giá trị của nó thành rỗng
    inputFields.forEach(function (input) {
        input.value = '';
    });
}

function validateForm() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Ngăn chặn gửi form mặc định

        const username = document.getElementById('username').value;
        const email = document.getElementById('email').value;
        const password1 = document.getElementById('password1').value;
        const password2 = document.getElementById('password2').value;

        // Kiểm tra tên đăng nhập không được rỗng và có ít nhất 4 ký tự
        if (username.trim() === '' || username.length < 4) {
            alert('Tên đăng nhập phải có ít nhất 4 ký tự');
            return;
        }

        // Kiểm tra email có đúng cú pháp không
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            alert('Email không hợp lệ');
            return;
        }

        // Kiểm tra mật khẩu không được rỗng và có ít nhất 8 ký tự
        if (password1.trim() === '' || password1.length < 8) {
            alert('Mật khẩu phải có ít nhất 8 ký tự');
            return;
        }

        // Kiểm tra mật khẩu nhập lại trùng khớp với mật khẩu
        if (password1 !== password2) {
            alert('Mật khẩu nhập lại không khớp');
            return;
        }

        // Nếu các điều kiện kiểm tra qua, thực hiện gửi form
        alert('Đăng ký thành công!');
        form.submit();
    });
};

