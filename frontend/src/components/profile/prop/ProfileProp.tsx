import styles from "./styles.module.scss";

export interface ProfilePropProps {
  prop: string;
  text: string;
}

const ProfileProp = ({ prop, text }: ProfilePropProps) => {
  return (
    <div>
      <p className={styles.propHeader}>{prop}</p>
      <p className={styles.propValue}>{text}</p>
    </div>
  );
};

export default ProfileProp;
