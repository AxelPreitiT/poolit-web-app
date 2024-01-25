import { useEffect, useState } from "react";

const useLastDateCollapse = (
  removeLastDate: () => void,
  {
    initialDate,
    initialIsMultitrip,
  }: {
    initialDate?: Date;
    initialIsMultitrip?: boolean;
  } = {}
) => {
  const [date, setDate] = useState<Date | undefined>(initialDate);
  const [isMultitrip, setIsMultitrip] = useState<boolean>(
    initialIsMultitrip || false
  );
  const [showLastDate, setShowLastDate] = useState<boolean>(
    !!(date && isMultitrip)
  );

  useEffect(() => {
    console.log("date", date);
    console.log("isMultitrip", isMultitrip);
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
    isMultitrip,
  };
};

export default useLastDateCollapse;
