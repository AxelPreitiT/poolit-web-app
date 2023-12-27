import styles from "./styles.module.scss";

export interface ProfilePropProps {
  prop: string;
  text: string;
}

const ProfileProp = ({ prop, text }: ProfilePropProps) => {
  return (
    <div>
      <h6 className={styles.propProfile}>{prop}</h6>
      <h4>{text}</h4>
    </div>
  );
};

export default ProfileProp;
