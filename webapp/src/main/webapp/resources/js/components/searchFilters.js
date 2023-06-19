import {timeConfig} from "../modules/timeConfig.js";
import {calendarConfig, getDaysOfWeekDisabled, today} from "../modules/calendarConfig.js";

let currentTab = 'unique-trip-tab';
const carFeatureLabel = 'car-feature-label';
const carFeatureInput = 'car-feature-input';

const carFeatureLabelElements = document.querySelectorAll('.' + carFeatureLabel);
const uniqueTripTabElement = document.getElementById('unique-trip-tab');
const multitripTabElement = document.getElementById('multitrip-tab');

const originCitySelectorComponent = document.getElementById('originCityId');
const destinationCitySelectorComponent = document.getElementById('destinationCityId');
const swapCitiesButtonComponent = document.getElementById('swap-cities');

const checkCitySelectorValues = () => {
    swapCitiesButtonComponent.disabled = !(originCitySelectorComponent.value > 0 || destinationCitySelectorComponent.value > 0);
}

checkCitySelectorValues();
originCitySelectorComponent.addEventListener('change',  checkCitySelectorValues);
destinationCitySelectorComponent.addEventListener('change',  checkCitySelectorValues);

swapCitiesButtonComponent.addEventListener('click', () => {
    const originCityId = originCitySelectorComponent.value;
    const destinationCityId = destinationCitySelectorComponent.value;
    originCitySelectorComponent.value = destinationCityId;
    destinationCitySelectorComponent.value = originCityId;
    checkCitySelectorValues();
});


const minPriceElement = document.getElementById('min-price');
const maxPriceElement = document.getElementById('max-price');

if(minPriceElement.value === "0.0") {
    minPriceElement.value = "";
}

if(maxPriceElement.value === "0.0") {
    maxPriceElement.value = "";
}

const day = 24 * 60 * 60 * 1000;
const tomorrow = new Date(today.getTime() + day);

const timeElement = document.getElementById('time-picker');
const dateElement = document.getElementById('date-picker');
const lastDateElement = document.getElementById('last-date-picker');
const timeInputElement = document.getElementById('time');
const dateInputElement = document.getElementById('date');
const lastDateInputElement = document.getElementById('last-date');
const dayRepeatContainerElement = document.getElementById('day-repeat-container');
const dayRepeatTextElement = document.getElementById('day-repeat-text');
const multitripContainerElement = document.getElementById('multitrip-container');
const isMultitripInput = document.getElementById('is-multitrip');

const initialTimeValue = timeInputElement.value;
const initialDateValue = dateInputElement.value;
const initialLastDateValue = lastDateInputElement.value;
let initialDate = null;
let isDayRepeat = false;
const dateFormatRegex = /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;

timeInputElement.value = "";
dateInputElement.value = "";
lastDateInputElement.value = "";

if(dateFormatRegex.test(initialDateValue)) {
    isDayRepeat = true;
    const day = initialDateValue.substring(0, 2);
    const month = initialDateValue.substring(3, 5);
    const year = initialDateValue.substring(6, 10);
    initialDate = new Date(parseInt(year), month - 1, parseInt(day));
    let dayOfWeek = initialDate.toLocaleString(window.navigator.language, {weekday: 'long'});
    if(window.navigator.language.includes('es') && !dayOfWeek.endsWith('s')) {
        dayOfWeek += 's';
    }
    dayRepeatTextElement.innerText = dayOfWeek;
}

if(isMultitripInput.value === "true") {
    isMultitripInput.value = true;
    multitripTabElement.classList.add('active');
    currentTab = 'multitrip-tab';
    new bootstrap.Collapse(multitripContainerElement, {
        show: true
    });
    multitripContainerElement.addEventListener('shown.bs.collapse', () => {
        if(isDayRepeat) {
            new bootstrap.Collapse(dayRepeatContainerElement, {
                show: true
            });
        }
    }, {once: true});
} else {
    uniqueTripTabElement.classList.add('active');
    currentTab = 'unique-trip-tab';
    isMultitripInput.value = false;
}

const timePicker = new tempusDominus.TempusDominus(timeElement, {
    ...timeConfig
});

const datePicker = new tempusDominus.TempusDominus(dateElement, {
    ...calendarConfig,
});

const lastDatePicker = new tempusDominus.TempusDominus(lastDateElement, {
    ...calendarConfig,
    restrictions: {
        minDate: initialDate ? new Date(initialDate.getTime() + day) : tomorrow,
        daysOfWeekDisabled: initialDate ? getDaysOfWeekDisabled(initialDate.getDay()) : []
    }
});

timeInputElement.value = initialTimeValue;
dateInputElement.value = initialDateValue;
lastDateInputElement.value = initialLastDateValue;

uniqueTripTabElement.addEventListener('click', () => {
    if(currentTab === "unique-trip-tab"){
        return;
    }
    isMultitripInput.value = false;
    currentTab = "unique-trip-tab";
    uniqueTripTabElement.classList.add('active');
    multitripTabElement.classList.remove('active');
    new bootstrap.Collapse(multitripContainerElement, {
        show: false
    });
    multitripContainerElement.addEventListener('hidden.bs.collapse', () => {
        if(isDayRepeat) {
            new bootstrap.Collapse(dayRepeatContainerElement, {
                show: false
            });
        }
    }, {once: true});
    lastDatePicker.dates.clear();
});

multitripTabElement.addEventListener('click', () => {
    if(currentTab === "multitrip-tab"){
        return;
    }
    isMultitripInput.value = true;
    currentTab = "multitrip-tab";
    uniqueTripTabElement.classList.remove('active');
    multitripTabElement.classList.add('active');
    new bootstrap.Collapse(multitripContainerElement, {
        show: true
    });
    multitripContainerElement.addEventListener('shown.bs.collapse', () => {
        if(isDayRepeat) {
            new bootstrap.Collapse(dayRepeatContainerElement, {
                show: true
            });
        }
    }, {once: true});
});

dateElement.addEventListener(tempusDominus.Namespace.events.change, (e) => {
    lastDatePicker.dates.clear();
    const selectedDate = new Date(e.detail.date);
    const minDate = new Date(selectedDate.getTime() + day);
    const daysOfWeekDisabled = getDaysOfWeekDisabled(selectedDate.getDay());
    lastDatePicker.updateOptions({
        restrictions: {
            minDate: minDate,
            daysOfWeekDisabled: daysOfWeekDisabled
        }
    });
    let dayOfWeek = selectedDate.toLocaleString(window.navigator.language, {weekday: 'long'});
    if (dayOfWeek === "Invalid Date") {
        dayRepeatTextElement.innerText = "";
        if(isDayRepeat) {
            if(currentTab === "multitrip-tab") {
                new bootstrap.Collapse(dayRepeatContainerElement, {
                    show: false
                });
            }
            isDayRepeat = !isDayRepeat;
        }
    } else {
        if(window.navigator.language.includes('es') && !dayOfWeek.endsWith('s')) {
            dayOfWeek += 's';
        }
        dayRepeatTextElement.innerText = dayOfWeek;
        if(!isDayRepeat) {
            if(currentTab === "multitrip-tab") {
                new bootstrap.Collapse(dayRepeatContainerElement, {
                    show: true
                });
            }
            isDayRepeat = !isDayRepeat;
        }
    }
});


const carFeaturesAvailable = {};
carFeatureLabelElements.forEach((carFeatureLabelElement) => {
    carFeaturesAvailable[carFeatureLabelElement.id] = {
        id: carFeatureLabelElement.id,
        feature: carFeatureLabelElement.id.slice(carFeatureLabel.length + 1),
    };
    carFeaturesAvailable[carFeatureLabelElement.id].inputElement = document.getElementById(carFeatureInput + '-' + carFeaturesAvailable[carFeatureLabelElement.id].feature);

    carFeatureLabelElement.addEventListener('click', () => {
        if (carFeatureLabelElement.classList.contains('active')) {
            carFeatureLabelElement.classList.remove('active');
            carFeaturesAvailable[carFeatureLabelElement.id].inputElement.setAttribute('disabled', 'disabled');
        } else {
            carFeatureLabelElement.classList.add('active');
            carFeaturesAvailable[carFeatureLabelElement.id].inputElement.removeAttribute('disabled');
        }
    });
});
