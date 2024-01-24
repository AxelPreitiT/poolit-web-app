import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import Dropdown from "react-bootstrap/Dropdown";
import SpinnerComponent from "@/components/Spinner/Spinner.tsx";
import PaginationList from "@/components/paginationList/paginationList.tsx";
import PassangerComponent from "@/components/passanger/Passanger.tsx";
import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import { useState } from "react";
import PassangerModel from "@/models/PassangerModel.ts";
import { useTranslation } from "react-i18next";

interface PassangersTripComponentProps {
  passangers: PassangerModel[];
}

const PassangersTripComponent = ({
  passangers,
}: PassangersTripComponentProps) => {
  const { t } = useTranslation();
  const options = ["All", "Accepted", "Waiting", "Rejected"];
  const [selectedOption, setSelectedOption] = useState<string>("All");
  const handleSelect = (eventKey: string | null) => {
    if (eventKey !== null) {
      setSelectedOption(eventKey);
    }
  };

  return (
    <MainComponent>
      <MainHeader title={t("trip_detail.passengers.header")} />
      <div className={styles.dropdown_style}>
        <Dropdown onSelect={handleSelect}>
          <Dropdown.Toggle id="dropdown-basic" className={styles.btn_dropdown}>
            {t("trip_detail.filter_by", {
              status: selectedOption,
            })}
          </Dropdown.Toggle>
          <Dropdown.Menu className={styles.dropdown_menu_passanger}>
            {options.map((option, index) => (
              <Dropdown.Item key={index} eventKey={option}>
                {option}
              </Dropdown.Item>
            ))}
          </Dropdown.Menu>
        </Dropdown>
      </div>
      <div className={styles.passangers_container}>
        {passangers === null ? (
          <SpinnerComponent />
        ) : (
          <PaginationList
            pagination_component={<h3>Poner paginación</h3>}
            empty_component={
              <div className={styles.review_empty_container}>
                <i className={`bi-solid bi-people h2`}></i>
                <h3 className="italic-text placeholder-text">
                  {t("trip_detail.passengers.empty")}
                </h3>
              </div>
            }
            data={passangers}
            Item={PassangerComponent}
          />
        )}
      </div>
    </MainComponent>
  );
};

export default PassangersTripComponent;
