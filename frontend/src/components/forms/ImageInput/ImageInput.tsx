import { Form } from "react-bootstrap";
import styles from "./styles.module.scss";
import { useRef } from "react";

export interface ImageInputProps {
  preview?: string;
  previewAlt: string;
  onImageUpload: (image: File) => void;
  placeholder: React.ReactNode;
  className?: string;
}

const ImageInput = ({
  preview,
  previewAlt,
  onImageUpload,
  placeholder,
  className,
}: ImageInputProps) => {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const onClick = () => {
    fileInputRef.current!.click();
  };

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files![0];
    onImageUpload(file);
  };

  return (
    <div
      className={styles.imageInputContainer + " " + className}
      onClick={onClick}
    >
      <Form.Control
        type="file"
        className={styles.inputFile}
        onChange={onChange}
        ref={fileInputRef}
      />
      {preview ? (
        <div className={styles.previewContainer}>
          <img src={preview} alt={previewAlt} className={styles.previewImg} />
          <div className={styles.previewIcon}>
            <i className="bi bi-pencil-fill light-text h3"></i>
          </div>
        </div>
      ) : (
        placeholder
      )}
    </div>
  );
};

export default ImageInput;
