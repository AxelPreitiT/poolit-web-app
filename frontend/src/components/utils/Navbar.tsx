import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import PoolitLogo from "@/images/poolit.svg";
import { homePath } from "@/AppRouter";
import { NavLink } from "react-router-dom";

interface RouterComponentProps {
  children: React.ReactNode;
}

const NavbarWrapper = ({ children }: RouterComponentProps) => {
  const { t } = useTranslation();

  return (
    <>
      <Navbar expand="lg" className={styles.navbar}>
        <Navbar.Brand href={homePath}>
          <img
            src={PoolitLogo}
            alt={t("poolit.logo")}
            className={styles.logoImg}
          />
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav>
            <Nav.Link href="#pepe">
              <h4>Home</h4>
            </Nav.Link>
            <Nav.Link href="#link">
              <h4>Link</h4>
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
      {children}
    </>
  );
};

export default NavbarWrapper;
