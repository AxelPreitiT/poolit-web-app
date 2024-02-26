interface BadRequestFields {
  field?: string;
  message: string;
}

type BadRequestModel = BadRequestFields[] | BadRequestFields;

export default BadRequestModel;
