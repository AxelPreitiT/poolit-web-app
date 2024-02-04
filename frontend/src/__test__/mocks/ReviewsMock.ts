import BaseMock from "@/__test__/mocks/BaseMock.ts";
import reviewModel from "@/models/ReviewModel.ts";

const reviewList: reviewModel[] = [
  {
    "comment":"was really really disappointed",
    "id":31,
    "option":"DISRESPECTFUL",
    "rating":1,
    "reviewDateTime":"2023-06-26T19:53:44.472",
    "reviewedUserUri":"http://localhost:8080/paw-2023a-07/api/users/11",
    "selfUri":"http://localhost:8080/paw-2023a-07/api/passenger-reviews/31",
    "tripUri":"http://localhost:8080/paw-2023a-07/api/trips/38"
  },
  {
    "comment":"was really really disappointed",
    "id":31,
    "option":"DISRESPECTFUL",
    "rating":1,
    "reviewDateTime":"2023-06-26T19:53:44.472",
    "reviewedUserUri":"http://localhost:8080/paw-2023a-07/api/users/11",
    "selfUri":"http://localhost:8080/paw-2023a-07/api/passenger-reviews/31",
    "tripUri":"http://localhost:8080/paw-2023a-07/api/trips/38"
  },
];

class ReviewsMock extends BaseMock {
  public static getReviewsPassanger() {
    return this.get("/driver-reviews", () =>
      this.jsonResponse(reviewList, { status: this.OK_STATUS })
    );
  }
  public static getReviewsDriver() {
    return this.get("/passenger-reviews", () =>
      this.jsonResponse(reviewList, { status: this.OK_STATUS })
    );
  }
}

export default ReviewsMock;
