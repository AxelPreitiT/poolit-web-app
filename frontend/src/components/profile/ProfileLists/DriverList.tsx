import ListProfileContainer from "@/components/profile/list/ListProfileContainer";
import ShortReview from "@/components/review/shorts/ShortReview";
import CardTripProfile from "@/components/cardTrip/cardTripProfile/cardTripProfile";
import { publicsReviewsPath, createdTripsPath } from "@/AppRouter";
import { useTranslation } from "react-i18next";
import CardCar from "@/components/cardCar/CardCar";
import { useEffect, useState } from "react";
import tripsService from "@/services/TripsService.ts";

export interface PassengerListProp {
  uri: string;
}

const DriverList = ({ uri }: PassengerListProp) => {
  const { t } = useTranslation();

  const [trip, setTrip] = useState<TripModel[] | null>(null);

  useEffect(() => {
    tripsService.getTripsByUser(uri).then((response) => {
      // Extraer el cuerpo de la respuesta Axios
      // Luego, llamar a setProfileInfo con el resultado
      setTrip(response);
    });
  });

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
      {trip == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_next_title")}
          btn_footer_text={t("profile.lists.created_next_more")}
          empty_text={t("profile.lists.created_next_empty")}
          empty_icon={"car-front-fill"}
          data={trip}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}
      {trip == null ? (
        <h1>holaaa</h1>
      ) : (
        <ListProfileContainer
          title={t("profile.lists.created_prev_title")}
          btn_footer_text={t("profile.lists.created_prev_more")}
          empty_text={t("profile.lists.created_prev_empty")}
          empty_icon={"car-front-fill"}
          data={trip}
          component_name={CardTripProfile}
          link={createdTripsPath}
        />
      )}

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
