import { useSearchParams } from "react-router-dom";
import useVerifyAccount from "@/hooks/auth/useVerifyAccount";
import LoadingScreen from "@/components/loading/LoadingScreen";

const VerifyAccountPage = () => {
  const [searchParams] = useSearchParams();
  const email = searchParams.get("email") || "";
  const token = searchParams.get("token") || "";
  useVerifyAccount(email, token);

  return <LoadingScreen />;
};

export default VerifyAccountPage;
