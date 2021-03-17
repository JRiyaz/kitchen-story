function showPassword(currentTag) {
    const password = currentTag.previousElementSibling;
    // toggle the type attribute
    const type =
        password.getAttribute("type") === "password" ? "text" : "password";
    password.setAttribute("type", type);
    const password_icon = currentTag.firstElementChild;

    if (type === "password")
        password_icon.src = "@{/img/eye-slash.svg}";
    else password_icon.src = "@{/img/eye.svg}";
}

$(document).ready(function() {
    const options = {
        animation: true,
        autohide: true,
        delay: 5000,
    };

    const toast = document.getElementById("toast");
    const bootstrap_toast = new bootstrap.Toast(toast, options);
    bootstrap_toast.show();

});