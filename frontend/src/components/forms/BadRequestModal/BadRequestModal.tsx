import BadRequestModel from "@/models/BadRequestModel";
import { useState } from "react";
import { Modal } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import styles from "./styles.module.scss";

type BadRequestModalOutput = Record<string, string[]>;

const formatErrors = (errors: BadRequestModel): BadRequestModalOutput => {
  const output: BadRequestModalOutput = {};
  for (const { field, message } of errors) {
    if (output[field] === undefined) {
      output[field] = [];
    }
    output[field].push(message);
  }
  return output;
};

const ModalBody = ({ errors }: { errors: BadRequestModalOutput }) => {
  const capitalize = (str: string) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
  };
  return (
    <div>
      {Object.entries(errors).map(([field, messages]) => (
        <div>
          <h4 className="mb-2">{capitalize(field)}</h4>
          <ul>
            {messages.map((message) => (
              <li>{capitalize(message)}</li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  );
};

const BadRequestModal = ({
  errors,
  className,
}: {
  errors?: BadRequestModel;
  className?: string;
}) => {
  const { t } = useTranslation();
  const [showModal, setShowModal] = useState(false);

  if (!errors) {
    return undefined;
  }

  const output = formatErrors(errors);
  return (
    <div className={className}>
      <a onClick={() => setShowModal(true)} className={styles.clicker}>
        <i className="bi bi-caret-right-fill" />
        <p>{t("bad_request.label")}</p>
      </a>
      <Modal
        show={showModal}
        onHide={() => setShowModal(false)}
        centered
        scrollable
      >
        <Modal.Header closeButton className={styles.modal} closeVariant="white">
          <Modal.Title>{t("bad_request.title")}</Modal.Title>
        </Modal.Header>
        <Modal.Body className={styles.modal + " pb-0"}>
          <ModalBody errors={output} />
        </Modal.Body>
      </Modal>
    </div>
  );
};

export default BadRequestModal;
