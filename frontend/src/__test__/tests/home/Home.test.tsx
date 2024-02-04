import { describe, expect } from "vitest";
import { customRender, screen } from "@/__test__/utils.tsx";
import HomePage from "@/pages/home/HomePage.tsx";
import { setAuthToken } from "@/__test__/tests/utils.ts";
import { server } from "@/__test__/setup.ts";
import UserMock from "@/__test__/mocks/UserMock.ts";
import CityMock from "@/__test__/mocks/CityMock.ts";
import CarFeatureMock from "@/__test__/mocks/CarFeatureMock.ts";
import TripMock from "@/__test__/mocks/TripMock.ts";
import CarMock from "@/__test__/mocks/CarMock";

describe("Home", () => {
  it("Should not show the tutorial page when not logged in ", async () => {
    server.use(CityMock.getAllCities(), CarFeatureMock.getAllFeatures());

    customRender(<HomePage />, { route: "/" });

    expect(await screen.findByText(/Find your trip in CABA/i)).toBeVisible();
    expect(await screen.findByText(/Book your trip/i)).toBeVisible();
    expect(await screen.findByText(/Drive and share/i)).toBeVisible();
  });

  it("Should not show the tutorial page when there are not recommended trips", async () => {
    setAuthToken();
    server.use(
      UserMock.getByIdPrivateRoleDriver(),
      CityMock.getAllCities(),
      CarFeatureMock.getAllFeatures(),
      TripMock.mockEmptyTrips()
    );

    customRender(<HomePage />, { route: "/" });

    expect(await screen.findByText(/Find your trip in CABA/i)).toBeVisible();
    expect(await screen.findByText(/Book your trip/i)).toBeVisible();
    expect(await screen.findByText(/Drive and share/i)).toBeVisible();
  });

  it("Should show the tutorial when there are recommended trips", async () => {
    setAuthToken();
    server.use(
      UserMock.getByIdPrivateRoleDriver(),
      CityMock.getAllCities(),
      CarFeatureMock.getAllFeatures(),
      TripMock.mockTripList(),
      CarMock.getCar(),
      CityMock.getCity()
    );

    customRender(<HomePage />, { route: "/" });

    expect(
      screen.queryByText(/Find your trip in CABA/i)
    ).not.toBeInTheDocument();
    expect(screen.queryByText(/Book your trip/i)).not.toBeInTheDocument();
    expect(screen.queryByText(/Drive and share/i)).not.toBeInTheDocument();
    expect(await screen.findByText(/Available trips/i)).toBeVisible();
  });
});
