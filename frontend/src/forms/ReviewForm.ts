import Form, { InferedFormSchemaType } from "./Form";
import OptionFormField from "./fields/OptionFormField";
import RatingFormField from "./fields/RatingFormField";
import ReviewCommentFormField from "./fields/ReviewCommentFormField";

const ratingFieldName = "rating";
const optionFieldName = "option";
const commentFieldName = "comment";

const ReviewFormFields = {
  [ratingFieldName]: RatingFormField,
  [optionFieldName]: OptionFormField,
  [commentFieldName]: ReviewCommentFormField,
};

export type ReviewFormFieldsType = typeof ReviewFormFields;

export const ReviewForm = new Form<ReviewFormFieldsType>(ReviewFormFields);

export type ReviewFormSchemaType = InferedFormSchemaType<ReviewFormFieldsType>;

export const ReviewFormSchema = ReviewForm.getSchema();
