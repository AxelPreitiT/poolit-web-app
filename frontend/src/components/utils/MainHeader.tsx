import styles from "./styles.module.scss";

interface MainHeaderProps {
  title: string;
  left_component?: React.ReactNode;
}

// This component is a wrapper of route pages
// Should be used to:
//  - set the title of the page
const MainHeader = ({ title, left_component }: MainHeaderProps) => {
  return (
    <div>
      <div className={styles.main_header}>
        <h1 className={styles.color_header}>{title}</h1>
        {left_component}
      </div>
      <hr className={styles.color_header}></hr>
    </div>
  );
};

export default MainHeader;
