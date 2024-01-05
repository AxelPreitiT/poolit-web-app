import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Link, useLocation } from "react-router-dom";
import Section from "@/types/Section";
import "./navbarStyles.css"; // Importa el archivo CSS auxiliar
import PoolitLogo from "@/images/poolit.svg";
import CircleImg from "../img/circleImg/CircleImg";
import ProfilePhoto from "@/images/descarga.jpeg";
import {
  profilePath,
  createdTripsPath,
  reservedTripsPath,
  createTripsPath,
} from "@/AppRouter";
import Button from "react-bootstrap/Button";

const NavbarWrapper = () => {
  const { t } = useTranslation();
  const location = useLocation();
  const pathname = location?.pathname;

  const sections: Section[] = [
    { path: createdTripsPath, name: "Mis cursos" },
    { path: reservedTripsPath, name: "Mis anuncios" },
  ];

  return (
    <div className="nav-container">
      <div className="nav-sections-container">
        <div className="left-container">
          <div className="img-container">
            <img
              src={PoolitLogo}
              alt={t("poolit.logo")}
              className={styles.logoImg}
            />
          </div>
          {sections.map((section) => (
            <div
              className={`nav-section-item ${
                pathname === section.path ? "active" : ""
              }`}
              key={section.path}
            >
              <Link to={section.path}>
                <h4>{section.name}</h4>
              </Link>
            </div>
          ))}
        </div>
        <div className="right-container">
          <div>
            <Link to={createTripsPath}>
              <Button
                variant="primary"
                size="lg"
                active
                className="create-trip-btn"
              >
                <i className="bi bi-plus-lg light-text h4"></i>
                <span className="button-text-style light-text h4">
                  Create Trip
                </span>
              </Button>
            </Link>
          </div>
          <div className="img-profile-container">
            <CircleImg src={ProfilePhoto} size={50} path={profilePath} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default NavbarWrapper;
