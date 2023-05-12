
export const today = new Date(Date.now());
export const calendarConfig = {
    display: {
        components: {
            clock: false,
            calendar: true,
            date: true,
            month: true,
            year: false,
            decades: false,
        }
    },
    localization: {
        format: 'dd/MM/yyyy',
        dayViewHeaderFormat: {
            month: 'long',
            year: 'numeric'
        }
    },
    restrictions: {
        minDate: today,
    },
    useCurrent: false,
}

export const getDaysOfWeekDisabled = (dayNumber) => {
    const daysOfWeekDisabled = [];
    for (let i = 0; i < 7; i++) {
        if (i !== dayNumber) {
            daysOfWeekDisabled.push(i);
        }
    }
    return daysOfWeekDisabled;
}