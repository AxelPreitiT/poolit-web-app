import { Button, Form, Image, InputGroup } from "react-bootstrap";
import styles from "./styles.module.scss";
import {
  BsArrowRepeat,
  BsCalendarFill,
  BsCarFrontFill,
  BsClockFill,
  BsFillPeopleFill,
  BsGeoAlt,
  BsGeoAltFill,
  BsInfoCircleFill,
} from "react-icons/bs";
import FormError from "@/components/forms/FormError/FormError";
import DatePicker from "@/components/forms/DatePicker/DatePicker";
import { FaCaretRight, FaDollarSign } from "react-icons/fa";
import RegisterBanner from "@/images/register-banner.jpg";
import { BiCheck } from "react-icons/bi";
import { useTranslation } from "react-i18next";
import CitySelector from "@/components/forms/CitySelector/CitySelector";
import CarSelector from "@/components/forms/CarSelector/CarSelector";
import TimePicker from "@/components/forms/TimePicker/TimePicker";

const CreateTripForm = () => {
  const { t } = useTranslation();

  return (
    <Form className={styles.form}>
      <div className={styles.formGroup} id="origin">
        <div className={styles.formHeader}>
          <BsGeoAlt className="h2 secondary-text" />
          <h2 className="secondary-text">{t("create_trip.origin")}</h2>
        </div>
        <div className={styles.formContainer}>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <CitySelector
                cities={[]}
                defaultOption={t("create_trip.district")}
                onChange={() => {}}
                size="sm"
              />
              <FormError error="error" className={styles.formItemError} />
            </div>
            <div className={styles.formItem}>
              <Form.Control
                type="text"
                placeholder={t("create_trip.address")}
                size="sm"
              />
              <FormError error="error" className={styles.formItemError} />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <Button className="secondary-btn">
                  <BsCalendarFill className="light-text" />
                </Button>
                <DatePicker
                  inputClass="form-control form-control-sm"
                  placeholder={t("create_trip.date")}
                  containerClassName={styles.pickerContainer}
                  onChange={() => {}}
                />
              </InputGroup>
              <FormError error="error" className={styles.formItemError} />
            </div>
            <div className={styles.formItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <Button className="secondary-btn">
                  <BsClockFill className="light-text" />
                </Button>
                <TimePicker
                  inputClass="form-control form-control-sm"
                  placeholder={t("create_trip.time")}
                  containerClassName={styles.pickerContainer}
                  onChange={() => {}}
                />
              </InputGroup>
              <FormError error="error" className={styles.formItemError} />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.recurrentCheckbox}>
              <Form.Check type="checkbox" />
              <label className="light-text">
                {t("create_trip.recurrent_trip")}
              </label>
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.recurrentLabel}>
              <BsArrowRepeat className="secondary-text" />
              <span className="secondary-text">{t("create_trip.every")}</span>
              <strong className="secondary-text">
                {t("day.sunday", { plural: "s" })}
              </strong>
              <span className="secondary-text">
                {t("create_trip.until").toLowerCase()}
              </span>
            </div>
            <div className={styles.formItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <Button className="secondary-btn">
                  <BsCalendarFill className="light-text" />
                </Button>
                <DatePicker
                  inputClass="form-control form-control-sm"
                  placeholder={t("create_trip.last_date")}
                  containerClassName={styles.pickerContainer}
                  onChange={() => {}}
                />
              </InputGroup>
              <FormError error="error" className={styles.formItemError} />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.verticalDottedLine}></div>
      <div className={styles.formGroup} id="destination">
        <div className={styles.formHeader}>
          <BsGeoAltFill className="h2 secondary-text" />
          <h2 className="secondary-text">{t("create_trip.destination")}</h2>
        </div>
        <div className={styles.formContainer}>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <CitySelector
                cities={[]}
                defaultOption={t("create_trip.district")}
                onChange={() => {}}
                size="sm"
              />
              <FormError error="error" className={styles.formItemError} />
            </div>
            <div className={styles.formItem}>
              <Form.Control
                type="text"
                placeholder={t("create_trip.address")}
                size="sm"
              />
              <FormError error="error" className={styles.formItemError} />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.formGroup} id="details">
        <div className={styles.formHeader}>
          <BsInfoCircleFill className="h2 secondary-text" />
          <h2 className="secondary-text">{t("create_trip.details")}</h2>
        </div>
        <div className={styles.formContainer}>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <InputGroup.Text>
                  <BsCarFrontFill className="text" />
                </InputGroup.Text>
                <CarSelector
                  cars={[]}
                  defaultOption={t("create_trip.select_car")}
                  onChange={() => {}}
                />
              </InputGroup>
              <div className={styles.carInfoContainer}>
                <div className={styles.carInfoItem}>
                  <FaCaretRight className="light-text" />
                  <span className="light-text">
                    {t("create_trip.brand", { brand: "Ford" })}
                  </span>
                </div>
                <hr className="light-text" />
                <div className={styles.carInfoItem}>
                  <FaCaretRight className="light-text" />
                  <span className="light-text">
                    {t("create_trip.license_plate", {
                      license_plate: "OIC319",
                    })}
                  </span>
                </div>
              </div>
              <FormError error="error" className={styles.formItemError} />
            </div>
            <div className={styles.formItem}>
              <Image
                rounded
                fluid
                alt={t("create_trip.car_image", { carInfo: "Ford OIC319" })}
                className={styles.carImage}
                src={RegisterBanner}
              />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <InputGroup.Text>
                  <BsFillPeopleFill className="text" />
                </InputGroup.Text>
                <Form.Control
                  type="text"
                  placeholder={t("create_trip.number_of_seats")}
                />
              </InputGroup>
              <FormError error="error" className={styles.formItemError} />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <InputGroup size="sm" className={styles.formItemGroup}>
                <InputGroup.Text>
                  <FaDollarSign className="text" />
                </InputGroup.Text>
                <Form.Control
                  type="text"
                  placeholder={t("create_trip.price_per_trip")}
                />
                <InputGroup.Text>ARS</InputGroup.Text>
              </InputGroup>
              <FormError error="error" className={styles.formItemError} />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.submitContainer}>
        <Button type="submit" className="secondary-btn">
          <BiCheck className="light-text h2" />
          <span className="light-text h3">{t("create_trip.create")}</span>
        </Button>
      </div>
    </Form>
  );
};

export default CreateTripForm;
