interface ReviewModel {
  comment: string;
  id: number;
  option: string;
  rating: number;
  reviewedUserUri: string;
  reviewDateTime: string;
  selfUri: string;
  tripUri: string;
}

export default ReviewModel;
