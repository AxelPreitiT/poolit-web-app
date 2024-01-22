import Service from "@/services/Service.ts";
import ReviewsApi from "@/api/ReviewsApi.ts";

class ReviewsService extends Service {

    public static getReviewsAsDriverByUserId = async (selftUri : string): Promise<ReviewModel[]> => {
        const pathArray = new URL(selftUri).pathname.split("/");
        const id = pathArray[pathArray.length - 1];
        const uri = `http://localhost:8080/paw-2023a-07/api/driver-reviews?forUser=${id}`
        return await this.resolveQuery(ReviewsApi.getReviewsByUser(uri));
    };

    public static getReviewsAsPassangerByUserId = async (selftUri : string): Promise<ReviewModel[]> => {
        const pathArray = new URL(selftUri).pathname.split("/");
        const id = pathArray[pathArray.length - 1];
        const uri = `http://localhost:8080/paw-2023a-07/api/passenger-reviews?forUser=${id}`
        return await this.resolveQuery(ReviewsApi.getReviewsByUser(uri));
    };

}

export default ReviewsService;
