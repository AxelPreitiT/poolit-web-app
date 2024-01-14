import BadRequestModel from "@/models/BadRequestModel";
import { useState } from "react";
import { Modal } from "react-bootstrap";
import { useTranslation } from "react-i18next";

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
  return (
    <div>
      {Object.entries(errors).map(([field, messages]) => {
        return (
          <div>
            <h4>{field}</h4>
            <ul>
              {messages.map((message) => {
                return <li>{message}</li>;
              })}
            </ul>
          </div>
        );
      })}
    </div>
  );
};

const BadRequestModal = ({ errors }: { errors?: BadRequestModel }) => {
  const { t } = useTranslation();
  const [showModal, setShowModal] = useState(false);

  if (!errors) {
    return undefined;
  }

  const output = formatErrors(errors);
  return (
    <>
      <a>
        <i className="bi bi-caret-right-fill" />
        <p>{t("bad_request.label")}</p>
      </a>
      <Modal
        show={showModal}
        onHide={() => setShowModal(false)}
        centered
        size="sm"
        scrollable
      >
        <Modal.Header closeButton>
          <Modal.Title>{t("bad_request.title")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ModalBody errors={output} />
        </Modal.Body>
      </Modal>
    </>
  );
};

export default BadRequestModal;
