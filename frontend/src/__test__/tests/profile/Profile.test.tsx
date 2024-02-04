import setAuthToken from "@/__test__/tests/utils.ts";
import { customRender, screen } from "@/__test__/utils.tsx";
import { server } from "@/__test__/setup.ts";
import UserMock from "@/__test__/mocks/UserMock.ts";
import { expect } from "vitest";
import CityMock from "@/__test__/mocks/CityMock";
import ProfilePage from "@/pages/profile/ProfilePage";
import CarMock from "@/__test__/mocks/CarMock";
import TripMock from "@/__test__/mocks/TripMock";
import ReviewsMock from "@/__test__/mocks/ReviewsMock.ts";

describe("Profile", () => {
  it("Should show the driver info", async () => {
    setAuthToken();

    server.use(
      UserMock.getByIdPrivateRoleDriver(),
      UserMock.optionsMock(),
      CityMock.getAllCities(),
      CityMock.getCity(),
      CarMock.getCars(),
      TripMock.mockTripList(),
      CarMock.getCar(),
      TripMock.mockPassenger(),
      ReviewsMock.getReviewsPassanger(),
      ReviewsMock.getReviewsDriver()
    );

    customRender(<ProfilePage />, { route: "/profile" });

    expect(await screen.findByText(/Opinions as a driver/i)).toBeVisible();
    expect(await screen.findByText(/My cars/i)).toBeVisible();
  });

  it("Should show the user info", async () => {
    setAuthToken();

    server.use(
        UserMock.getByIdPrivateRoleDriver(),
        UserMock.optionsMock(),
        CityMock.getAllCities(),
        CityMock.getCity(),
        CarMock.getCars(),
        TripMock.mockTripList(),
        CarMock.getCar(),
        TripMock.mockPassenger(),
        ReviewsMock.getReviewsPassanger(),
        ReviewsMock.getReviewsDriver()
    );

    customRender(<ProfilePage />, { route: "/profile" });
    expect(await screen.findByText(/Opinions as a passenger/i)).toBeVisible();
  });
});
