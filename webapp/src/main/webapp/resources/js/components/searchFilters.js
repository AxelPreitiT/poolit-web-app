import {calendarConfig, getDaysOfWeekDisabled} from "../modules/calendarConfig.js";
import {timeConfig} from "../modules/timeConfig.js";

const tomorrow = new Date();
tomorrow.setDate(tomorrow.getDate() + 1);

const timeElement = document.getElementById('time');
const firstDateElement = document.getElementById('first-date-picker');
const lastDateElement = document.getElementById('last-date-picker');
const uniqueDateElement = document.getElementById('unique-date-picker');

const daySelectorElement = document.getElementById('day');
const firstDateInputElement = document.getElementById('first-date');
const lastDateInputElement = document.getElementById('last-date');

const isMultitripCheckbox = document.getElementById('is-multitrip');
const multitripContainer = document.getElementById('multitrip-container');
const uniqueTripContainer = document.getElementById('unique-trip-container');

const timePicker = new tempusDominus.TempusDominus(timeElement, {
    ...timeConfig
});

const firstDatePicker = new tempusDominus.TempusDominus(firstDateElement, {
    ...calendarConfig,
});

const lastDatePicker = new tempusDominus.TempusDominus(lastDateElement, {
    ...calendarConfig,
    restrictions: {
        minDate: tomorrow,
    }
});

const uniqueDatePicker = new tempusDominus.TempusDominus(uniqueDateElement, {
    ...calendarConfig,
});

firstDateElement.addEventListener(tempusDominus.Namespace.events.change, (e) => {
    lastDatePicker.updateOptions({
        restrictions: {
            minDate: e.detail.date
        },
    });
});

lastDateElement.addEventListener(tempusDominus.Namespace.events.change, (e) => {
    firstDatePicker.updateOptions({
        restrictions: {
            maxDate: e.detail.date
        },
    });
});

daySelectorElement.addEventListener('change', (e) => {
    firstDatePicker.dates.clear();
    lastDatePicker.dates.clear();
    const day = e.target.value;
    if (day === "none") {
        firstDateInputElement.setAttribute('disabled', 'disabled');
        lastDateInputElement.setAttribute('disabled', 'disabled');
        return;
    }
    firstDateInputElement.removeAttribute('disabled');
    lastDateInputElement.removeAttribute('disabled');
    const daysOfWeekDisabled = getDaysOfWeekDisabled(day);
    firstDatePicker.updateOptions({
        restrictions: {
            daysOfWeekDisabled: daysOfWeekDisabled,
        }
    });
    lastDatePicker.updateOptions({
        restrictions: {
            daysOfWeekDisabled: daysOfWeekDisabled,
        }
    });
});

isMultitripCheckbox.addEventListener('change', (e) => {
    if(e.target.checked) {
        new bootstrap.Collapse(uniqueTripContainer, {
            show: false
        });
    } else {
        new bootstrap.Collapse(multitripContainer, {
            show: false
        });
    }
});

multitripContainer.addEventListener('hidden.bs.collapse', (e) => {
    new bootstrap.Collapse(uniqueTripContainer, {
        show: true
    });
});

uniqueTripContainer.addEventListener('hidden.bs.collapse', (e) => {
    new bootstrap.Collapse(multitripContainer, {
        show: true
    });
});


