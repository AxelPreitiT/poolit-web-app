
function getTotalTrips(startDateTime: Date, endDateTime: Date): number{
    return Math.floor((endDateTime.getTime() -  startDateTime.getTime()) / (7 * 24 * 60 * 60 * 1000)) + 1;
}

export default getTotalTrips