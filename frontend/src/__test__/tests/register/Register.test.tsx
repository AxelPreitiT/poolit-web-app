import { loginPath, registerPath } from "@/AppRouter";
import CityMock from "@/__test__/mocks/CityMock";
import UserMock from "@/__test__/mocks/UserMock";
import { server } from "@/__test__/setup";
import { customRender, screen } from "@/__test__/utils";
import RegisterPage from "@/pages/register/RegisterPage";
import { UserEvent } from "@testing-library/user-event";

describe("Register", () => {
  let user: UserEvent;

  beforeEach(() => {
    server.use(CityMock.getAllCities());
    user = customRender(<RegisterPage />, { route: registerPath }).user;
  });

  const awaitPageToLoad = async () => {
    expect(
      await screen.findByRole("heading", {
        level: 4,
        name: /personal information/i,
      })
    ).toBeVisible();
  };

  const completeForm = async () => {
    const nameInput = screen.getByPlaceholderText(/^name$/i);
    const lastNameInput = screen.getByPlaceholderText(/last name/i);
    const emailInput = screen.getByPlaceholderText(/email/i);
    const telephoneInput = screen.getByPlaceholderText(/telephone/i);
    const passwordInput = screen.getByPlaceholderText(/^password$/i);
    const confirmPasswordInput =
      screen.getByPlaceholderText(/confirm password/i);
    const citySelect = screen.getByPlaceholderText(/residence/i);
    const localeSelect = screen.getByPlaceholderText(/language/i);

    await user.type(nameInput, "John");
    await user.type(lastNameInput, "Doe");
    await user.type(emailInput, "johndoe@example.com");
    await user.type(telephoneInput, "1234567890");
    await user.type(passwordInput, "123456");
    await user.type(confirmPasswordInput, "123456");
    await user.selectOptions(citySelect, "1");
    await user.selectOptions(localeSelect, "en");
  };

  it("Should render the register page", async () => {
    await awaitPageToLoad();
    expect(screen.getByRole("button", { name: /sign up/i })).toBeVisible();
    expect(window.location.pathname).toBe(registerPath);
  });

  it("Should show form error when submitting empty form", async () => {
    await awaitPageToLoad();
    const containers = await screen.queryAllByRole("generic");
    containers.forEach((container) => {
      expect(container).not.toContainElement(
        screen.queryByText(/name is required/i)
      );
    });

    const signUpButton = await screen.findByRole("button", {
      name: /sign up/i,
    });
    await user.click(signUpButton);

    expect(await screen.findByText(/the name is required/i)).toBeVisible();
    expect(screen.getByText(/last name is required/i)).toBeVisible();
    expect(screen.getByText(/email is required/i)).toBeVisible();
    expect(screen.getByText(/password is required/i)).toBeVisible();
    expect(screen.getByText(/telephone is required/i)).toBeVisible();
    expect(screen.getByText(/select a city/i)).toBeVisible();
  });

  it("If the email is invalid, it should show an error", async () => {
    await awaitPageToLoad();

    const emailInput = screen.getByPlaceholderText(/email/i);
    const signUpButton = screen.getByRole("button", { name: /sign up/i });

    await user.type(emailInput, "invalidemail");
    await user.click(signUpButton);

    expect(await screen.findByText(/invalid email/i)).toBeVisible();
  });

  it("If the telephone is invalid, it should show an error", async () => {
    await awaitPageToLoad();

    const telephoneInput = screen.getByPlaceholderText(/telephone/i);
    const signUpButton = screen.getByRole("button", { name: /sign up/i });

    await user.type(telephoneInput, "123");
    await user.click(signUpButton);

    expect(await screen.findByText(/invalid telephone number/i)).toBeVisible();
  });

  it("If the confirm password is different, it should show an error", async () => {
    await awaitPageToLoad();

    const passwordInput = screen.getByPlaceholderText(/^password$/i);
    const confirmPasswordInput =
      screen.getByPlaceholderText(/confirm password/i);
    const signUpButton = screen.getByRole("button", { name: /sign up/i });

    await user.type(passwordInput, "123456");
    await user.type(confirmPasswordInput, "1234567");
    await user.click(signUpButton);

    expect(await screen.findByText(/passwords do not match/i)).toBeVisible();
  });

  it("If the form is valid, it should register the user", async () => {
    server.use(UserMock.createUserSuccess());
    await awaitPageToLoad();

    await completeForm();

    const signUpButton = screen.getByRole("button", { name: /sign up/i });
    await user.click(signUpButton);

    expect(await screen.findByText(/welcome to poolit/i)).toBeVisible();
    expect(screen.getByText(/check your email inbox to verify/i)).toBeVisible();
    expect(window.location.pathname).toBe(loginPath);
  });

  it("If the email is already in use, it should show an error", async () => {
    server.use(UserMock.createUserEmailAlreadyExists());
    await awaitPageToLoad();

    await completeForm();

    const signUpButton = screen.getByRole("button", { name: /sign up/i });
    await user.click(signUpButton);

    expect(await screen.findByText(/sign up failed/i)).toBeVisible();
    expect(screen.getByText(/email is already registered/i)).toBeVisible();
    expect(window.location.pathname).toBe(registerPath);
  });

  it("If user clicks on the login link, it should redirect to the login page", async () => {
    await awaitPageToLoad();

    const loginLink = screen.getByRole("link", { name: /log in/i });
    await user.click(loginLink);

    expect(window.location.pathname).toBe(loginPath);
  });
});
