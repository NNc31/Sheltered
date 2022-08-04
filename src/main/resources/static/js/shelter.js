let cap = shelter.capacity;
let cnt = shelter.counter;
let ratio = shelter.counter / shelter.capacity;
if (ratio <= 0.5) {
    document.getElementById("counter-field").style.color = "#0ff020";
} else if (ratio <= 0.75) {
    document.getElementById("counter-field").style.color = "#ffcc00";
} else if (ratio < 1) {
    document.getElementById("counter-field").style.color = "#f01b0f";
} else {
    document.getElementById("counter-field").style.color = "#000000";
}
