interface PassangerModel {
    selfUri : string;
    endDateTime: string;
    passengerState: string;
    startDateTime: string;
    tripUri:  string;
    userUri: string;
    otherPassengersUri: string;
    passengerReviewsForTripUriTemplate: string;
    passengerReportsForTripUriTemplate: string;
}

export default PassangerModel;