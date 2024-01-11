import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { ToastContainer } from "react-bootstrap";
import Toast from "./Toast";

const GlobalToastStack = () => {
  const { toastStack, closeToast, removeToast } = useToastStackStore();
  const toasts = Object.values(toastStack);

  return (
    <ToastContainer position="bottom-end" className="mb-2 me-2">
      {toasts.map((toast) => {
        return (
          <Toast
            title={toast.title}
            message={toast.message}
            timeout={toast.timeout}
            key={toast.id}
            show={toast.show}
            type={toast.type}
            onClose={() => {
              closeToast(toast.id);
              setTimeout(() => removeToast(toast.id), 500);
            }}
          />
        );
      })}
    </ToastContainer>
  );
};

export default GlobalToastStack;
