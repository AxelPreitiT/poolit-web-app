import AuthService from "@/services/AuthService";

const useAuthentication = () => {
  return AuthService.isAuthenticated();
};

export default useAuthentication;
