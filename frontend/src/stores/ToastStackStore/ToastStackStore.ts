import { create } from "zustand";
import ToastType from "./ToastType";

interface ToastState {
  message: string; // Message to display in the toast
  title?: string; // Title to display in the toast
  type: ToastType; // Type of toast (error)
  timeout?: number; // Time in ms to display the toast
  show: boolean; // Whether or not to show the toast
  id: number; // Unique ID for the toast (timestamp)
}

interface ToastStackState {
  toastStack: Record<number, ToastState>; // Object of toast states, keyed by ID
  addToast: ({
    title,
    message,
    type,
    timeout,
  }: {
    title?: string;
    message: string;
    type: ToastType;
    timeout?: number;
  }) => void; // Add a toast to the stack
  closeToast: (id: number) => void; // Close a toast (hide it)
  removeToast: (id: number) => void; // Remove a toast from the stack
}

const useToastStackStore = create<ToastStackState>((set) => ({
  toastStack: {},

  addToast: ({ title, message, type, timeout }) =>
    set((state) => {
      const now = Date.now();
      return {
        toastStack: {
          ...state.toastStack,
          [now]: {
            title,
            message,
            type,
            timeout,
            show: true,
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
