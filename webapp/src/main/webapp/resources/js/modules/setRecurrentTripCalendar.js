import {calendarConfig, getDaysOfWeekDisabled, today} from "./calendarConfig.js";

const day = 24 * 60 * 60 * 1000;
const tomorrow = new Date(today.getTime() + day);

export const setRecurrentTripCalendar = (firstDateElementId, firstDateInputElementId, lastDateElementId, lastDateInputElementId, lastDateButtonElementId, isMultitripCheckboxId, dayRepeatContainerId, dayRepeatTextId, setDayRepeatContainerCallback = null) => {
    const firstDateElement = document.getElementById(firstDateElementId);
    const firstDateInputElement = document.getElementById(firstDateInputElementId);
    const lastDateElement = document.getElementById(lastDateElementId);
    const lastDateInputElement = document.getElementById(lastDateInputElementId);
    const lastDateButtonElement = document.getElementById(lastDateButtonElementId);
    const isMultitripCheckbox = document.getElementById(isMultitripCheckboxId);
    const dayRepeatContainer = document.getElementById(dayRepeatContainerId);
    const dayRepeatText = document.getElementById(dayRepeatTextId);

    let isDisabled = true;
    dayRepeatText.innerHTML = "";

    const firstDatePicker = new tempusDominus.TempusDominus(firstDateElement, {
        ...calendarConfig,
    });

    const lastDatePicker = new tempusDominus.TempusDominus(lastDateElement, {
        ...calendarConfig,
        restrictions: {
            minDate: tomorrow,
        }
    });

    const firstDateUpdate = (date) => {
        const minDate = new Date(date.getTime() + day);
        const daysOfWeekDisabled = getDaysOfWeekDisabled(date.getDay());
        lastDatePicker.update({
            restrictions: {
                minDate: minDate,
                daysOfWeekDisabled: daysOfWeekDisabled
            }
        });
        let dayOfWeek = date.toLocaleString(window.navigator.language, {weekday: 'long'});
        if(dayOfWeek === "Invalid Date") {
            dayOfWeek = "";
        }
        dayRepeatText.innerHTML = dayOfWeek;
    }

    const toggleDisabledElements = () => {
        if(isMultitripCheckbox.checked && firstDateInputElement.value !== "") {
            lastDateInputElement.removeAttribute('disabled');
            lastDateButtonElement.removeAttribute('disabled');
            if(isDisabled) {
                new bootstrap.Collapse(dayRepeatContainer, {
                    show: true
                });
            }
        } else if(!isMultitripCheckbox.checked || firstDateInputElement.value === "") {
            lastDatePicker.dates.clear();
            lastDateInputElement.setAttribute('disabled', 'disabled');
            lastDateButtonElement.setAttribute('disabled', 'disabled');
            if(!isDisabled) {
                new bootstrap.Collapse(dayRepeatContainer, {
                    show: false
                });
            }
        }
    }

    firstDateElement.addEventListener(tempusDominus.Namespace.events.change, (e) => {
        lastDatePicker.dates.clear();
        const selectedDate = new Date(e.detail.date);
        firstDateUpdate(selectedDate);
        toggleDisabledElements();
    });

    isMultitripCheckbox.addEventListener('change', (e) => {
        toggleDisabledElements();
    });


    dayRepeatContainer.addEventListener('hide.bs.collapse', () => {
        isDisabled = true;
        if(setDayRepeatContainerCallback !== null){
            setDayRepeatContainerCallback(false);
        }
    });

    dayRepeatContainer.addEventListener('show.bs.collapse', () => {
        isDisabled = false;
        if(setDayRepeatContainerCallback !== null){
            setDayRepeatContainerCallback(true);
        }
    });

    if(firstDateInputElement.value !== "") {
        const [day, month, year] = firstDateInputElement.value.split("/");
        const selectedDate = new Date(year, month - 1, day);
        firstDateUpdate(selectedDate);
    }
    toggleDisabledElements();
}