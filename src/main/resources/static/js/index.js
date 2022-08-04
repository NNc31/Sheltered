function initMap()
{
    let element = document.getElementById('map');
    let options = {
        zoom: 12,
        center: {lat: 50.9216, lng: 34.80029} // Sumy lat & lng
    };

    let shelterMap = new google.maps.Map(element, options);

    let marker;

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
                document.getElementById("form").submit();
            };
        })(marker));
    }
}