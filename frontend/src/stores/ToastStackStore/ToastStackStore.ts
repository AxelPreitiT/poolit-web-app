import ToastType from "@/enums/ToastType";
import { create } from "zustand";

export interface ToastState {
  message: string; // Message to display in the toast
  title?: string; // Title to display in the toast
  type: ToastType; // Type of toast (error)
  timeout?: number; // Time in ms to display the toast
  show: boolean; // Whether or not to show the toast
  children?: JSX.Element; // Children to display in the toast body
}

type ToastInstance = ToastState & {
  id: number;
};

interface ToastStackState {
  toastStack: Record<number, ToastInstance>; // Object of toast states, keyed by ID
  addToast: ({
    title,
    message,
    type,
    timeout,
    children,
  }: {
    title?: string;
    message: string;
    type: ToastType;
    timeout?: number;
    children?: JSX.Element;
  }) => void; // Add a toast to the stack
  closeToast: (id: number) => void; // Close a toast (hide it)
  removeToast: (id: number) => void; // Remove a toast from the stack
}

let __toastStackTimeout: number | undefined = undefined;
export const __setToastStackTimeout = (timeout: number) => {
  __toastStackTimeout = timeout;
};

const useToastStackStore = create<ToastStackState>((set) => ({
  toastStack: {},

  addToast: ({ title, message, type, timeout, children }) =>
    set((state) => {
      const now = Date.now();
      return {
        toastStack: {
          ...state.toastStack,
          [now]: {
            title,
            message,
            type,
            timeout: __toastStackTimeout || timeout,
            show: true,
            children,
            id: now,
          },
        },
      };
    }),

  closeToast: (id: number) =>
    set((state) => {
      if (state.toastStack[id]) {
        return {
          toastStack: {
            ...state.toastStack,
            [id]: {
              ...state.toastStack[id],
              show: false,
            },
          },
        };
      }
      return state;
    }),

  removeToast: (id: number) =>
    set((state) => {
      if (state.toastStack[id]) {
        const newToastStack = { ...state.toastStack };
        delete newToastStack[id];
        return { toastStack: newToastStack };
      }
      return state;
    }),
}));

export default useToastStackStore;
