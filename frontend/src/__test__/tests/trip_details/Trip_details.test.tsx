import {describe} from "vitest";
import {customRender, screen} from "@/__test__/utils.tsx";
import TripDetailsPage from "@/pages/trips/TripDetailsPage.tsx";
import {server} from "@/__test__/setup.ts";
import LoginMock from "@/__test__/mocks/LoginMock.ts";


describe("trip_details",()=>{
    it("Should show trip details",async () => {
        server.use(LoginMock.mockLogin());
        customRender(<TripDetailsPage/>, {route: "/trips/:tripId"})
        expect(await screen.findByText(/Trip details/i)).toBeVisible();
    });
});
