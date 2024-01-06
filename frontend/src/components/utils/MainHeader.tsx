import styles from "./styles.module.scss";

interface MainHeaderProps {
  children: React.ReactNode;
}

// This component is a wrapper of route pages
// Should be used to:
//  - set the title of the page
const MainHeader = ({ children }: MainHeaderProps) => {
  return (
    <div>
      <div className={styles.main_component}>{children}</div>
    </div>
  );
};

export default MainHeader;
