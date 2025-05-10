var markers = [];

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
                document.getElementById("lat").value = marker.getPosition().lat();
                document.getElementById("lng").value = marker.getPosition().lng();

                for (let j = 0; j < markers.length; j++) {
                    markers[j].setIcon();
                }

                marker.setIcon("/web-service/img/delete-marker.svg");
                audio.play();
            };
        })(marker));
        markers.push(marker);
    }
}