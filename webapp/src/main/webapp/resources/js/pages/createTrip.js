import {timeConfig} from "../modules/timeConfig.js";
import {setRecurrentTripCalendar} from "../modules/setRecurrentTripCalendar.js";

const timeElement = document.getElementById('time-picker');
const carSelectElement = document.getElementById('car-select');
const createTripButtonElement = document.getElementById('create-trip-button');
const verticalDottedLineElement = document.getElementById('vertical-dotted-line');
const carImageContainerElement = document.getElementById('car-image-container');

const carInfoDetailsElementPrefix = 'car-info-details-';
const carInfoImageElementPrefix = 'car-info-image-';

let currentSelectedCarId = carSelectElement.value;

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
    if(carId === "-1") {
        const currentSelectedCarInfoDetailsElement = document.getElementById(carInfoDetailsElementPrefix + currentSelectedCarId);
        const currentSelectedCarInfoImageElement = document.getElementById(carInfoImageElementPrefix + currentSelectedCarId);
        currentSelectedCarInfoImageElement.classList.remove("collapse-horizontal");
        new bootstrap.Collapse(currentSelectedCarInfoImageElement, {
            show: false
        });
        new bootstrap.Collapse(currentSelectedCarInfoDetailsElement, {
            show: false
        });
        carImageContainerElement.classList.remove('active');
        createTripButtonElement.disabled = true;
    } else {
        const carInfoDetailsElement = document.getElementById(carInfoDetailsElementPrefix + carId);
        const carInfoImageElement = document.getElementById(carInfoImageElementPrefix + carId);
        carInfoImageElement.classList.add("collapse-horizontal");
        if (currentSelectedCarId !== "-1") {
            const currentSelectedCarInfoDetailsElement = document.getElementById(carInfoDetailsElementPrefix + currentSelectedCarId);
            const currentSelectedCarInfoImageElement = document.getElementById(carInfoImageElementPrefix + currentSelectedCarId);
            currentSelectedCarInfoImageElement.classList.remove("collapse-horizontal");
            new bootstrap.Collapse(currentSelectedCarInfoImageElement, {
                show: false
            });
            new bootstrap.Collapse(currentSelectedCarInfoDetailsElement, {
                show: false
            });
            currentSelectedCarInfoImageElement.addEventListener('hidden.bs.collapse', () => {
                new bootstrap.Collapse(carInfoDetailsElement, {
                    show: true
                });
                new bootstrap.Collapse(carInfoImageElement, {
                    show: true
                });
            }, {once: true});
        } else {
            carImageContainerElement.classList.add('active');
            new bootstrap.Collapse(carInfoDetailsElement, {
                show: true
            });
            new bootstrap.Collapse(carInfoImageElement, {
                show: true
            });
        }
        createTripButtonElement.disabled = false;
    }
    currentSelectedCarId = carId;
});
