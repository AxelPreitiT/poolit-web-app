import CityMock from "@/__test__/mocks/CityMock";
import UserMock, { privateUserRoleUser } from "@/__test__/mocks/UserMock";
import { setAuthToken } from "../utils";
import { UserEvent } from "@testing-library/user-event";
import { customRender, screen } from "@/__test__/utils";
import ProfileInfo from "@/pages/profile/ProfileInfo";
import UserPrivateModel from "@/models/UserPrivateModel";
import CityModel from "@/models/CityModel";
import { server } from "@/__test__/setup";

describe("ProfileInfo", () => {
  let user: UserEvent;
  let rerender: ReturnType<typeof customRender>["rerender"];

  const readonlyProps = {
    currentUser: privateUserRoleUser,
    cities: CityMock.CITIES,
    city: CityMock.getCityByUriProp(privateUserRoleUser.cityUri),
  };

  let props: typeof readonlyProps;

  window.URL.createObjectURL = vi.fn();

  beforeEach(() => {
    setAuthToken();
    server.use(UserMock.getByIdPrivateRoleUser());
    props = {
      ...readonlyProps,
    };
    const render = customRender(<ProfileInfo {...props} />);
    user = render.user;
    rerender = render.rerender;
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });

  const rerenderWithProps = (props: typeof readonlyProps) => {
    rerender(<ProfileInfo {...props} />);
  };

  const expectViewMode = async (
    currentUser: UserPrivateModel,
    city: CityModel
  ) => {
    expect(
      await screen.findByText(`${currentUser.username} ${currentUser.surname}`)
    ).toBeInTheDocument();
    expect(screen.getByText(currentUser.email)).toBeInTheDocument();
    expect(screen.getByText(currentUser.phone)).toBeInTheDocument();
    expect(screen.getByText(city.name)).toBeInTheDocument();
    expect(screen.getByText(currentUser.tripCount)).toBeInTheDocument();
    expect(screen.getByRole("img")).toHaveAttribute(
      "src",
      currentUser.imageUri
    );

    expect(screen.getByRole("button", { name: /edit/i })).toBeInTheDocument();
  };

  const expectEditMode = async () => {
    expect(await screen.findByPlaceholderText(/^name$/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/^surname$/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/phone/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/neighborhood/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/language/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/image input/i)).toBeInTheDocument();

    expect(screen.getByRole("button", { name: /cancel/i })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /save/i })).toBeInTheDocument();
  };

  it("Should render", async () => {
    await expectViewMode(props.currentUser, props.city);
  });

  it("If edit button is clicked, it should render the form", async () => {
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();
  });

  it("If cancel button is clicked, it should render the profile info", async () => {
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    user.click(screen.getByRole("button", { name: /cancel/i }));

    await expectViewMode(props.currentUser, props.city);
  });

  it("If save button is clicked, it should render the profile info", async () => {
    server.use(UserMock.updateUserSuccess());
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    user.click(screen.getByRole("button", { name: /save/i }));

    await expectViewMode(props.currentUser, props.city);
  });

  it("If save button is clicked and the request fails, it should render the form", async () => {
    server.use(UserMock.updateUserFail());
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    user.click(screen.getByRole("button", { name: /save/i }));

    expect(
      await screen.findByText(/profile update failed/i)
    ).toBeInTheDocument();
    await expectEditMode();
  });

  it("If the user is updated, it should render the new info", async () => {
    const newCity = CityMock.getCityByIdProp(1);
    const imageFile = new File(["image"], "image.jpg", { type: "image/jpeg" });
    const newUser: UserPrivateModel = {
      ...privateUserRoleUser,
      username: "newUsername",
      surname: "newSurname",
      phone: "newPhone",
    };

    server.use(UserMock.updateUserSuccess(), UserMock.updateUserImage());
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    const nameInput = screen.getByPlaceholderText(/^name$/i);
    const surnameInput = screen.getByPlaceholderText(/^surname$/i);
    const phoneInput = screen.getByPlaceholderText(/phone/i);
    const neighborhoodInput = screen.getByPlaceholderText(/neighborhood/i);
    const imageInput = screen.getByPlaceholderText(/image input/i);

    user.type(nameInput, newUser.username);
    user.type(surnameInput, newUser.surname);
    user.type(phoneInput, newUser.phone);
    user.selectOptions(neighborhoodInput, newCity.id.toString());
    user.upload(imageInput, imageFile);

    user.click(screen.getByRole("button", { name: /save/i }));

    rerenderWithProps({
      ...props,
      currentUser: newUser,
      city: newCity,
    });

    await expectViewMode(newUser, newCity);
    expect(window.URL.createObjectURL).toHaveBeenCalledWith(imageFile);
  });

  it("If the user is updated, but not its image, it should not call createObjectURL", async () => {
    const newUser: UserPrivateModel = {
      ...privateUserRoleUser,
      username: "newUsername",
      surname: "newSurname",
      phone: "newPhone",
    };

    server.use(UserMock.updateUserSuccess());
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    const nameInput = screen.getByPlaceholderText(/^name$/i);
    const surnameInput = screen.getByPlaceholderText(/^surname$/i);
    const phoneInput = screen.getByPlaceholderText(/phone/i);

    user.type(nameInput, newUser.username);
    user.type(surnameInput, newUser.surname);
    user.type(phoneInput, newUser.phone);

    user.click(screen.getByRole("button", { name: /save/i }));

    rerenderWithProps({
      ...props,
      currentUser: newUser,
    });

    expect(window.URL.createObjectURL).not.toHaveBeenCalled();
  });
});
