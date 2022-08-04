function validateAndSubmitForm() {
    let lat = document.getElementById("lat").value;
    let lng = document.getElementById("lng").value;
    let cap = document.getElementById("capacity").value;
    let area = document.getElementById("area").value;
    let status = document.getElementById("status").value;
    let conditions = document.getElementById("conditions").value;
    let additional = document.getElementById("additional").value;

    const alertTitle = "Некоректні дані";
    const alertColor = "#000000";

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