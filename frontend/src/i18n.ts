import i18next from "i18next";
import localeEN from "./locales/en/translation.en.json";
import localeES from "./locales/es/translation.es.json";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";

const resources = {
  es: {
    translation: localeES,
  },
  en: {
    translation: localeEN,
  },
};

i18next
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    resources,
    fallbackLng: "en",
    debug: import.meta.env.DEV,
    detection: {
        order:["querystring","navigator"],
        lookupQuerystring:"lng",
        caches: [],
    },
  });

export default i18next;
