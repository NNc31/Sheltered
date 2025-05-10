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

const countryLat = 48.383022;
const countryLng = 31.1828699;

function initMap()
{
    let element = document.getElementById('map');
    let options = {
        zoom: 6,
        center: {lat: countryLat, lng: countryLng}
    };

    let shelterMap = new google.maps.Map(element, options);

    for (let i = 0; i < shelters.length; i++) {
        let shelter = shelters[i];
        new google.maps.Marker({
            position: {lat: shelter.coordinates.latitude, lng: shelter.coordinates.longitude},
            map: shelterMap
        });
    }

    let marker = new google.maps.Marker({
        position: {lat: 50.9216, lng: 34.80029},
        map: shelterMap,
        draggable: true,
        icon: '/web-service/img/add-marker.svg'
    });
    let audio = new Audio("/web-service/sfx/marker-drop.wav")

    document.getElementById("lat").value = 50.9216;
    document.getElementById("lng").value = 34.80029;

    google.maps.event.addListener(marker, 'dragend', function () {
        document.getElementById("lat").value = marker.getPosition().lat();
        document.getElementById("lng").value = marker.getPosition().lng();
        audio.play();
    });
}