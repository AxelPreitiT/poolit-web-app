import {timeConfig} from "../modules/timeConfig.js";
import {setRecurrentTripCalendar} from "../modules/setRecurrentTripCalendar.js";

const timeElement = document.getElementById('time-picker');

const timePicker = new tempusDominus.TempusDominus(timeElement, {
    ...timeConfig
});

setRecurrentTripCalendar('first-date-picker', 'last-date-picker', 'last-date', 'last-date-button', 'is-multitrip', 'day-repeat-container', 'day-repeat-text');

