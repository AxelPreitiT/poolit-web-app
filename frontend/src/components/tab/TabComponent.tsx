import Nav from "react-bootstrap/Nav";
import Tab from "react-bootstrap/Tab";
import styles from "./styles.module.scss";

interface TabComponentProps {
  right_component: React.ReactNode;
  left_component: React.ReactNode;
  right_title: string;
  left_title: string;
  active?: "left" | "right";
  onLeftClick?: () => void;
  onRightClick?: () => void;
}

const TabComponent = ({
  right_component,
  left_component,
  right_title,
  left_title,
  active,
  onLeftClick,
  onRightClick,
}: TabComponentProps) => {
  return (
    <Tab.Container
      id="center-tabs-example"
      activeKey={active ? active : "left"}
    >
      <div>
        <Nav variant="pills" className={styles.nav_prop}>
          <Nav.Item className={styles.fulltab}>
            <Nav.Link
              eventKey="left"
              className={styles.custom_tab}
              onClick={onLeftClick}
            >
              <h3>{left_title}</h3>
            </Nav.Link>
          </Nav.Item>
          <Nav.Item className={styles.fulltab}>
            <Nav.Link
              eventKey="right"
              className={styles.custom_tab}
              onClick={onRightClick}
            >
              <h3>{right_title}</h3>
            </Nav.Link>
          </Nav.Item>
        </Nav>
        <Tab.Content>
          <Tab.Pane eventKey="left">{left_component}</Tab.Pane>
          <Tab.Pane eventKey="right">{right_component}</Tab.Pane>
        </Tab.Content>
      </div>
    </Tab.Container>
  );
};

export default TabComponent;
