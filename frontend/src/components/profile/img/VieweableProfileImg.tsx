import { Image } from "react-bootstrap";
import ProfileImg from "./ProfileImg";

interface ViewableProfileImgProps {
  src: string;
}

const ViewableProfileImg = ({ src }: ViewableProfileImgProps) => (
  <ProfileImg>
    <Image src={src} />
  </ProfileImg>
);

export default ViewableProfileImg;
