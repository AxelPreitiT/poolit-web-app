import LoadingButton from "@/components/buttons/LoadingButton";
import { Button, Modal } from "react-bootstrap";
import { useTranslation } from "react-i18next";

interface ModalMakeFooterProps {
  onClose: () => void;
  isFetching: boolean;
  formId?: string;
}

const ModalMakeFooter = ({
  onClose,
  isFetching,
  formId,
}: ModalMakeFooterProps) => {
  const { t } = useTranslation();

  return (
    <Modal.Footer>
      <Button className="primary-btn" onClick={onClose}>
        {t("modal.close")}
      </Button>
      <LoadingButton
        type="submit"
        formId={formId}
        className="secondary-btn"
        isLoading={isFetching}
      >
        {t("modal.submit")}
      </LoadingButton>
    </Modal.Footer>
  );
};

export default ModalMakeFooter;
