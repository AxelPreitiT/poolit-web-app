import { Helmet } from "react-helmet-async";
import { useTranslation } from "react-i18next";

interface RouterComponentProps {
  children: React.ReactNode;
  title?: string;
}

// This component is used to set the title of the page
const RouteWithTitle = ({ children, title }: RouterComponentProps) => {
  const { t } = useTranslation();

  return (
    <>
      <Helmet>
        <title>
          {title ? `${t(title)} | ` : ""}
          {t("poolit.name")}
        </title>
      </Helmet>
      {children}
    </>
  );
};

export default RouteWithTitle;
