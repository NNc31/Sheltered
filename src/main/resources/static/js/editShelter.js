var markers = [];

const countryLat = 48.383022;
const countryLng = 31.1828699;

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

    let shelterMap = new google.maps.Map(element, options);



    let marker;
    let audio = new Audio("/sfx/marker-toggle.wav");

    let collapsible = new bootstrap.Collapse(document.getElementById("collapsible"), {
        toggle: false
    });

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

                let selectedShelter = shelters.find(sh => {return sh.coordinates.latitude === lat && sh.coordinates.longitude === lng});
                document.getElementById("capacity").value = selectedShelter.capacity;
                document.getElementById("area").value = selectedShelter.area;
                document.getElementById("additional").value = selectedShelter.additional;

                document.querySelector('#status').setValue(selectedShelter.status);
                document.querySelector('#conditions').setValue(selectedShelter.conditions);

                for (let j = 0; j < markers.length; j++) {
                    markers[j].setIcon();
                }
                marker.setIcon("/img/edit-marker.svg");

                collapsible.show();
                audio.play();
            };
        })(marker));
        markers.push(marker);
    }
}