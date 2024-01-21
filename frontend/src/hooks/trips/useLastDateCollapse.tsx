import { useEffect, useState } from "react";

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
    isMultitrip,
  };
};

export default useLastDateCollapse;
