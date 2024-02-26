import TripSortTypeModel from "@/models/TripSortTypeModel";
import { ButtonGroup, Dropdown } from "react-bootstrap";
import styles from "./styles.module.scss";
import { useTranslation } from "react-i18next";
import { BiCaretDown } from "react-icons/bi";

const defaultInitialSortTypeId = "PRICE";
const defaultInitialDescending = false;

interface TripsSorterProps {
  sortTypes?: TripSortTypeModel[];
  currentSortTypeId?: string;
  currentDescending?: boolean;
  onSelect: (sortTypeId: string, descending: boolean) => void;
  className?: string;
}

const TripsSorter = ({
  sortTypes = [],
  currentSortTypeId = defaultInitialSortTypeId,
  currentDescending = defaultInitialDescending,
  onSelect: onSelectProp,
  className,
}: TripsSorterProps) => {
  const { t } = useTranslation();

  const sortTypeById = sortTypes.reduce(
    (obj, sortType) => {
      obj[sortType.id] = sortType;
      return obj;
    },
    {} as Record<string, TripSortTypeModel>
  );

  const isCurrentValue = (sortTypeId: string, descending: boolean) => {
    return sortTypeId === currentSortTypeId && descending === currentDescending;
  };

  const getName = (sortTypeId: string, descending: boolean) => {
    const sortType = sortTypeById[sortTypeId];
    return descending ? sortType.descName : sortType.ascName;
  };

  return (
    <div className={className}>
      <Dropdown as={ButtonGroup} className={styles.dropdown}>
        <Dropdown.Toggle className={styles.dropdownToggle}>
          <span>
            {t("trip_sort_types.sort_by", {
              sort_by: getName(currentSortTypeId, currentDescending),
            })}
          </span>
          <BiCaretDown />
        </Dropdown.Toggle>
        <Dropdown.Menu className={styles.dropdownMenu}>
          {sortTypes.map((sortType) =>
            [false, true].map((descending) => (
              <Dropdown.Item
                as="button"
                key={`${sortType.id}-${descending}`}
                onClick={() => onSelectProp(sortType.id, descending)}
                className={
                  isCurrentValue(sortType.id, descending)
                    ? styles.dropdownItemActive
                    : styles.dropdownItem
                }
              >
                {getName(sortType.id, descending)}
              </Dropdown.Item>
            ))
          )}
        </Dropdown.Menu>
      </Dropdown>
    </div>
  );
};

export default TripsSorter;
