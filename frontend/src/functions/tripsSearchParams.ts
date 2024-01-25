import { SearchTripsFormSchemaType } from "@/forms/SearchTripsForm";
import { getIsoDate, parseIsoDate } from "@/utils/date/isoDate";

const originCityKey = "originCity";
const destinationCityKey = "destinationCity";
const dateKey = "date";
const timeKey = "time";
const lastDateKey = "lastDate";
const minPriceKey = "minPrice";
const maxPriceKey = "maxPrice";
const carFeatureKey = "carFeature";

export const createTripsSearchParams = (search: SearchTripsFormSchemaType) => {
  const searchParams = new URLSearchParams({
    [originCityKey]: search.origin_city.toString(),
    [destinationCityKey]: search.destination_city.toString(),
    [dateKey]: getIsoDate(search.date),
    [timeKey]: search.time,
  });
  if (search.multitrip && search.last_date) {
    searchParams.append(lastDateKey, getIsoDate(search.last_date));
  }
  if (search.min_price) {
    searchParams.append(minPriceKey, search.min_price.toString());
  }
  if (search.max_price) {
    searchParams.append(maxPriceKey, search.max_price.toString());
  }
  if (search.car_features) {
    search.car_features.forEach((feature) => {
      searchParams.append(carFeatureKey, feature);
    });
  }
  return searchParams.toString();
};

export const parseTripsSearchParams: (
  search: string
) => Partial<SearchTripsFormSchemaType> = (search: string) => {
  const params = new URLSearchParams(search);
  const originCity = params.get(originCityKey) || undefined;
  const destinationCity = params.get(destinationCityKey) || undefined;
  const date = params.get(dateKey) || undefined;
  const time = params.get(timeKey) || undefined;
  const lastDate = params.get(lastDateKey) || undefined;
  const minPrice = params.get(minPriceKey) || undefined;
  const maxPrice = params.get(maxPriceKey) || undefined;
  const carFeatures = params.getAll(carFeatureKey);
  return {
    origin_city: originCity ? parseInt(originCity) : undefined,
    destination_city: destinationCity ? parseInt(destinationCity) : undefined,
    date: date ? parseIsoDate(date) : undefined,
    time: time || undefined,
    last_date: lastDate ? parseIsoDate(lastDate) : undefined,
    min_price: minPrice ? parseInt(minPrice) : undefined,
    max_price: maxPrice ? parseInt(maxPrice) : undefined,
    car_features: carFeatures,
    multitrip: !!lastDate,
  };
};
