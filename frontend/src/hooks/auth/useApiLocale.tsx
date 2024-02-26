import ApiLocale from "@/api/axios/ApiLocale";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";

const useApiLocale = () => {
  const { i18n } = useTranslation();

  useEffect(() => {
    const locale = i18n.language;
    ApiLocale.setLocale(locale);
  }, [i18n.language]);
};

export default useApiLocale;
