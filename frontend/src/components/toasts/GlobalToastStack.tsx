import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import ToastType from "@/stores/ToastStackStore/ToastType";
import { ToastContainer } from "react-bootstrap";
import ErrorToast from "./ErrorToast/ErrorToast";
import ToastProps from "./ToastProps";

const toastTypeMap: Record<ToastType, React.FC<ToastProps>> = {
  [ToastType.Error]: ErrorToast,
};

const GlobalToastStack = () => {
  const { toastStack, closeToast, removeToast } = useToastStackStore();
  const toasts = Object.values(toastStack);

  return (
    <ToastContainer position="bottom-end" className="mb-2 me-2">
      {toasts.map((toast) => {
        const ToastComponent = toastTypeMap[toast.type];
        if (ToastComponent) {
          return (
            <ToastComponent
              message={toast.message}
              timeout={toast.timeout}
              key={toast.id}
              show={toast.show}
              onClose={() => {
                closeToast(toast.id);
                setTimeout(() => removeToast(toast.id), 500);
              }}
            />
          );
        }
        removeToast(toast.id);
        return null;
      })}
    </ToastContainer>
  );
};

export default GlobalToastStack;
