const alertTitle = "Некоректні дані";
const alertColor = "#000000";
const validEmailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
const validPhoneRegex = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im

function validateAndSubmitForm() {
    let lat = document.getElementById("lat").value;
    let lng = document.getElementById("lng").value;
    let cap = document.getElementById("capacity").value;
    let area = document.getElementById("area").value;
    let status = document.getElementById("status").value;
    let conditions = document.getElementById("conditions").value;
    let additional = document.getElementById("additional").value;

    if (lat > 90) {
        Swal.fire({
            title: alertTitle,
            text: "Широта не може бути більшою 90°",
            confirmButtonColor: alertColor
        });
    } else if (lat < -90) {
        Swal.fire({
            title: alertTitle,
            text: "Широта не може бути меншою -90°",
            confirmButtonColor: alertColor
        });
    } else if (lng > 180) {
        Swal.fire({
            title: alertTitle,
            text: "Довгота не може бути більшою 180°",
            confirmButtonColor: alertColor
        });
    } else if (lng < -180) {
        Swal.fire({
            title: alertTitle,
            text: "Довгота не може бути меншою -180°",
            confirmButtonColor: alertColor
        });
    } else if (cap < 1) {
        Swal.fire({
            title: alertTitle,
            text: "Місткість повинна бути більшою від нуля",
            confirmButtonColor: alertColor
        });
    } else if (area <= 0) {
        Swal.fire({
            title: alertTitle,
            text: "Площа повинна бути більшою від нуля",
            confirmButtonColor: alertColor
        });
    } else if (!statuses.some(s => {return s === status})) {
        Swal.fire({
            title: alertTitle,
            text: "Оберіть статус із наданих значень",
            confirmButtonColor: alertColor
        });
    } else if (conditions.length <= 0 || !conditions.every(e => conditionsValues.includes(e))) {
        Swal.fire({
            title: alertTitle,
            text: "Оберіть умови із наданих значень",
            confirmButtonColor: alertColor
        });
    } else if (additional.length > 250) {
        Swal.fire({
            title: alertTitle,
            text: "Додаткова інформація має бути не більше 250 символів",
            confirmButtonColor: alertColor
        });
    } else {
        document.getElementById("form").submit();
    }
}

function validateAndSubmitRegistration() {
    let email = document.getElementById("email").value;
    let pass = document.getElementById("password").value;

    if (!email.match(validEmailRegex)) {
        Swal.fire({
            title: alertTitle,
            text: "Некоректна пошта",
            confirmButtonColor: alertColor
        });
    } else if (pass.length < 8) {
        Swal.fire({
            title: alertTitle,
            text: "Мінімальна довжина паролю 8 символів",
            confirmButtonColor: alertColor
        });
    } else if (pass.length > 16) {
        Swal.fire({
            title: alertTitle,
            text: "Максимальна довжина паролю 16 символів",
            confirmButtonColor: alertColor
        });
    } else {
        document.getElementById("form").submit();
    }
}

function applyRegistrationForm() {
    let fullName = document.getElementById("fullName").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("phone").value;
    let organisation = document.getElementById("organisation").value;

    if (fullName.length < 1 ) {
        Swal.fire({
            title: alertTitle,
            text: "Довжина імені не може бути меншою за 1",
            confirmButtonColor: alertColor
        });
    } else if (fullName.length > 50) {
        Swal.fire({
            title: alertTitle,
            text: "Довжина імені не може бути більшою за 50",
            confirmButtonColor: alertColor
        });
    } else if (!email.match(validEmailRegex)) {
        Swal.fire({
            title: alertTitle,
            text: "Некоректна пошта",
            confirmButtonColor: alertColor
        });
    } else if (!phone.match(validPhoneRegex)) {
        Swal.fire({
            title: alertTitle,
            text: "Некоректний номер телефону",
            confirmButtonColor: alertColor
        });
    } else if (organisation < 1) {
        Swal.fire({
            title: alertTitle,
            text: "Назва компанії не може бути меншою за 1",
            confirmButtonColor: alertColor
        });
    } else if (organisation.lengt > 40) {
        Swal.fire({
            title: alertTitle,
            text: "Назва компанії не може бути більшою за 40",
            confirmButtonColor: alertColor
        });
    } else {
        document.getElementById("form").submit();
    }
}