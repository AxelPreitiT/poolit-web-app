import styles from "./styles.module.scss";
import ProfilePropProps from "./ProfileInterface";

const ProfileProp = ({ prop, text }: ProfilePropProps) => (
  <div>
    <h6 className={styles.propProfile}>{text}</h6>
    <h4>{prop}</h4>
  </div>
);

export default ProfileProp;
