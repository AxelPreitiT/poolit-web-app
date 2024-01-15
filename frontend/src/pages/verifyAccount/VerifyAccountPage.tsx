import { useSearchParams } from "react-router-dom";
import useVerifyAccount from "@/hooks/auth/useVerifyAccount";

const VerifyAccountPage = () => {
  const [searchParams] = useSearchParams();
  const email = searchParams.get("email") || "";
  const token = searchParams.get("token") || "";
  useVerifyAccount(email, token);

  // TODO: Add loading component
  return <p>Loading...</p>;
};

export default VerifyAccountPage;
