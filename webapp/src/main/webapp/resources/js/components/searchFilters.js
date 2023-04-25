import {calendarConfig, getDaysOfWeekDisabled, today} from "../modules/calendarConfig.js";
import {timeConfig} from "../modules/timeConfig.js";

const day = 60 * 60 * 24 * 1000;
const tomorrow = new Date(today.getTime() + day);

const timeElement = document.getElementById('time-picker');
const firstDateElement = document.getElementById('first-date-picker');
const lastDateElement = document.getElementById('last-date-picker');
const lastDateInputElement = document.getElementById('last-date');
const lastDateButtonElement = document.getElementById('last-date-button');
const isMultitripCheckbox = document.getElementById('is-multitrip');
const dayRepeatContainer = document.getElementById('day-repeat-container');
const dayRepeatText = document.getElementById('day-repeat-text');

dayRepeatText.innerHTML = "";

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

firstDateElement.addEventListener(tempusDominus.Namespace.events.change, (e) => {
    const selectedDate = new Date(e.detail.date);
    const minDate = new Date(selectedDate.getTime() + day);
    const daysOfWeekDisabled = getDaysOfWeekDisabled(new Date(e.detail.date).getDay());
    lastDatePicker.dates.clear();
    lastDatePicker.updateOptions({
        restrictions: {
            minDate: minDate,
            daysOfWeekDisabled: daysOfWeekDisabled,
        },
    });
    const previousValue = dayRepeatText.innerHTML;
    let dayOfWeek = selectedDate.toLocaleString(window.navigator.language, {weekday: 'long'});
    if(dayOfWeek === "Invalid Date"){
        dayOfWeek = "";
        new bootstrap.Collapse(dayRepeatContainer, {
            show: false
        });
    }
    dayRepeatText.innerHTML = dayOfWeek;
    if(previousValue === "" && isMultitripCheckbox.checked) {
        new bootstrap.Collapse(dayRepeatContainer, {
            show: true
        });
    }
});

isMultitripCheckbox.addEventListener('change', (e) => {
    if(e.target.checked) {
        lastDateInputElement.removeAttribute('disabled');
        lastDateButtonElement.removeAttribute('disabled');
        if(dayRepeatText.innerHTML !== ""){
            new bootstrap.Collapse(dayRepeatContainer, {
                show: true
            });
        }
    } else {
        lastDatePicker.dates.clear();
        lastDateInputElement.setAttribute('disabled', 'disabled');
        lastDateButtonElement.setAttribute('disabled', 'disabled');
        if(dayRepeatText.innerHTML !== ""){
            new bootstrap.Collapse(dayRepeatContainer, {
                show: false
            });
        }
    }
});

