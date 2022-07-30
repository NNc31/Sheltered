var markers = [];

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

    for (var i = 0; i < shelters.length; i++) {
        var shelter = shelters[i];
        marker = new google.maps.Marker({
            position: {lat: shelter.latitude, lng: shelter.longitude},
            map: shelterMap
        });

        google.maps.event.addListener(marker, 'click', (function(marker) {
            return function() {
                document.getElementById("lat").value = marker.getPosition().lat();
                document.getElementById("lng").value = marker.getPosition().lng();

                for (var j = 0; j < markers.length; j++) {
                    markers[j].setIcon();
                }

                marker.setIcon("/img/delete-marker.svg");
                audio.play();
            };
        })(marker));
        markers.push(marker);
    }
}