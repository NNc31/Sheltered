VirtualSelect.init({
    ele: '#region',
    search: false,
    placeholder: 'Оберіть область',
    optionsCount: 1
});

// no options found
VirtualSelect.init({
    ele: '#city',
    search: false,
    placeholder: 'Оберіть місто',
    optionsCount: 1
});

let regionElement = document.querySelector('#region');
let cityElement = document.querySelector('#city');
let tableBody = document.getElementById('requestTableBody');

let locations = new Map();
let urlRequestPairs = new Map();
let cityRequestPairs = new Map();

function reverseGeocoding(url) {
    return fetch(url).then(response => response.json())
        .then(data => {
            if (data.results[0]) {
                let city;
                let region
                let components = data.results[0].address_components;
                for (let component = 0; component < components.length; component++) {
                    if (components[component].types[0] === "administrative_area_level_1") {
                        region = components[component].long_name;
                    }

                    if(components[component].types[0] === "locality") {
                        city = components[component].long_name;
                    }
                }
                if (region && city) {
                    if (!locations.has(region)) {
                        locations.set(region, []);
                    }
                    let cityList = locations.get(region);
                    if (!cityList.includes(city)) {
                        cityList.push(city);
                        cityRequestPairs.set(city, []);
                    }
                    cityRequestPairs.get(city).push(urlRequestPairs.get(url));
                }
            }
        }).catch(exception => {
            throw new Error(exception);
        });
}

function refreshCityOptions(region) {
    let cityOptions = [];
    if (region) {
        locations.get(region).forEach(city => {
            cityOptions.push({label: city, value: city})
        })
    }
    cityElement.setOptions(cityOptions);
    cityElement.reset();
}

let urls = [];
let regions;

for (let i = 0; i < supplyRequests.length; i++) {
    let shelter = supplyRequests[i].shelter;
    let url = "https://maps.googleapis.com/maps/api/geocode/json?language=uk&key=AIzaSyBNygk1I_A0o5KiaTXySMczo0evoDak2ug&latlng="
        + shelter.coordinates.latitude + "," + shelter.coordinates.longitude;
    urls.push(url)
    urlRequestPairs.set(url, supplyRequests[i]);
}

let requests = urls.map(url => reverseGeocoding(url));
Promise.all(requests).then(results => {
    regions = Array.from(locations.keys());

    let regionOptions = [];

    regions.forEach(region => {
        let regionOption = { label: region, value: region };
        regionOptions.push(regionOption);
    })

    regionElement.setOptions(regionOptions);
    regionElement.reset();

    regionElement.addEventListener('afterClose', function() {
        refreshCityOptions(regionElement.value);
    });
    regionElement.addEventListener('reset', function() {
        refreshCityOptions();
    });
    cityElement.addEventListener('afterClose', function() {
        console.log(cityRequestPairs);
        let requestsByCities = cityRequestPairs.get(cityElement.value);

        for (let i = 0; i < requestsByCities.length; i++) {
            let row = tableBody.insertRow(i);
            let index = row.insertCell(0);
            let name = row.insertCell(1);
            let date = row.insertCell(2);
            let description = row.insertCell(3);
            row.setAttribute("class", "clickable-row");
            let shelterLink = "http://" + window.location.host + "/shelter?lat=";
            shelterLink = shelterLink + requestsByCities[i].shelter.coordinates.latitude;
            shelterLink = shelterLink + "&lng=";
            shelterLink = shelterLink + requestsByCities[i].shelter.coordinates.longitude;
            row.setAttribute("data-href", shelterLink);

            index.innerHTML = (i + 1).toString();
            name.innerHTML = requestsByCities[i].name;
            date.innerHTML = requestsByCities[i].submitDate;
            description.innerHTML = requestsByCities[i].description;

            jQuery(document).ready(function($) {
                $(".clickable-row").click(function() {
                    window.location = $(this).data("href");
                });
            });
        }

    });
    cityElement.addEventListener('reset', function() {
        while (tableBody.hasChildNodes()) {
            tableBody.removeChild(tableBody.lastChild);
        }
    });
})
.catch(error => {
    throw new Error(error);
})