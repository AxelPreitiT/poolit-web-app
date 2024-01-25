import CarFeatureModel from "@/models/CarFeatureModel";
import CityModel from "@/models/CityModel";
import styles from "./styles.module.scss";
import { Button, Collapse, Form, Nav } from "react-bootstrap";
import {
  BsArrowLeftRight,
  BsArrowRepeat,
  BsCalendar,
  BsCalendarEventFill,
  BsCarFrontFill,
  BsCurrencyDollar,
  BsGeoAltFill,
} from "react-icons/bs";
import { useCallback, useEffect, useState } from "react";
import CitySelector, {
  citySelectorDefaultValue,
} from "../forms/CitySelector/CitySelector";
import FormError from "../forms/FormError/FormError";
import { BiSearch } from "react-icons/bi";
import CarFeaturesPills from "../car/CarFeaturesPills/CarFeaturesPills";
import { useTranslation } from "react-i18next";
import { getDayString } from "@/utils/date/dayString";
import useSearchTripsForm from "@/hooks/forms/useSearchTripsForm";
import { Controller } from "react-hook-form";
import DatePicker from "../forms/DatePicker/DatePicker";
import { getToday } from "@/utils/date/today";
import TimePicker from "../forms/TimePicker/TimePicker";
import { getNextDay } from "@/utils/date/nextDay";
import { parseInputFloat } from "@/utils/float/parse";
import LoadingButton from "../buttons/LoadingButton";
import useLastDateCollapse from "@/hooks/trips/useLastDateCollapse";

const uniqueTripId = "unique";
const recurrentTripId = "recurrent";

interface TripsSearchProps {
  searchForm: ReturnType<typeof useSearchTripsForm>;
  cities?: CityModel[];
  carFeatures?: CarFeatureModel[];
}

const useCitiesSwap = (
  onSwap: (oldOriginCityId: number, oldDestinationCityId: number) => void
) => {
  const [originCity, setOriginCity] = useState<number>(
    citySelectorDefaultValue
  );
  const [destinationCity, setDestinationCity] = useState<number>(
    citySelectorDefaultValue
  );
  const [canSwap, setCanSwap] = useState<boolean>(false);

  useEffect(() => {
    setCanSwap(
      originCity !== citySelectorDefaultValue ||
        destinationCity !== citySelectorDefaultValue
    );
  }, [originCity, destinationCity]);

  const swapCities = () => {
    const oldOriginCity = originCity;
    const oldDestinationCity = destinationCity;
    setOriginCity(oldDestinationCity);
    setDestinationCity(oldOriginCity);
    onSwap(oldOriginCity, oldDestinationCity);
  };

  return {
    setOriginCity,
    setDestinationCity,
    swapCities,
    canSwap,
  };
};

const TripsSearch = ({
  searchForm,
  cities = [],
  carFeatures = [],
}: TripsSearchProps) => {
  const { t } = useTranslation();
  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    tFormError,
    setValue,
    isFetching,
    getValues,
  } = searchForm;
  const [activeTab, setActiveTab] = useState<string>(
    getValues("multitrip") ? recurrentTripId : uniqueTripId
  );

  const removeLastDate = useCallback(() => {
    setValue("last_date", null);
  }, [setValue]);

  const { setDate, setIsMultitrip, date, isMultitrip } = useLastDateCollapse(
    removeLastDate,
    {
      initialDate: getValues("date"),
      initialIsMultitrip: getValues("multitrip"),
    }
  );
  const minLastDate = date ? getNextDay(date) : getToday();

  const setMultitrip = useCallback(
    (value: boolean) => {
      setValue("multitrip", value);
      setIsMultitrip(value);
    },
    [setValue, setIsMultitrip]
  );

  const setCarFeatures = useCallback(
    (value: string[]) => setValue("car_features", value),
    [setValue]
  );

  const onTabSelect = (eventKey: string | null) => {
    if (eventKey === recurrentTripId) {
      setMultitrip(true);
    } else {
      removeLastDate();
      setMultitrip(false);
    }
    if (eventKey) {
      setActiveTab(eventKey);
    }
  };

  const onSwap = useCallback(
    (oldOriginCityId: number, oldDestinationCityId: number) => {
      setValue("origin_city", oldDestinationCityId);
      setValue("destination_city", oldOriginCityId);
    },
    [setValue]
  );

  const { setOriginCity, setDestinationCity, swapCities, canSwap } =
    useCitiesSwap(onSwap);

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
            <span className="light-text">{t("search_trips.unique_trip")}</span>
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
            <span className="light-text">
              {t("search_trips.recurrent_trip")}
            </span>
          </Nav.Link>
        </Nav.Item>
      </Nav>
      <Form className={styles.searchForm} onSubmit={handleSubmit}>
        <div className={styles.searchFormGroup}>
          <div className={styles.searchFormTitle}>
            <BsGeoAltFill className="light-text" />
            <span className="light-text">{t("search_trips.route")}</span>
          </div>
          <div className={styles.searchFormInput}>
            <div className={styles.searchFormInputItem}>
              <Form.Group className={styles.searchFormRouteInput}>
                <Form.Label className="light-text">
                  {t("search_trips.origin")}
                </Form.Label>
                <Controller
                  name="origin_city"
                  control={control}
                  defaultValue={citySelectorDefaultValue}
                  render={({ field: { value, onChange } }) => (
                    <CitySelector
                      cities={cities}
                      defaultOption={t("search_trips.district")}
                      onChange={(event) => {
                        const cityId = parseInt(event.target.value);
                        onChange(cityId);
                        setOriginCity(cityId);
                      }}
                      value={value}
                      size="sm"
                    />
                  )}
                />
              </Form.Group>
              <FormError error={tFormError(errors.origin_city)} />
            </div>
            <Button
              type="button"
              className="secondary-btn"
              onClick={swapCities}
              disabled={!canSwap}
            >
              <BsArrowLeftRight className="light-text" />
            </Button>
            <div className={styles.searchFormInputItem}>
              <Form.Group className={styles.searchFormRouteInput}>
                <Form.Label className="light-text">
                  {t("search_trips.destination")}
                </Form.Label>
                <Controller
                  name="destination_city"
                  control={control}
                  defaultValue={citySelectorDefaultValue}
                  render={({ field: { value, onChange } }) => (
                    <CitySelector
                      cities={cities}
                      defaultOption={t("search_trips.district")}
                      onChange={(event) => {
                        const cityId = parseInt(event.target.value);
                        onChange(cityId);
                        setDestinationCity(cityId);
                      }}
                      value={value}
                      size="sm"
                    />
                  )}
                />
              </Form.Group>
              <FormError error={tFormError(errors.destination_city)} />
            </div>
          </div>
        </div>
        <div className={styles.searchFormGroup}>
          <div className={styles.searchFormTitle}>
            <BsCalendar className="light-text" />
            <span className="light-text">{t("search_trips.date")}</span>
          </div>
          <div className={styles.searchFormInput}>
            <div className={styles.searchFormInputItem}>
              <Controller
                name="date"
                control={control}
                render={({ field: { value, onChange } }) => (
                  <DatePicker
                    onPick={(date: Date | undefined) => {
                      onChange(date);
                      removeLastDate();
                      setDate(date);
                    }}
                    value={value}
                    placeholder={t("search_trips.date")}
                    inputClass={styles.formItemGroup}
                    minDate={getToday()}
                  />
                )}
              />
              <FormError error={tFormError(errors.date)} />
            </div>
            <div className={styles.searchFormInputItem}>
              <Controller
                name="time"
                control={control}
                defaultValue=""
                render={({ field: { value, onChange } }) => (
                  <TimePicker
                    onPick={onChange}
                    inputClass={styles.formItemGroup}
                    value={value}
                    placeholder={t("search_trips.time")}
                  />
                )}
              />
              <FormError error={tFormError(errors.time)} />
            </div>
          </div>
          <Collapse in={isMultitrip}>
            <div className={styles.searchFormInput}>
              <Collapse in={date && isMultitrip} dimension="width">
                <div className={styles.searchFormInputItem}>
                  <div className={styles.searchFormLastDateDecorator}>
                    {date && (
                      <>
                        <BsArrowRepeat className="light-text" />
                        <div className={styles.searchFormLastDateInputText}>
                          <span className="light-text">
                            {t("search_trips.every")}
                          </span>
                          <strong className="light-text">
                            {t(`day.full.${getDayString(date).toLowerCase()}`, {
                              plural: "s",
                            })}
                          </strong>
                          <span className="light-text">
                            {t("search_trips.until").toLowerCase()}
                          </span>
                        </div>
                      </>
                    )}
                  </div>
                </div>
              </Collapse>
              <div className={styles.searchFormInputItem}>
                <Controller
                  name="last_date"
                  control={control}
                  render={({ field: { value, onChange } }) => (
                    <DatePicker
                      mapDays={({ date: calendarDate }) => ({
                        disabled: calendarDate.weekDay.index !== date?.getDay(),
                      })}
                      onPick={onChange}
                      value={value}
                      placeholder={t("search_trips.last_date")}
                      inputClass={styles.formItemGroup}
                      minDate={minLastDate}
                      disabled={!isMultitrip}
                    />
                  )}
                />
                <FormError error={tFormError(errors.last_date)} />
              </div>
            </div>
          </Collapse>
        </div>
        <div className={styles.searchFormGroup}>
          <div className={styles.searchFormTitle}>
            <BsCurrencyDollar className="light-text" />
            <span className="light-text">
              {t("search_trips.price_per_trip")}
            </span>
          </div>
          <div className={styles.searchFormInput}>
            <div className={styles.searchFormInputItem}>
              <Form.Control
                type="text"
                placeholder={t("search_trips.minimum")}
                size="sm"
                {...register("min_price", {
                  setValueAs: parseInputFloat,
                })}
              />
              <FormError error={tFormError(errors.min_price)} />
            </div>
            <span className="light-text">-</span>
            <div className={styles.searchFormInputItem}>
              <Form.Control
                type="text"
                placeholder={t("search_trips.maximum")}
                size="sm"
                {...register("max_price", {
                  setValueAs: parseInputFloat,
                })}
              />
              <FormError error={tFormError(errors.max_price)} />
            </div>
            <span className="light-text">ARS</span>
          </div>
        </div>
        {carFeatures && carFeatures.length > 0 && (
          <div className={styles.searchFormGroup}>
            <div className={styles.searchFormTitle}>
              <BsCarFrontFill className="light-text" />
              <span className="light-text">
                {t("search_trips.car_features")}
              </span>
            </div>
            <div className={styles.searchFormInput}>
              <CarFeaturesPills
                carFeatures={carFeatures}
                onSelect={setCarFeatures}
                initialSelectedCarFeatures={getValues("car_features")}
              />
            </div>
          </div>
        )}
        <div className={styles.searchFormSubmit}>
          <LoadingButton
            type="submit"
            className="secondary-btn"
            isLoading={isFetching}
          >
            <BiSearch className="light-text" />
            <span className="light-text">{t("search_trips.search")}</span>
          </LoadingButton>
        </div>
      </Form>
    </div>
  );
};

export default TripsSearch;
