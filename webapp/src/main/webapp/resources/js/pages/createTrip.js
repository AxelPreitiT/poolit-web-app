import {timeConfig} from "../modules/timeConfig.js";
import {setRecurrentTripCalendar} from "../modules/setRecurrentTripCalendar.js";

const timeElement = document.getElementById('time-picker');
const carSelectElement = document.getElementById('car-select');
const carInfoDetailsElement = document.getElementById('car-info-details');
const carInfoImageElement = document.getElementById('car-info-image');
const createTripButtonElement = document.getElementById('create-trip-button');
const verticalDottedLineElement = document.getElementById('vertical-dotted-line');

// Vertical dotted line management
const shouldExpandVerticalDottedLine = (value) => {
    if(value){
        verticalDottedLineElement.classList.add('expanded');
    }
    else {
        verticalDottedLineElement.classList.remove('expanded');
    }
}

// Time management
const timePicker = new tempusDominus.TempusDominus(timeElement, {
    ...timeConfig
});

// Recurrent trip management
setRecurrentTripCalendar('first-date-picker', 'first-date', 'last-date-picker', 'last-date', 'last-date-button', 'is-multitrip', 'day-repeat-container', 'day-repeat-text', shouldExpandVerticalDottedLine);


// Select car management
carSelectElement.addEventListener('change', (e) => {
    const carId = e.target.value;
    if(carId === "") {
        new bootstrap.Collapse(carInfoImageElement, {
            show: false
        });
        createTripButtonElement.disabled = true;
    } else {
        new bootstrap.Collapse(carInfoDetailsElement, {
            show: true
        });
        createTripButtonElement.disabled = false;
    }
});

carInfoDetailsElement.addEventListener('shown.bs.collapse', () => {
    new bootstrap.Collapse(carInfoImageElement, {
        show: true
    });
});

carInfoImageElement.addEventListener('hidden.bs.collapse', () => {
    new bootstrap.Collapse(carInfoDetailsElement, {
        show: false
    });
});
