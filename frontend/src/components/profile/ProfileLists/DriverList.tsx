import { Trip } from "@/types/Trip";
import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import ShortReview from "@/components/review/shorts/ShortReview";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { publicsReviewsPath, createdTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import CardCar from "@/components/cardCar/CardCar";

const DriverList = () => {
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

  const data3 = [
    {
      carId: 1,
      imageId: 1,
      infoCar: "Mondeo blanco",
      plate: "AAA111",
    },
  ];

  return (
    <div>
      <ListProfileContainer
        title={t("profile.lists.review_as_driver")}
        btn_footer_text={t("profile.lists.review_more")}
        empty_text={t("profile.lists.review_empty")}
        empty_icon={"book"}
        data={data2}
        component_name={ShortReview}
        link={publicsReviewsPath.replace(":id", String(5))}
      />
      <ListProfileContainer
        title={t("profile.lists.created_next_title")}
        btn_footer_text={t("profile.lists.created_next_more")}
        empty_text={t("profile.lists.created_next_empty")}
        empty_icon={"car-front-fill"}
        data={data}
        component_name={CardTripProfile}
        link={createdTripsPath}
      />
      <ListProfileContainer
        title={t("profile.lists.created_prev_title")}
        btn_footer_text={t("profile.lists.created_prev_more")}
        empty_text={t("profile.lists.created_prev_empty")}
        empty_icon={"car-front-fill"}
        data={data}
        component_name={CardTripProfile}
        link={createdTripsPath}
      />
      <ListProfileContainer
        title={t("profile.lists.cars")}
        btn_footer_text={t("profile.lists.cars_create")}
        empty_text={t("profile.lists.cars_empty")}
        empty_icon={"car-front-fill"}
        data={data3}
        component_name={CardCar}
        link={createdTripsPath}
      />
    </div>
  );
};

export default DriverList;
