import ImageInput, {
  ImageInputProps,
} from "@/components/forms/ImageInput/ImageInput";
import ProfileImg from "./ProfileImg";

const EditableProfileImg = (props: ImageInputProps) => (
  <ProfileImg>
    <ImageInput {...props} />
  </ProfileImg>
);

export default EditableProfileImg;
