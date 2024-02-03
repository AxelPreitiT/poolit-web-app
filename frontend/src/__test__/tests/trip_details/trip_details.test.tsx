import { describe } from "vitest";
import { customRender, screen } from "@/__test__/utils.tsx";
import TripDetailsPage from "@/pages/trips/TripDetailsPage.tsx";
import { server } from "@/__test__/setup.ts";
import LoginMock from "@/__test__/mocks/LoginMock.ts";
import DiscoveryMock from "@/__test__/mocks/DiscoveryMock";
import CarMock from "@/__test__/mocks/CarMock";
import UserMock from "@/__test__/mocks/UserMock";
import TripMock from "@/__test__/mocks/TripMock";

describe("trip_details", () => {
    it("Should show trip details", async () => {
        server.use(
            LoginMock.mockLogin(),
            UserMock.getByIdPrivateRoleDriver(),
            DiscoveryMock.mockDiscovery(),
            TripMock.mockSingleTrip(),
            CarMock.getCar(),
            UserMock.getByIdPublic()
        );
        customRender(<TripDetailsPage />, { route: "/trips/5" });
        expect(await screen.findByText(/Trip details/i)).toBeVisible();
    });
});
