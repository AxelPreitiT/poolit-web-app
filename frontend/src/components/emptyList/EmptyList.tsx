import styles from "./styles.module.scss";

interface EmptyListProps {
  icon: string;
  text: string;
  second_text?: string;
}

const EmptyList = ({ icon, text, second_text }: EmptyListProps) => (
  <div className={styles.empty_list_cointainer}>
    <i className={`bi-solid bi-${icon} h1`}></i>
    <h3>{text}</h3>
    {second_text && (
      <h6 className="italic-text placeholder-text">{second_text}</h6>
    )}
  </div>
);

export default EmptyList;
