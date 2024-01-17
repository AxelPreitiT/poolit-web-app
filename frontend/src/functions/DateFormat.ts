interface FormattedDateTime {
    date: string;
    time: string;
}

const getFormattedDateTime = (dateTimeString: string | undefined): FormattedDateTime => {
    if (!dateTimeString) {
        return { date: '', time: '' };
    }
    const [date, time] = dateTimeString.split('T');
    return { date: date || '', time: time || '' };
};

export default getFormattedDateTime;