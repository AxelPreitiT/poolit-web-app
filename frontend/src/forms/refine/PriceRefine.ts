const priceRefine =
  <T extends object>(minField: keyof T, maxField: keyof T) =>
  (data: T) => {
    const minPrice = data[minField] as number | undefined;
    const maxPrice = data[maxField] as number | undefined;
    return (
      minPrice === undefined || maxPrice === undefined || minPrice <= maxPrice
    );
  };

export default priceRefine;
