let markers = [];

const countryLat = 48.383022;
const countryLng = 31.1828699;
const alertTitle = "Некоректні дані";
const alertColor = "#000000";

VirtualSelect.init({
    ele: '#status',
    search: false,
    placeholder: 'Оберіть статус',
    optionsCount: 4
});

VirtualSelect.init({
    ele: '#conditions',
    search: false,
    placeholder: 'Оберіть умови',
    optionsCount: 4,
    selectAllText: 'Обрати все',
    allOptionsSelectedText: 'Всі',
    optionsSelectedText: 'умов обрано',
    optionSelectedText: 'умови обрано',
    multiple: true
});

function initMap()
{
    let element = document.getElementById('map');
    let options = {
        zoom: 6,
        center: {lat: countryLat, lng: countryLng}
    };

    //set impossible lat and lng to make user select the shelter
    document.getElementById("lat").value = -181;
    document.getElementById("lng").value = -181;

    let shelterMap = new google.maps.Map(element, options);
    let marker;
    let audio = new Audio("/sfx/marker-toggle.wav");

    for (let i = 0; i < shelters.length; i++) {
        let shelter = shelters[i];
        marker = new google.maps.Marker({
            position: {lat: shelter.coordinates.latitude, lng: shelter.coordinates.longitude},
            map: shelterMap
        });
        google.maps.event.addListener(marker, 'click', (function(marker) {
            return function() {
                let lat = marker.getPosition().lat();
                let lng = marker.getPosition().lng();
                document.getElementById("lat").value = lat;
                document.getElementById("lng").value = lng;

                for (let j = 0; j < markers.length; j++) {
                    markers[j].setIcon();
                }
                marker.setIcon("/img/add-marker.svg");
                audio.play();
            };
        })(marker));
        markers.push(marker);
    }
}

function validateAndSubmitForm() {
    let lat = document.getElementById("lat").value;
    let lng = document.getElementById("lng").value;
    let name = document.getElementById("name").value;
    let description = document.getElementById("description").value;
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
    } else if (name === null || name.length < 1) {
        Swal.fire({
            title: alertTitle,
            text: "Заявка повинна мати назву",
            confirmButtonColor: alertColor
        });
    } else if (name.length > 60) {
        Swal.fire({
            title: alertTitle,
            text: "Заявка має задовгу назву",
            confirmButtonColor: alertColor
        });
    } else if (description === null || description.length <= 1) {
        Swal.fire({
            title: alertTitle,
            text: "Заявка повинна мати опис",
            confirmButtonColor: alertColor
        });
    } else if (description.length > 1000) {
        Swal.fire({
            title: alertTitle,
            text: "Заявка має задовгий опис",
            confirmButtonColor: alertColor
        });
    } else {
        document.getElementById("form").submit();
    }
}