import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { Link, useLocation } from "react-router-dom";
import Section from "@/types/Section";
import "./navbarStyles.css"; // Importa el archivo CSS auxiliar
import PoolitLogo from "@/images/poolit.svg";
import CircleImg from "../img/circleImg/CircleImg";
import ProfilePhoto from "@/images/descarga.jpeg";
import {
  homePath,
  profilePath,
  createdTripsPath,
  reservedTripsPath,
  createTripsPath,
} from "@/AppRouter";
import Button from "react-bootstrap/Button";
import Dropdown from "react-bootstrap/Dropdown";
import { useState } from "react";

const Navbar = () => {
  const { t } = useTranslation();
  const location = useLocation();
  const pathname = location?.pathname;

  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => setDropdownOpen(!dropdownOpen);

  const sections: Section[] = [
    { path: createdTripsPath, name: t("navbar.created") },
    { path: reservedTripsPath, name: t("navbar.reserved") },
  ];

  return (
    <div className="nav-container">
      <div className="nav-sections-container">
        <div className="left-container">
          <div className="img-container">
            <Link to={homePath}>
              <img
                src={PoolitLogo}
                alt={t("poolit.logo")}
                className={styles.logoImg}
              />
            </Link>
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

          <Dropdown show={dropdownOpen} onToggle={toggleDropdown} drop="down">
            <Dropdown.Toggle variant="link" id="profile-dropdown">
              <div className="img-profile-container">
                <CircleImg src={ProfilePhoto} size={50} />
              </div>
            </Dropdown.Toggle>
            <Dropdown.Menu className={styles.menu_dropdown}>
              <Dropdown.Item as={Link} to={profilePath}>
                <div className={styles.item_dropdown}>
                  <CircleImg src={ProfilePhoto} size={28} />
                  <h3 className={styles.dropdown_text}>
                    Poner nombreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                  </h3>
                </div>
              </Dropdown.Item>
              <Dropdown.Item as={Link} to={homePath}>
                <div className={styles.item_dropdown}>
                  <div className={styles.dropdown_text}>
                    <i className="bi bi-box-arrow-right light-text h4"></i>
                  </div>
                  <h4 className={styles.dropdown_text}>
                    {t("navbar.log_out")}
                  </h4>
                </div>
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
