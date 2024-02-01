
function getTotalTrips(startDateTime: Date, endDateTime: Date): number{
    return Math.floor((startDateTime.getTime() -  endDateTime.getTime()) / (7 * 24 * 60 * 60 * 1000));
}

export default getTotalTrips