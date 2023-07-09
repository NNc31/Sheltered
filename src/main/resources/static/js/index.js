VirtualSelect.init({
    ele: '#status',
    search: false,
    placeholder: 'Оберіть умови',
    optionsCount: 4,
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
    optionsCount: 4,
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
    let element = document.getElementById('map');
    let options = {
        zoom: 12,
        center: {lat: 50.9216, lng: 34.80029} // Sumy lat & lng
    };

    let shelterMap = new google.maps.Map(element, options);

    let marker;

    let filterStatus = document.getElementById('status').value;
    let filterCondition = document.getElementById('conditions').value;

    for (let i = 0; i < shelters.length; i++) {
        let shelter = shelters[i];

        if (filterStatus.length === 0 && filterCondition.length === 0 ||
            filterStatus.length !== 0 && filterCondition.length === 0 && checkStatusFilter(shelter, filterStatus) ||
            filterStatus.length === 0 && filterCondition.length !== 0 && checkConditionFilter(shelter, filterCondition) ||
            checkStatusFilter(shelter, filterStatus) && checkConditionFilter(shelter, filterCondition)) {
            addMarker(shelter);
        }
    }

function addMarker(shelter) {
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

}