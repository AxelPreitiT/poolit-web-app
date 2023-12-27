import Image from "react-bootstrap/Image";
import Col from "react-bootstrap/Col";

interface ProfileImgProps {
  src: string;
  dim: number;
}

const ProfileImg = ({ src, dim }: ProfileImgProps) => (
  <Image src={src} roundedCircle />
);

export default ProfileImg;
