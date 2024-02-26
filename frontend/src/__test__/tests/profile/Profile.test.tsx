import { setAuthToken } from "@/__test__/tests/utils.ts";
import { customRender, screen } from "@/__test__/utils.tsx";
import { server } from "@/__test__/setup.ts";
import UserMock, { profileListInfo } from "@/__test__/mocks/UserMock.ts";
import { expect } from "vitest";
import CityMock from "@/__test__/mocks/CityMock";
import ProfilePage from "@/pages/profile/ProfilePage";
import CarMock from "@/__test__/mocks/CarMock";
import TripMock from "@/__test__/mocks/TripMock";
import ReviewsMock from "@/__test__/mocks/ReviewsMock.ts";
import PublicProfileInfo from "@/pages/publicProfile/PublicInfoProfile.tsx";
import ListProfileContainer from "@/components/profile/list/ListProfileContainer.tsx";
import ShortReviewProfile from "@/components/review/shorts/ShortReviewProfile.tsx";
import {
  publicsDriverReviewsPath,
  publicsPassangerReviewsPath,
} from "@/AppRouter.tsx";

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

    expect(await screen.findByText(/My cars/i)).toBeVisible();
  });

  it("Should show the public profile info", async () => {
    customRender(<PublicProfileInfo user={UserMock.PUBLIC_USER} />, {
      route: "/profile/5",
    });
    expect(
      await screen.findByText(
        UserMock.PUBLIC_USER.username + " " + UserMock.PUBLIC_USER.surname
      )
    ).toBeVisible();
    expect(
      await screen.findByText(UserMock.PUBLIC_USER.tripCount)
    ).toBeVisible();
  });

  it("Should show the empty format", async () => {
    customRender(
      <ListProfileContainer
        title={profileListInfo.title}
        btn_footer_text={profileListInfo.btn_footer_text}
        empty_text={profileListInfo.empty_text}
        empty_icon={profileListInfo.empty_icon}
        data={[]}
        component_name={ShortReviewProfile}
        link={publicsDriverReviewsPath.replace(
          ":id",
          String(profileListInfo.id)
        )}
      />
    );
    expect(await screen.findByText(profileListInfo.empty_text)).toBeVisible();
    expect(
      await screen.queryByText(profileListInfo.btn_footer_text)
    ).not.toBeInTheDocument();
  });

  it("Should show the fill format", async () => {
    const { user } = customRender(
      <ListProfileContainer
        title={profileListInfo.title}
        btn_footer_text={profileListInfo.btn_footer_text}
        empty_text={profileListInfo.empty_text}
        empty_icon={profileListInfo.empty_icon}
        data={ReviewsMock.REVIEWS}
        component_name={ShortReviewProfile}
        link={publicsPassangerReviewsPath.replace(
          ":id",
          profileListInfo.id.toString()
        )}
      />
    );
    const listButton = screen.getByRole("link", {
      name: profileListInfo.btn_footer_text,
    });

    expect(
      await screen.queryByText(profileListInfo.empty_text)
    ).not.toBeInTheDocument();
    expect(
      await screen.findByText(profileListInfo.btn_footer_text)
    ).toBeVisible();

    await user.click(listButton);

    expect(window.location.pathname).toBe(
      publicsPassangerReviewsPath.replace(":id", profileListInfo.id.toString())
    );
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
