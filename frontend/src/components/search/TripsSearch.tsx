import CarFeatureModel from "@/models/CarFeatureModel";
import CityModel from "@/models/CityModel";
import styles from "./styles.module.scss";
import { Button, Collapse, Form, InputGroup, Nav } from "react-bootstrap";
import {
  BsArrowLeftRight,
  BsArrowRepeat,
  BsCalendar,
  BsCalendarEventFill,
  BsCalendarFill,
  BsCarFrontFill,
  BsClockFill,
  BsCurrencyDollar,
  BsGeoAltFill,
} from "react-icons/bs";
import { useState } from "react";
import CitySelector from "../forms/CitySelector/CitySelector";
import FormError from "../forms/FormError/FormError";
import { BiSearch } from "react-icons/bi";
import CarFeaturesPills from "../car/CarFeaturesPills/CarFeaturesPills";

const uniqueTripId = "unique";
const recurrentTripId = "recurrent";

interface TripsSearchProps {
  cities?: CityModel[];
  carFeatures?: CarFeatureModel[];
}

const TripsSearch = ({ cities, carFeatures }: TripsSearchProps) => {
  // const { t } = useTranslation();
  const [activeTab, setActiveTab] = useState<string>(uniqueTripId);
  const [showLastDate, setShowLastDate] = useState<boolean>(false);

  const onTabSelect = (eventKey: string | null) => {
    if (eventKey === recurrentTripId) {
      setShowLastDate(true);
    } else {
      setShowLastDate(false);
    }
    if (eventKey) {
      setActiveTab(eventKey);
    }
  };

  return (
    <div className={styles.searchContainer}>
      <Nav
        variant="tabs"
        defaultActiveKey={uniqueTripId}
        onSelect={onTabSelect}
        className={styles.searchTabs}
      >
        <Nav.Item className={styles.searchTab}>
          <Nav.Link
            eventKey={uniqueTripId}
            className={
              activeTab === uniqueTripId ? styles.activeTab : styles.tab
            }
          >
            <BsCalendarEventFill className="light-text" />
            <span className="light-text">Unique trip</span>
          </Nav.Link>
        </Nav.Item>
        <Nav.Item className={styles.searchTab}>
          <Nav.Link
            eventKey={recurrentTripId}
            className={
              activeTab === recurrentTripId ? styles.activeTab : styles.tab
            }
          >
            <BsArrowRepeat className="light-text" />
            <span className="light-text">Recurrent trip</span>
          </Nav.Link>
        </Nav.Item>
      </Nav>
      <Form className={styles.searchForm}>
        <div className={styles.searchFormGroup}>
          <div className={styles.searchFormTitle}>
            <BsGeoAltFill className="light-text" />
            <span className="light-text">Route</span>
          </div>
          <div className={styles.searchFormInput}>
            <div className={styles.searchFormInputItem}>
              <Form.Group className={styles.searchFormRouteInput}>
                <Form.Label className="light-text">Origin</Form.Label>
                <CitySelector
                  cities={cities || []}
                  defaultOption="District"
                  onChange={() => {}}
                  value={-1}
                  size="sm"
                />
              </Form.Group>
              <FormError error="error" />
            </div>
            <Button type="button" className="secondary-btn">
              <BsArrowLeftRight className="light-text" />
            </Button>
            <div className={styles.searchFormInputItem}>
              <Form.Group className={styles.searchFormRouteInput}>
                <Form.Label className="light-text">Destination</Form.Label>
                <CitySelector
                  cities={cities || []}
                  defaultOption="District"
                  onChange={() => {}}
                  value={-1}
                  size="sm"
                />
              </Form.Group>
              <FormError error="error" />
            </div>
          </div>
        </div>
        <div className={styles.searchFormGroup}>
          <div className={styles.searchFormTitle}>
            <BsCalendar className="light-text" />
            <span className="light-text">Date</span>
          </div>
          <div className={styles.searchFormInput}>
            <div className={styles.searchFormInputItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <Button className="secondary-btn">
                  <BsCalendarFill className="light-text" />
                </Button>
                <Form.Control type="text" placeholder="Date" size="sm" />
              </InputGroup>
              <FormError error="error" />
            </div>
            <div className={styles.searchFormInputItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <Button className="secondary-btn">
                  <BsClockFill className="light-text" />
                </Button>
                <Form.Control type="text" placeholder="Time" size="sm" />
              </InputGroup>
              <FormError error="error" />
            </div>
          </div>
          <Collapse in={showLastDate}>
            <div className={styles.searchFormInput}>
              <div className={styles.searchFormInputItem}>
                <div className={styles.searchFormLastDateDecorator}>
                  <BsArrowRepeat className="light-text" />
                  <div className={styles.searchFormLastDateInputText}>
                    <span className="light-text">Every</span>
                    <strong className="light-text">Monday</strong>
                    <span className="light-text">until</span>
                  </div>
                </div>
              </div>
              <div className={styles.searchFormInputItem}>
                <InputGroup size="sm" className={styles.formItemGroup}>
                  <Button className="secondary-btn">
                    <BsCalendarFill className="light-text" />
                  </Button>
                  <Form.Control type="text" placeholder="Last date" size="sm" />
                </InputGroup>
                <FormError error="error" />
              </div>
            </div>
          </Collapse>
        </div>
        <div className={styles.searchFormGroup}>
          <div className={styles.searchFormTitle}>
            <BsCurrencyDollar className="light-text" />
            <span className="light-text">Price (per trip)</span>
          </div>
          <div className={styles.searchFormInput}>
            <div className={styles.searchFormInputItem}>
              <Form.Control type="text" placeholder="Minimum" size="sm" />
              <FormError error="error" />
            </div>
            <span className="light-text">-</span>
            <div className={styles.searchFormInputItem}>
              <Form.Control type="text" placeholder="Maximum" size="sm" />
              <FormError error="error" />
            </div>
            <span className="light-text">ARS</span>
          </div>
        </div>
        {carFeatures && carFeatures.length > 0 && (
          <div className={styles.searchFormGroup}>
            <div className={styles.searchFormTitle}>
              <BsCarFrontFill className="light-text" />
              <span className="light-text">Car features</span>
            </div>
            <div className={styles.searchFormInput}>
              <CarFeaturesPills carFeatures={carFeatures} />
            </div>
          </div>
        )}
        <div className={styles.searchFormSubmit}>
          <Button type="submit" className="secondary-btn">
            <BiSearch className="light-text" />
            <span className="light-text">Search</span>
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default TripsSearch;
