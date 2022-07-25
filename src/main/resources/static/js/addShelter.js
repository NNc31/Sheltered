function initMap()
{
    var element = document.getElementById('map');
    var options = {
        zoom: 12,
        center: {lat: 50.9216, lng: 34.80029} // Sumy lat & lng
    };

    var shelterMap = new google.maps.Map(element, options);

    //set icon for the shelter instead of regular icons?
    for (var i = 0; i < shelters.length; i++) {
        var shelter = shelters[i];
        new google.maps.Marker({
            position: {lat: shelter.latitude, lng: shelter.longitude},
            map: shelterMap
        });
    }

    var marker = new google.maps.Marker({
        position: {lat: 50.9216, lng: 34.80029},
        map: shelterMap,
        draggable: true,
        icon: '/img/add-location.png'
    });
}