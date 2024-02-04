import BaseMock from "@/__test__/mocks/BaseMock.ts";
import ReviewModel from "@/models/ReviewModel.ts";

class ReviewsMock extends BaseMock {
  public static readonly REVIEWS: ReviewModel[] = [
    {
      comment: "was really really disappointed",
      id: 31,
      option: "DISRESPECTFUL",
      rating: 1,
      reviewDateTime: "2023-06-26T19:53:44.472",
      reviewedUserUri: this.getPath("/users/11"),
      selfUri: this.getPath("/passenger-reviews/31"),
      tripUri: this.getPath("/trips/38"),
    },
    {
      comment: "was really really disappointed",
      id: 31,
      option: "DISRESPECTFUL",
      rating: 1,
      reviewDateTime: "2023-06-26T19:53:44.472",
      reviewedUserUri: this.getPath("/users/11"),
      selfUri: this.getPath("/passenger-reviews/31"),
      tripUri: this.getPath("/trips/38"),
    },
  ];

  public static getReviewsPassanger() {
    return this.get("/driver-reviews", () =>
      this.jsonResponse(this.REVIEWS, { status: this.OK_STATUS })
    );
  }
  public static getReviewsDriver() {
    return this.get("/passenger-reviews", () =>
      this.jsonResponse(this.REVIEWS, { status: this.OK_STATUS })
    );
  }
}

export default ReviewsMock;
