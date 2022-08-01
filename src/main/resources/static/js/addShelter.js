VirtualSelect.init({
    ele: '#status-select',
    search: false,
    placeholder: 'Оберіть статус',
    optionsCount: 4
});

VirtualSelect.init({
    ele: '#conditions-select',
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
    var element = document.getElementById('map');
    var options = {
        zoom: 12,
        center: {lat: 50.9216, lng: 34.80029} // Sumy lat & lng
    };

    var shelterMap = new google.maps.Map(element, options);

    for (var i = 0; i < shelters.length; i++) {
        var shelter = shelters[i];
        new google.maps.Marker({
            position: {lat: shelter.coordinates.latitude, lng: shelter.coordinates.longitude},
            map: shelterMap
        });
    }

    var marker = new google.maps.Marker({
        position: {lat: 50.9216, lng: 34.80029},
        map: shelterMap,
        draggable: true,
        icon: '/img/add-marker.svg'
    });
    var audio = new Audio("/sfx/marker-drop.wav")

    document.getElementById("lat").value = 50.9216;
    document.getElementById("lng").value = 34.80029;

    google.maps.event.addListener(marker, 'dragend', function () {
        document.getElementById("lat").value = marker.getPosition().lat();
        document.getElementById("lng").value = marker.getPosition().lng();
        audio.play();
    });
}