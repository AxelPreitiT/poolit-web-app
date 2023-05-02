import {timeConfig} from "../modules/timeConfig.js";
import {setRecurrentTripCalendar} from "../modules/setRecurrentTripCalendar.js";

const timeElement = document.getElementById('time-picker');
const minPriceElement = document.getElementById('min-price');
const maxPriceElement = document.getElementById('max-price');

const timePicker = new tempusDominus.TempusDominus(timeElement, {
    ...timeConfig
});

setRecurrentTripCalendar('first-date-picker', 'date','last-date-picker', 'last-date', 'last-date-button', 'is-multitrip', 'day-repeat-container', 'day-repeat-text');

if(minPriceElement.value === "0.0") {
    minPriceElement.value = "";
}

if(maxPriceElement.value === "0.0") {
    maxPriceElement.value = "";
}
