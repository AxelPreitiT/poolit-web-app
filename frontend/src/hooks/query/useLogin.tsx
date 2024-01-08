import UserPublicModel from "@/models/UserPublicModel";
import UserService from "@/services/UserService";
// import useToastStackStore from "@/stores/ToastStackStore/ToastStackStore";
import { DefaultError, useMutation } from "@tanstack/react-query";
// import { useLocation, useNavigate } from "react-router";

const useLogin = () => {
  // const location = useLocation();
  // const navigate = useNavigate();
  // const addToast = useToastStackStore((state) => state.addToast);

  // const from = location.state?.from;
  const mutation = useMutation<
    UserPublicModel,
    DefaultError,
    [string, string, boolean]
  >({
    mutationFn: ([email, password, rememberMe]: [string, string, boolean]) =>
      UserService.login(email, password, rememberMe),

    onSuccess: () => {
      console.log("Login success");
    },
    onError: (error) => {
      console.error(error);
    },
  });

  const login = (
    email: string,
    password: string,
    rememberMe: boolean = false
  ) => {
    mutation.mutate([email, password, rememberMe]);
  };

  return login;
};

export default useLogin;
