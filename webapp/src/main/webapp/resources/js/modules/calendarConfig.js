
const dayIntValue = {
    'sunday': 0,
    'monday': 1,
    'tuesday': 2,
    'wednesday': 3,
    'thursday': 4,
    'friday': 5,
    'saturday': 6,
}

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
}

export const getDaysOfWeekDisabled = (day) => {
    const daysOfWeekDisabled = [];
    for (let i = 0; i < 7; i++) {
        if (i !== dayIntValue[day]) {
            daysOfWeekDisabled.push(i);
        }
    }
    return daysOfWeekDisabled;
}