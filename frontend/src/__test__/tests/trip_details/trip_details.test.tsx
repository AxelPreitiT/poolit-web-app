import { describe } from "vitest";
import { customRender, screen } from "@/__test__/utils.tsx";
import TripDetailsPage from "@/pages/trips/TripDetailsPage.tsx";
import { server } from "@/__test__/setup.ts";
// import LoginMock from "@/__test__/mocks/LoginMock.ts";
// import DiscoveryMock from "@/__test__/mocks/DiscoveryMock";
import CarMock from "@/__test__/mocks/CarMock";
import UserMock from "@/__test__/mocks/UserMock";
import TripMock from "@/__test__/mocks/TripMock";
import setAuthToken from "@/__test__/tests/utils.ts";
import DiscoveryMock from "@/__test__/mocks/DiscoveryMock.ts";
// import {waitFor} from "@testing-library/react";

describe("trip_details", () => {
    it("Should show trip details", async () => {
        setAuthToken();
        server.use(
            DiscoveryMock.mockDiscovery(),
            TripMock.mockSingleTrip(),//LoginMock.mockLogin(),
            UserMock.getByIdPrivateRoleDriver(),
            //DiscoveryMock.mockDiscovery(),
            TripMock.mockOccupiedSeats(),
            CarMock.getCar(),
            UserMock.getByIdPublic(),
            TripMock.mockPassenger()
        );
        customRender(<TripDetailsPage />, { route: "/trips/5?startDateTime=2023-10-10&endDateTime=2023-10-17" });
        expect(await screen.findByText(/Trip details/i)).toBeVisible();
    });
});

