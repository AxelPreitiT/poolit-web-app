import {calendarConfig, getDaysOfWeekDisabled, today} from "./calendarConfig.js";

const day = 24 * 60 * 60 * 1000;
const tomorrow = new Date(today.getTime() + day);

export const setRecurrentTripCalendar = (firstDateElementId, lastDateElementId, lastDateInputElementId, lastDateButtonElementId, isMultitripCheckboxId, dayRepeatContainerId, dayRepeatTextId, setDayRepeatContainerCallback = null) => {
    const firstDateElement = document.getElementById(firstDateElementId);
    const lastDateElement = document.getElementById(lastDateElementId);
    const lastDateInputElement = document.getElementById(lastDateInputElementId);
    const lastDateButtonElement = document.getElementById(lastDateButtonElementId);
    const isMultitripCheckbox = document.getElementById(isMultitripCheckboxId);
    const dayRepeatContainer = document.getElementById(dayRepeatContainerId);
    const dayRepeatText = document.getElementById(dayRepeatTextId);

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

    if(setDayRepeatContainerCallback !== null){
        dayRepeatContainer.addEventListener('hide.bs.collapse', () => {
            setDayRepeatContainerCallback(false);
        });

        dayRepeatContainer.addEventListener('show.bs.collapse', () => {
            setDayRepeatContainerCallback(true);
        });
    }
}