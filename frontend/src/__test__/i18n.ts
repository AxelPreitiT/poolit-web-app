import i18nTest from "i18next";
import { initReactI18next } from "react-i18next";
import localeEN from "@/locales/en/translation.en.json";

i18nTest.use(initReactI18next).init({
  lng: "en",
  fallbackLng: "en",
  resources: {
    en: {
      translation: localeEN,
    },
    es: {},
  },
});

export default i18nTest;
