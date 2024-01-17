import Spinner from 'react-bootstrap/Spinner';
import styles from "./styles.module.scss";

const SpinnerComponent = () => {
    return (
        <div className={styles.spinner_container}>
            <Spinner animation="border" size="sm" />
        </div>
    )
}

export default SpinnerComponent;