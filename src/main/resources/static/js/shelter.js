let shelterMap;

function initMap()
{
    let element = document.getElementById('map');
    let options = {
        zoom: 14,
        center: {lat: shelter.coordinates.latitude, lng: shelter.coordinates.longitude}
    };

    shelterMap = new google.maps.Map(element, options);
    let marker = new google.maps.Marker({
        position: {lat: shelter.coordinates.latitude, lng: shelter.coordinates.longitude},
        map: shelterMap
    });
}