
function showPassword(element) {

//  const show_password = document.getElementsByClassName("show-password")[0];
  const password = element.previousElementSibling;
  // toggle the type attribute
  const type =
    password.getAttribute("type") === "password" ? "text" : "password";
  password.setAttribute("type", type);
//  const password_icon = element.firstElementChild;

//  if (type === "password") password_icon.src = /*[[@{/img/eye-slash.svg}]]*/;
//  else password_icon.src = /*[[@{/img/eye.svg}]]*/;
}

function filterItems() {
  const menu = document.getElementById("menu");
  const search = document.getElementById("search").value.toUpperCase();
  const items = menu.getElementsByClassName("item");

  for (let i = 0; i < items.length; i++) {
    let item = items[i].getElementsByTagName("h3")[0].innerHTML.toUpperCase();
    if (item.includes(search)) {
      items[i].classList.add("d-flex");
      items[i].classList.remove("d-none");
    } else {
      items[i].classList.add("d-none");
      items[i].classList.remove("d-flex");
    }
  }
}

function closeResult() {
    const form_search = document.getElementsByClassName("form-search")[0];
    form_search.classList.add("d-none");
}

//document.getElementById("close-search").addEventListener("click", () => {
//    const form_search = document.getElementsByClassName("form-search")[0];
//    form_search.classList.add("d-none");
//});

function changePassword() {
//    event.preventDefault();
    let passwordForm = document.forms.changePasswordForm;
    let formData = new FormData(passwordForm);
    const password = formData.get('password');
    const confirmPassword = formData.get('confirmPassword');
    if (password == confirmPassword)
        return true;
    else
        return false;
}

$(document).ready(function () {
  const options = {
    animation: true,
    autohide: true,
    delay: 5000,
  };

  const toast = document.getElementById("toast");
  const bootstrap_toast = new bootstrap.Toast(toast, options);
  bootstrap_toast.show();
});
