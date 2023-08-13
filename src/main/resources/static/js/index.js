let markers;
let shelterMap;
let directionsRenderer = null;

VirtualSelect.init({
    ele: '#status',
    search: false,
    placeholder: 'Оберіть умови',
    optionsCount: 10,
    selectAllText: 'Обрати все',
    allOptionsSelectedText: 'Всі',
    optionsSelectedText: 'умов обрано',
    optionSelectedText: 'умови обрано',
    multiple: true
});

VirtualSelect.init({
    ele: '#conditions',
    search: false,
    placeholder: 'Оберіть умови',
    optionsCount: 10,
    selectAllText: 'Обрати все',
    allOptionsSelectedText: 'Всі',
    optionsSelectedText: 'умов обрано',
    optionSelectedText: 'умови обрано',
    multiple: true
});

document.querySelector('#status').addEventListener('afterClose', function() {
    initMap();
});
document.querySelector('#status').addEventListener('reset', function() {
    initMap();
});
document.querySelector('#conditions').addEventListener('afterClose', function() {
    initMap();
});
document.querySelector('#conditions').addEventListener('reset', function() {
    initMap();
});

let optCondsChbx = document.getElementById('optionalCondsCheckbox');

optCondsChbx.addEventListener('change', function() {
    initMap();
});

function initMap()
{
    markers = [];
    let element = document.getElementById('map');
    let options = {
        zoom: 12,
        center: {lat: 50.9216, lng: 34.80029} // Sumy lat & lng
    };

    shelterMap = new google.maps.Map(element, options);
    let filterStatus = document.getElementById('status').value;
    let filterCondition = document.getElementById('conditions').value;

    for (let i = 0; i < shelters.length; i++) {
        let shelter = shelters[i];

        if (filterStatus.length === 0 && filterCondition.length === 0 ||
            filterStatus.length !== 0 && filterCondition.length === 0 && checkStatusFilter(shelter, filterStatus) ||
            filterStatus.length === 0 && filterCondition.length !== 0 && checkConditionFilter(shelter, filterCondition) ||
            checkStatusFilter(shelter, filterStatus) && checkConditionFilter(shelter, filterCondition)) {
            addMarker(shelter, shelterMap);
        }
    }

}

function addMarker(shelter, shelterMap) {
    let marker = new google.maps.Marker({
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
    markers.push(marker);
}

function checkStatusFilter(shelter, filterStatuses) {
    return filterStatuses.includes(shelter.status);
}

function checkConditionFilter(shelter, filterConds) {
    if (optCondsChbx.checked) {
        return filterConds.some(cond => shelter.conditions.includes(cond));
    } else {
        return filterConds.every(cond => shelter.conditions.includes(cond));
    }
}

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getClosestShelter);
    } else {
        fireAlert("Неможлива операція", "Геолокація не підтримується цим браузером");
    }
}

function getClosestShelter(position) {
    let coords = [];
    for (let i = 0; i < shelters.length; i++) {
        coords[i] = [];
        coords[i][0] = shelters[i].coordinates.latitude;
        coords[i][1] = shelters[i].coordinates.longitude;
    }

    let posLat = position.coords.latitude;
    let posLng = position.coords.longitude;
    let minLat = null;
    let minLng = null;
    let minDistance = null;
    if (shelters.length > 0) {
        for (let i = 0; i < coords.length; i++) {
            let x = coords[i][0] - posLat;
            let y = coords[i][1] - posLng;
            let distance = Math.sqrt(x * x + y * y);

            if (minDistance === null || distance < minDistance) {
                minDistance = distance;
                minLat = coords[i][0];
                minLng = coords[i][1];
            }
        }
        drawShelterRoute({lat: posLat, lng: posLng}, {lat: minLat, lng: minLng});
    } else {
        fireAlert("Неможлива операція", "Сховища відсутні")
    }
}

function drawShelterRoute(posA, posB) {
    if (directionsRenderer !== null) {
        directionsRenderer.setMap(null)
    }

    let marker = new google.maps.Marker({
        position: {lat: posA.lat, lng: posA.lng},
        map: shelterMap
    });
    marker.setIcon("/img/person-marker.svg");

    let directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer({
        map: shelterMap,
        suppressMarkers: true
    });

    let routeTypeValue = document.getElementById('route-type-select').value;

    let directionRequest = {
        origin: posA,
        destination: posB,
        travelMode: routeTypeValue
    }

    directionsService.route(directionRequest, function (response, status) {
        if (status === 'OK') {
            directionsRenderer.setDirections(response);
        } else {
            fireAlert("Неможлива операція", "Не вдається визначити маршрут")
        }
        console.log(response);
    });
}

function fireAlert(alertTitle, alertText) {
    Swal.fire({
        title: alertTitle,
        text: alertText,
        confirmButtonColor: "#000000"
    });
}