import styles from "./styles.module.scss";
import Dropdown from "react-bootstrap/Dropdown";
import PassangerComponent from "@/components/passanger/Passanger.tsx";
import MainComponent from "@/components/utils/MainComponent.tsx";
import MainHeader from "@/components/utils/MainHeader.tsx";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useLocation, useSearchParams } from "react-router-dom";
import createPaginationUri from "@/functions/CreatePaginationUri.tsx";
import usePassangerByUri from "@/hooks/passanger/usePassangerByUri.tsx";
import PassangerStatus from "@/enums/PassangerStatus.ts";
import { INITIALPAGE, PASSANGERPAGESIZE } from "@/enums/PaginationConstants.ts";
import { parseTemplate } from "url-template";
import PaginationComponentExtraData from "@/components/pagination/PaginationComponent/PaginationComponentExtraData.tsx";
import { useQueryClient } from "@tanstack/react-query";
import { BiCaretDown } from "react-icons/bi";
import { ButtonGroup } from "react-bootstrap";

interface PassangersTripComponentProps {
  uri: string;
  fullSeats: boolean;
}

const PassangersTripComponent = ({
  uri,
  fullSeats,
}: PassangersTripComponentProps) => {
  const { t } = useTranslation();

  const { search } = useLocation();
  const page = new URLSearchParams(search).get("page");
  const currentPage = page == null ? INITIALPAGE : parseInt(page, 10);
  const [selectedOption, setSelectedOption] = useState<string>(
    PassangerStatus.ALL
  );

  const [params] = useSearchParams();
  const startDateTime = params.get("startDateTime") || "";
  const endDateTime = params.get("endDateTime") || "";
  const newUri = parseTemplate(uri as string).expand({
    userId: null,
    startDateTime: startDateTime,
    endDateTime: endDateTime,
    passengerState:
      selectedOption == PassangerStatus.ALL ? null : selectedOption,
  });
  const queryClient = useQueryClient();

  const options = Object.values(PassangerStatus);

  const handleSelect = (eventKey: string | null) => {
    if (eventKey !== null) {
      setSelectedOption(eventKey);
      queryClient.invalidateQueries({ queryKey: ["passangersPagination"] });
    }
  };

  return (
    <MainComponent>
      <MainHeader title={t("trip_detail.passengers.header")} />
      <div className={styles.dropdown_style}>
        <Dropdown onSelect={handleSelect} as={ButtonGroup}>
          <Dropdown.Toggle id="dropdown-basic" className={styles.btn_dropdown}>
            <span>
              {t("trip_detail.filter_by", {
                status: t("trip_detail.status." + selectedOption.toLowerCase()),
              })}
            </span>
            <BiCaretDown />
          </Dropdown.Toggle>
          <Dropdown.Menu className={styles.dropdown_menu_passanger}>
            {options.map((option, index) => (
              <Dropdown.Item
                as="button"
                key={index}
                eventKey={option}
                className={option === selectedOption ? styles.active : ""}
              >
                {t("trip_detail.status." + option.toLowerCase())}
              </Dropdown.Item>
            ))}
          </Dropdown.Menu>
        </Dropdown>
      </div>
      <div className={styles.passangers_container}>
        <PaginationComponentExtraData
          CardComponent={PassangerComponent}
          extraData={fullSeats}
          uri={createPaginationUri(newUri, currentPage, PASSANGERPAGESIZE)}
          current_page={currentPage}
          useFuction={usePassangerByUri}
          empty_component={
            <div className={styles.review_empty_container}>
              <h3 className="italic-text placeholder-text">
                {t("trip_detail.passengers.empty_modal")}
              </h3>
            </div>
          }
          itemsName={t("trip_detail.passengers.header")}
        />
      </div>
    </MainComponent>
  );
};

export default PassangersTripComponent;
