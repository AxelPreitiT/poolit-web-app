import ProfileImg from "@/components/profile/img/ProfileImg";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ListContainer from "@/components/profile/list/ListContainer";
import ShortReview from "@/components/review/shorts/ShortReview";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import CardTrip from "@/components/cardTrip/cardTrip";
import { Trip } from "@/components/cardTrip/cardTrip";
import CardCar from "@/components/cardCar/CardCar";

const ProfilePage = () => {
  const { t } = useTranslation();

  const user = {
    name: "Gaston Francois",
    email: "gfrancois@itba.edu.ar",
    phone: "3424394741",
    neighborhood: "Balvanera",
    language: "Español",
    trips: "5",
    rating_driver: 3.5,
    rating_passenger: 1.5,
  };

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
    <div className={styles.main_container}>
      <div className={styles.profileCard}>
        <ProfileImg dim={2} src={ProfilePhoto} />
        <h3 className="text-center">{user.name}</h3>
        <ProfileProp prop={t("profile.props.email")} text={user.email} />
        <ProfileProp prop={t("profile.props.phone")} text={user.phone} />
        <ProfileProp
          prop={t("profile.props.neighborhood")}
          text={user.neighborhood}
        />
        <ProfileProp prop={t("profile.props.language")} text={user.language} />
        <ProfileProp prop={t("profile.props.trips")} text={user.trips} />
        <ProfileStars
          prop={t("profile.props.rating_driver")}
          rating={user.rating_driver}
        />
        <ProfileStars
          prop={t("profile.props.rating_passenger")}
          rating={user.rating_passenger}
        />
      </div>

      <div className={styles.list_block}>
        <ListContainer
          title={t("profile.lists.review_as_driver")}
          btn_footer_text={t("profile.lists.review_more")}
          empty_text={t("profile.lists.review_empty")}
          empty_icon={"book"}
          data={data2}
          component_name={ShortReview}
        />
        <ListContainer
          title={t("profile.lists.created_next_title")}
          btn_footer_text={t("profile.lists.created_next_more")}
          empty_text={t("profile.lists.created_next_empty")}
          empty_icon={"car-front-fill"}
          data={data}
          component_name={CardTrip}
        />
        <ListContainer
          title={t("profile.lists.created_prev_title")}
          btn_footer_text={t("profile.lists.created_prev_more")}
          empty_text={t("profile.lists.created_prev_empty")}
          empty_icon={"car-front-fill"}
          data={data}
          component_name={CardTrip}
        />
        <ListContainer
          title={t("profile.lists.cars")}
          btn_footer_text={t("profile.lists.cars_create")}
          empty_text={t("profile.lists.cars_empty")}
          empty_icon={"car-front-fill"}
          data={data3}
          component_name={CardCar}
        />
      </div>
    </div>
  );
};

export default ProfilePage;
