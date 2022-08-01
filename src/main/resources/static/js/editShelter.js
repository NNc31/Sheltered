var markers = [];

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



    var marker;
    var audio = new Audio("/sfx/marker-toggle.wav");

    var collapsible = new bootstrap.Collapse(document.getElementById("collapsible"), {
        toggle: false
    });

    for (var i = 0; i < shelters.length; i++) {
        var shelter = shelters[i];
        marker = new google.maps.Marker({
            position: {lat: shelter.coordinates.latitude, lng: shelter.coordinates.longitude},
            map: shelterMap
        });
        google.maps.event.addListener(marker, 'click', (function(marker) {
            return function() {
                var lat = marker.getPosition().lat();
                var lng = marker.getPosition().lng();
                document.getElementById("lat").value = lat;
                document.getElementById("lng").value = lng;

                var selectedShelter = shelters.find(sh => {return sh.coordinates.latitude === lat && sh.coordinates.longitude === lng});
                document.getElementById("capacity").value = selectedShelter.capacity;
                document.getElementById("area").value = selectedShelter.area;
                document.getElementById("additional").value = selectedShelter.additional;

                document.querySelector('#status-select').setValue(selectedShelter.status);
                document.querySelector('#conditions-select').setValue(selectedShelter.conditions);

                for (var j = 0; j < markers.length; j++) {
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