import { Button, Collapse, Form, InputGroup } from "react-bootstrap";
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
import { FaDollarSign } from "react-icons/fa";
import { BiCheck } from "react-icons/bi";
import { useTranslation } from "react-i18next";
import CitySelector, {
  citySelectorDefaultValue,
} from "@/components/forms/CitySelector/CitySelector";
import CarSelector, {
  carSelectorDefaultValue,
} from "@/components/forms/CarSelector/CarSelector";
import useCreateTripForm from "@/hooks/forms/useCreateTripForm";
import { useCallback, useEffect, useState } from "react";
import { Controller } from "react-hook-form";
import LoadingButton from "@/components/buttons/LoadingButton";
import DatePicker, { DateObject } from "react-multi-date-picker";
import ReactTimePicker from "react-multi-date-picker/plugins/time_picker";
import { parseInputFloat } from "@/utils/float/parse";
import { parseInputInteger } from "@/utils/integer/parse";
import CityModel from "@/models/CityModel";
import CarModel from "@/models/CarModel";
import { getToday } from "@/utils/date/today";
import { getNextDay } from "@/utils/date/nextDay";
import { getDayString } from "@/utils/date/dayString";
import CarInfoCard from "@/components/car/CarInfoCard/CarInfoCard";
import CarImage from "@/components/car/CarImage/CarImage";
import { weekDays } from "@/utils/date/weekDays";
import { months } from "@/utils/date/months";

const useLastDateCollapse = (removeLastDate: () => void) => {
  const [date, setDate] = useState<Date | undefined>(undefined);
  const [isMultitrip, setIsMultitrip] = useState<boolean>(false);
  const [showLastDate, setShowLastDate] = useState<boolean>(false);

  useEffect(() => {
    if (date && isMultitrip) {
      setShowLastDate(true);
    } else {
      setShowLastDate(false);
      removeLastDate();
    }
  }, [date, isMultitrip, removeLastDate]);

  return {
    showLastDate,
    setDate,
    setIsMultitrip,
    date,
  };
};

const useCarInfoCollapse = () => {
  const [showCarInfo, setShowCarInfo] = useState<boolean>(false);
  const [car, setCar] = useState<CarModel | undefined>(undefined);

  useEffect(() => {
    if (car !== undefined) {
      setShowCarInfo(true);
    } else {
      setShowCarInfo(false);
    }
  }, [car]);

  return {
    showCarInfo,
    car,
    setCar,
  };
};

const CreateTripForm = ({
  cities = [],
  cars = [],
}: {
  cities?: CityModel[];
  cars?: CarModel[];
}) => {
  const { t } = useTranslation();

  const {
    register,
    handleSubmit,
    control,
    formState: { errors, isSubmitting },
    tFormError,
    setValue,
  } = useCreateTripForm();

  const removeLastDate = useCallback(
    () => setValue("last_date", undefined),
    [setValue]
  );

  const setCarSeats = useCallback(
    (seats: number) =>
      setValue("seats", seats, {
        shouldValidate: true,
        shouldDirty: true,
      }),
    [setValue]
  );

  const { showLastDate, setDate, setIsMultitrip, date } =
    useLastDateCollapse(removeLastDate);
  const minLastDate = date ? getNextDay(date) : getToday();
  const { showCarInfo, car, setCar } = useCarInfoCollapse();

  const tWeekDays = weekDays.map((day) => t(`day.short.${day.toLowerCase()}`));
  const tMonths = months.map((month) => t(`month.full.${month.toLowerCase()}`));

  return (
    <Form className={styles.form} onSubmit={handleSubmit}>
      <div className={styles.formGroup} id="origin">
        <div className={styles.formHeader}>
          <BsGeoAlt className="h2 secondary-text" />
          <h2 className="secondary-text">{t("create_trip.origin")}</h2>
        </div>
        <div className={styles.formContainer}>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <Controller
                name="origin_city"
                control={control}
                defaultValue={citySelectorDefaultValue}
                render={({ field: { onChange, value } }) => (
                  <CitySelector
                    cities={cities}
                    defaultOption={t("create_trip.district")}
                    onChange={(event) => onChange(parseInt(event.target.value))}
                    size="sm"
                    value={value}
                  />
                )}
              />
              <FormError
                error={tFormError(errors.origin_city)}
                className={styles.formItemError}
              />
            </div>
            <div className={styles.formItem}>
              <Form.Control
                type="text"
                placeholder={t("create_trip.address")}
                size="sm"
                {...register("origin_address")}
              />
              <FormError
                error={tFormError(errors.origin_address)}
                className={styles.formItemError}
              />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <Controller
                name="date"
                control={control}
                render={({ field: { onChange, value } }) => (
                  <DatePicker
                    weekDays={tWeekDays}
                    months={tMonths}
                    inputClass="form-control form-control-sm"
                    containerClassName={styles.pickerContainer}
                    onChange={(date: DateObject) => {
                      const dateValue = date?.toDate();
                      onChange(dateValue);
                      removeLastDate();
                      setDate(dateValue);
                    }}
                    format="DD/MM/YYYY"
                    value={value}
                    minDate={getToday()}
                    render={(value, onFocus, onChange) => (
                      <InputGroup size="sm" className={styles.formItemGroup}>
                        <Button className="secondary-btn" onClick={onFocus}>
                          <BsCalendarFill className="light-text" />
                        </Button>
                        <Form.Control
                          type="text"
                          placeholder={t("create_trip.date")}
                          size="sm"
                          onFocus={onFocus}
                          value={value || ""}
                          onChange={onChange}
                        />
                      </InputGroup>
                    )}
                  />
                )}
              />
              <FormError
                error={tFormError(errors.date)}
                className={styles.formItemError}
              />
            </div>
            <div className={styles.formItem}>
              <Controller
                name="time"
                control={control}
                defaultValue=""
                render={({ field: { onChange, value } }) => {
                  const [hours, minutes] = value.split(":");
                  const dateValue =
                    hours && minutes
                      ? new DateObject().set({
                          hour: parseInt(hours),
                          minute: parseInt(minutes),
                        })
                      : undefined;
                  return (
                    <DatePicker
                      disableDayPicker
                      inputClass="form-control form-control-sm"
                      placeholder={t("create_trip.time")}
                      containerClassName={styles.pickerContainer}
                      onChange={(date) => onChange(date?.toString() || "")}
                      format="HH:mm"
                      value={dateValue}
                      plugins={[<ReactTimePicker hideSeconds mStep={5} />]}
                      render={(value, onFocus, onChange) => (
                        <InputGroup size="sm" className={styles.formItemGroup}>
                          <Button className="secondary-btn" onClick={onFocus}>
                            <BsClockFill className="light-text" />
                          </Button>
                          <Form.Control
                            type="text"
                            placeholder={t("create_trip.time")}
                            size="sm"
                            onFocus={onFocus}
                            value={value || ""}
                            onChange={onChange}
                          />
                        </InputGroup>
                      )}
                    />
                  );
                }}
              />
              <FormError
                error={tFormError(errors.time)}
                className={styles.formItemError}
              />
            </div>
          </div>
          <div className={styles.formRow}>
            <div className={styles.recurrentCheckbox}>
              <Form.Check
                type="checkbox"
                {...register("multitrip")}
                onChange={(e) => setIsMultitrip(e.target.checked)}
              />
              <label className="light-text">
                {t("create_trip.recurrent_trip")}
              </label>
            </div>
          </div>
          <Collapse in={showLastDate}>
            <div className={styles.formRow}>
              {date && (
                <div className={styles.recurrentLabel}>
                  <BsArrowRepeat className="secondary-text" />
                  <span className="secondary-text">
                    {t("create_trip.every")}
                  </span>
                  <strong className="secondary-text">
                    {t(`day.full.${getDayString(date).toLowerCase()}`, {
                      plural: "s",
                    })}
                  </strong>
                  <span className="secondary-text">
                    {t("create_trip.until").toLowerCase()}
                  </span>
                </div>
              )}
              <div className={styles.formItem}>
                <Controller
                  name="last_date"
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <DatePicker
                      weekDays={tWeekDays}
                      months={tMonths}
                      mapDays={({ date: calendarDate }) => ({
                        disabled: calendarDate.weekDay.index !== date?.getDay(),
                      })}
                      inputClass="form-control form-control-sm"
                      placeholder={t("create_trip.last_date")}
                      containerClassName={styles.pickerContainer}
                      onChange={(date: DateObject) => onChange(date?.toDate())}
                      format="DD/MM/YYYY"
                      value={value}
                      minDate={minLastDate}
                      render={(value, onFocus, onChange) => (
                        <InputGroup size="sm" className={styles.formItemGroup}>
                          <Button className="secondary-btn" onClick={onFocus}>
                            <BsCalendarFill className="light-text" />
                          </Button>
                          <Form.Control
                            type="text"
                            placeholder={t("create_trip.last_date")}
                            size="sm"
                            onFocus={onFocus}
                            value={showLastDate ? value || "" : ""}
                            onChange={onChange}
                            disabled={!showLastDate}
                          />
                        </InputGroup>
                      )}
                    />
                  )}
                />
                <FormError
                  error={tFormError(errors.last_date)}
                  className={styles.formItemError}
                />
              </div>
            </div>
          </Collapse>
        </div>
      </div>
      <div
        className={
          styles[`verticalDottedLine${showLastDate ? "Extended" : ""}`]
        }
      ></div>
      <div className={styles.formGroup} id="destination">
        <div className={styles.formHeader}>
          <BsGeoAltFill className="h2 secondary-text" />
          <h2 className="secondary-text">{t("create_trip.destination")}</h2>
        </div>
        <div className={styles.formContainer}>
          <div className={styles.formRow}>
            <div className={styles.formItem}>
              <Controller
                name="destination_city"
                control={control}
                defaultValue={citySelectorDefaultValue}
                render={({ field: { onChange, value } }) => (
                  <CitySelector
                    cities={cities}
                    defaultOption={t("create_trip.district")}
                    onChange={(event) => onChange(parseInt(event.target.value))}
                    size="sm"
                    value={value}
                  />
                )}
              />
              <FormError
                error={tFormError(errors.destination_city)}
                className={styles.formItemError}
              />
            </div>
            <div className={styles.formItem}>
              <Form.Control
                type="text"
                placeholder={t("create_trip.address")}
                size="sm"
                {...register("destination_address")}
              />
              <FormError
                error={tFormError(errors.destination_address)}
                className={styles.formItemError}
              />
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
                <Controller
                  name="car"
                  control={control}
                  defaultValue={carSelectorDefaultValue}
                  render={({ field: { onChange, value } }) => (
                    <CarSelector
                      cars={cars}
                      defaultOption={t("create_trip.select_car")}
                      onChange={(event) => {
                        const carId = parseInt(event.target.value);
                        onChange(carId);
                        const car = cars.find((car) => car.carId === carId);
                        setCar(car);
                        if (car) {
                          setCar(car);
                          setCarSeats(car.seats);
                        }
                      }}
                      value={value}
                    />
                  )}
                />
              </InputGroup>
              <Collapse in={showCarInfo}>
                <div>
                  <CarInfoCard car={car} />
                </div>
              </Collapse>
              <FormError
                error={tFormError(errors.car)}
                className={styles.formItemError}
              />
            </div>
            <Collapse in={showCarInfo} dimension="width">
              <div className={styles.formItem}>
                <CarImage car={car} className={styles.carImage} rounded />
              </div>
            </Collapse>
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
                  {...register("seats", {
                    setValueAs: parseInputInteger,
                  })}
                />
              </InputGroup>
              <FormError
                error={tFormError(errors.seats)}
                className={styles.formItemError}
              />
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
                  {...register("price", {
                    setValueAs: parseInputFloat,
                  })}
                />
                <InputGroup.Text>ARS</InputGroup.Text>
              </InputGroup>
              <FormError
                error={tFormError(errors.price)}
                className={styles.formItemError}
              />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.submitContainer}>
        <LoadingButton
          type="submit"
          className="btn secondary-btn"
          isLoading={isSubmitting}
        >
          <BiCheck className="light-text h2" />
          <span className="light-text h3">{t("create_trip.create")}</span>
        </LoadingButton>
      </div>
    </Form>
  );
};

export default CreateTripForm;
