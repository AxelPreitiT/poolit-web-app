import {timeConfig} from "../modules/timeConfig.js";
import {calendarConfig, getDaysOfWeekDisabled, today} from "../modules/calendarConfig.js";


// Delete auto-filled inputs
const seatsInputElement = document.getElementById('seats');
const priceInputElement = document.getElementById('price');

if(seatsInputElement.value === "0") {
    seatsInputElement.value = "";
}

if(priceInputElement.value === "0.0") {
    priceInputElement.value = "";
}

// Vertical dotted line management
const verticalDottedLineElement = document.getElementById('vertical-dotted-line');

const shouldExpandVerticalDottedLine = (value) => {
    if(value){
        verticalDottedLineElement.classList.add('expanded');
    }
    else {
        verticalDottedLineElement.classList.remove('expanded');
    }
}

// Recurrent trip management
const day = 24 * 60 * 60 * 1000;
const tomorrow = new Date(today.getTime() + day);

const timeElement = document.getElementById('time-picker');
const dateElement = document.getElementById('date-picker');
const lastDateElement = document.getElementById('last-date-picker');
const timeInputElement = document.getElementById('time');
const dateInputElement = document.getElementById('date');
const lastDateInputElement = document.getElementById('last-date');
const multitripCheckboxElement = document.getElementById('multitrip-checkbox');
const multitripInfoElement = document.getElementById('multitrip-info');
const multitripTextElement = document.getElementById('multitrip-text');

const initialTimeValue = timeInputElement.value;
const initialDateValue = dateInputElement.value;
const initialLastDateValue = lastDateInputElement.value;

let initialDate = null;
let isDayRepeat = false;
const dateFormatRegex = /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;

timeInputElement.value = "";
dateInputElement.value = "";
lastDateInputElement.value = "";

if(dateFormatRegex.test(initialDateValue) && multitripCheckboxElement.checked) {
    isDayRepeat = true;
    const day = initialDateValue.substring(0, 2);
    const month = initialDateValue.substring(3, 5);
    const year = initialDateValue.substring(6, 10);
    initialDate = new Date(parseInt(year), month - 1, parseInt(day));
    let dayOfWeek = initialDate.toLocaleString(window.navigator.language, {weekday: 'long'});
    if (window.navigator.language.includes('es') && !dayOfWeek.endsWith('s')) {
        dayOfWeek += 's';
    }
    multitripTextElement.innerText = dayOfWeek;
    shouldExpandVerticalDottedLine(true);
    new bootstrap.Collapse(multitripInfoElement, {
        show: true
    });
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

multitripCheckboxElement.addEventListener('change', (e) => {
    if(e.target.checked) {
        if(dateFormatRegex.test(dateInputElement.value)) {
            isDayRepeat = true;
            const day = dateInputElement.value.substring(0, 2);
            const month = dateInputElement.value.substring(3, 5);
            const year = dateInputElement.value.substring(6, 10);
            initialDate = new Date(parseInt(year), month - 1, parseInt(day));
            let dayOfWeek = initialDate.toLocaleString(window.navigator.language, {weekday: 'long'});
            if (window.navigator.language.includes('es') && !dayOfWeek.endsWith('s')) {
                dayOfWeek += 's';
            }
            multitripTextElement.innerText = dayOfWeek;
            new bootstrap.Collapse(multitripInfoElement, {
                show: true
            });
            multitripInfoElement.addEventListener('shown.bs.collapse', () => {
                shouldExpandVerticalDottedLine(true);
            }, {once: true});
        }
    } else {
        if(isDayRepeat) {
            isDayRepeat = false;
            initialDate = null;
            lastDatePicker.dates.clear();
            shouldExpandVerticalDottedLine(false);
            new bootstrap.Collapse(multitripInfoElement, {
                show: false
            });
            multitripInfoElement.addEventListener('hidden.bs.collapse', () => {
                multitripTextElement.innerText = "";
            }, {once: true});
        }
    }
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
    if (dayOfWeek === "Invalid Date" && isDayRepeat) {
        shouldExpandVerticalDottedLine(false);
        new bootstrap.Collapse(multitripInfoElement, {
            show: false
        });
        multitripInfoElement.addEventListener('hidden.bs.collapse', () => {
            multitripTextElement.innerText = "";
        }, {once: true});
        isDayRepeat = false;
    } else {
        if(window.navigator.language.includes('es') && !dayOfWeek.endsWith('s')) {
            dayOfWeek += 's';
        }
        multitripTextElement.innerText = dayOfWeek;
        if(multitripCheckboxElement.checked && !isDayRepeat) {
            new bootstrap.Collapse(multitripInfoElement, {
                show: true
            });
            multitripInfoElement.addEventListener('shown.bs.collapse', () => {
                shouldExpandVerticalDottedLine(true);
            }, {once: true});
            isDayRepeat = true;
        }
    }
});


// Select car management
const carSelectElement = document.getElementById('car-select');
const createTripButtonElement = document.getElementById('create-trip-button');
const carImageContainerElement = document.getElementById('car-image-container');

const carInfoDetailsElementPrefix = 'car-info-details-';
const carInfoImageElementPrefix = 'car-info-image-';

let currentSelectedCarId = carSelectElement.value;

if(currentSelectedCarId !== "-1") {
    const carInfoDetailsElement = document.getElementById(carInfoDetailsElementPrefix + currentSelectedCarId);
    const carInfoImageElement = document.getElementById(carInfoImageElementPrefix + currentSelectedCarId);
    carInfoImageElement.classList.add("collapse-horizontal");
    carImageContainerElement.classList.add('active');
    new bootstrap.Collapse(carInfoDetailsElement, {
        show: true
    });
    new bootstrap.Collapse(carInfoImageElement, {
        show: true
    });
}

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
