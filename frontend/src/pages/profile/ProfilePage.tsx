import ProfileImg from "@/components/profile/img/ProfileImg";
import styles from "./styles.module.scss";
import {useTranslation} from "react-i18next";
import ProfilePhoto from "@/images/descarga.jpeg";
import ProfileProp from "@/components/profile/prop/ProfileProp";
import ProfileStars from "@/components/profile/stars/ProfileStars";
import DriverList from "@/components/profile/ProfileLists/DriverList";
import PassengerList from "@/components/profile/ProfileLists/PassangerList";
import TabComponent from "@/components/tab/TabComponent";
import {useCurrentUser} from "@/hooks/users/useCurrentUser.tsx";
import UserPrivateModel from "@/models/UserPrivateModel.ts";

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

  /*
  const [city, setCity] = useState<CityModel|null >(null);


    useEffect(() => {
      CityService.getCityById("http://localhost:8080/paw-2023a-07/api/cities/12").then(response => {
        // Extraer el cuerpo de la respuesta Axios
        // Luego, llamar a setProfileInfo con el resultado
        setCity(response);
      })
    })

   */

    const {isLoading, currentUser} = useCurrentUser();

    const ProfileInfo =({ currentUser }: { currentUser: UserPrivateModel }) => (
        <div className={styles.profileCard}>
            <ProfileImg src={ProfilePhoto} />
            <h3 className="text-center">
                {t("format.name", {
                    name: currentUser.username,
                    surname: currentUser.surname,
                })}</h3>
            <ProfileProp prop={t("profile.props.email")} text={currentUser.email} />
            <ProfileProp prop={t("profile.props.phone")} text={currentUser.phone} />
            <ProfileProp
                prop={t("profile.props.neighborhood")}
                text={user.neighborhood}
            />
            <ProfileProp prop={t("profile.props.language")} text={currentUser.mailLocale} />
            <ProfileProp prop={t("profile.props.trips")} text={user.trips} />
            <ProfileStars
                prop={t("profile.props.rating_driver")}
                rating={currentUser.driverRating}
            />
            <ProfileStars
                prop={t("profile.props.rating_passenger")}
                rating={currentUser.passengerRating}
            />
        </div>
    )

  return (
    <div className={styles.main_container}>
        {isLoading || currentUser===undefined ?
            <div className={styles.profileCard}>
                <h3>Loading...</h3>
            </div>:
            <ProfileInfo currentUser={currentUser} />
        }

      <div className={styles.list_block}>
          {isLoading || currentUser===undefined ?
              <TabComponent
                  right_title={t("roles.passenger")}
                  right_component={<h3>Loading...</h3>}
                  left_title={t("roles.driver")}
                  left_component={<h3>Loading...</h3>}
              />:
              <TabComponent
                  right_title={t("roles.passenger")}
                  //right_component={<PassengerList futureReservedTrips={currentUser.futureReservedTripsUri} pastReservedTrips={currentUser.pastReservedTripsUri}/>}
                  right_component={<PassengerList/>}
                  left_title={t("roles.driver")}
                  //left_component={<DriverList futureCreatedTrips={currentUser.futureCreatedTripsUri} pastCreatedTrips={currentUser.pastCreatedTripsUri}/>}
                  left_component={<DriverList/>}
              />
          }
      </div>
    </div>
  );
};

export default ProfilePage;
