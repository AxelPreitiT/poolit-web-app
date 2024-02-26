import styles from "./styles.module.scss";

interface MainComponentProps {
  children: React.ReactNode;
}

// This component is a wrapper of route pages
// Should be used to:
//  - set the title of the page
const MainComponent = ({ children }: MainComponentProps) => {
  return <div className={styles.main_component}>{children}</div>;
};

export default MainComponent;
