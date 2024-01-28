import "bootstrap/dist/css/bootstrap.min.css";
import styles from "./styles.module.scss";
import Dropdown from "react-bootstrap/Dropdown";
import PassangerComponent from "@/components/passanger/Passanger.tsx";
import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import PaginationComponent from "@/components/pagination/PaginationComponent/PaginationComponent.tsx";
import {useLocation} from "react-router-dom";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import createStatusPaginationUri from "@/functions/CreateStatusPaginationUri.tsx";
import usePassangerByUri from "@/hooks/passanger/usePassangerByUri.tsx";
import PassangerStatus from "@/enums/PassangerStatus.ts";
import {INITIALPAGE, PASSANGERPAGESIZE} from "@/enums/PaginationConstants.ts";

interface PassangersTripComponentProps {
  uri: string;
}

const PassangersTripComponent = ({
  uri,
}: PassangersTripComponentProps) => {
    const { t } = useTranslation();

    const { search } = useLocation();
    const page = new URLSearchParams(search).get("page");
    const currentPage = page == null ? INITIALPAGE : parseInt(page, 10);


  const options = Object.values(PassangerStatus);
  const [selectedOption, setSelectedOption] = useState<string>(PassangerStatus.ALL);
  const handleSelect = (eventKey: string | null) => {
    if (eventKey !== null) {
      setSelectedOption(eventKey)
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
          <PaginationComponent
              empty_component={
                  <div className={styles.review_empty_container}>
                      <i className={`bi-solid bi-people h2`}></i>
                      <h3 className="italic-text placeholder-text">
                          {t("trip_detail.passengers.empty")}
                      </h3>
                  </div>}
              uri={createStatusPaginationUri(createPaginationUri(uri, currentPage , PASSANGERPAGESIZE, true), selectedOption)}
              current_page={currentPage}
              component_name={PassangerComponent}
              useFuction={usePassangerByUri} />
      </div>
    </MainComponent>
  );
};

export default PassangersTripComponent;
