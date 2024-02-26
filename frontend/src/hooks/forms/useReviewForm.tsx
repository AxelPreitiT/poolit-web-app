import {
  ReviewForm,
  ReviewFormSchema,
  ReviewFormSchemaType,
} from "@/forms/ReviewForm";
import useForm, { SubmitHandlerReturnModel } from "./useForm";
import { useEffect, useState } from "react";
import ReviewsOptionModel from "@/models/ReviewsOptionModel";

interface ReviewFormHookProps<R extends ReviewsOptionModel> {
  onSubmit: SubmitHandlerReturnModel<ReviewFormSchemaType, void>;
  useReviewOptions: (rating: number) => {
    isError: boolean;
    isLoading: boolean;
    data: R[] | undefined;
  };
  onSuccess?: () => void;
  onError?: (error: Error) => void;
}

const useReviewForm = <R extends ReviewsOptionModel>({
  onSubmit,
  useReviewOptions,
  onSuccess: onSuccessProp,
  onError: onErrorProp,
}: ReviewFormHookProps<R>) => {
  const [rating, setRating] = useState(3);
  const [selectedOption, setSelectedOption] = useState<string | undefined>(
    undefined
  );
  const {
    isLoading: isReviewOptionsLoading,
    data: reviewOptions,
    isError: isReviewOptionsError,
  } = useReviewOptions(rating);

  const reviewForm = useForm({
    form: ReviewForm,
    formSchema: ReviewFormSchema,
    onSubmit,
    onSuccess: onSuccessProp,
    onError: onErrorProp,
    defaultValues: {
      rating,
    },
  });

  const { setValue } = reviewForm;

  const onRatingChange = (value: number) => {
    setRating(value);
    setSelectedOption(undefined);
    setValue("rating", value);
  };

  useEffect(() => {
    if (reviewOptions && reviewOptions.length > 0 && !selectedOption) {
      setSelectedOption(reviewOptions[0].id);
      setValue("option", reviewOptions[0].id);
    }
  }, [reviewOptions, setValue, selectedOption]);

  return {
    ...reviewForm,
    onRatingChange,
    isReviewOptionsLoading,
    reviewOptions,
    isReviewOptionsError,
  };
};

export default useReviewForm;
