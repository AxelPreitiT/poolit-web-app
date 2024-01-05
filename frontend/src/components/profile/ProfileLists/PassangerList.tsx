import { Trip } from "@/components/cardTrip/cardTrip";
import ListContainer from "@/components/profile/list/ListContainer";
import ShortReview from "@/components/review/shorts/ShortReview";
import CardTrip from "@/components/cardTrip/cardTrip";
import { publicsReviewsPath, reservedTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";

const PassengerList = () => {
  const { t } = useTranslation();
  const data: Trip[] = [
    {
      tripId: 1,
      originCity: {
        name: "Balvanera",
      },
      originAddress: "Av independencia 2135",
      destinationCity: {
        name: "Parque Patricios",
      },
      destinationAddress: "Iguazu 341",
      dayOfWeekString: "Miercoles",
      startDateString: "Date string",
      endDateString: "Date string",
      travelInfoDateString: "travel info",
      startTimeString: "time",
      integerQueryTotalPrice: "10",
      decimalQueryTotalPrice: "00",
      queryIsRecurrent: false,
      car: {
        imageId: "imagen",
      },
    },
  ];

  const data2 = [
    {
      type: "type",
      comment: "comment",
      raiting: 2,
      formattedDate: "Date",
    },
    {
      type: "type",
      comment: "comment",
      raiting: 2,
      formattedDate: "Date",
    },
  ];

  return (
    <div>
      <ListContainer
        title={t("profile.lists.review_as_passanger")}
        btn_footer_text={t("profile.lists.review_more")}
        empty_text={t("profile.lists.review_empty")}
        empty_icon={"book"}
        data={data2}
        component_name={ShortReview}
        link={publicsReviewsPath.replace(":id", String(5))}
      />
      <ListContainer
        title={t("profile.lists.reserved_next_title")}
        btn_footer_text={t("profile.lists.reserved_next_more")}
        empty_text={t("profile.lists.reserved_next_empty")}
        empty_icon={"car-front-fill"}
        data={data}
        component_name={CardTrip}
        link={reservedTripsPath}
      />
      <ListContainer
        title={t("profile.lists.reserved_prev_title")}
        btn_footer_text={t("profile.lists.reserved_prev_more")}
        empty_text={t("profile.lists.reserved_prev_empty")}
        empty_icon={"car-front-fill"}
        data={data}
        component_name={CardTrip}
        link={reservedTripsPath}
      />
    </div>
  );
};

export default PassengerList;
