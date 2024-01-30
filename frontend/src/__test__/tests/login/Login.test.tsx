import LoginMock from "@/__test__/mocks/LoginMock";
import { server } from "@/__test__/setup";
import { customRender, screen } from "@/__test__/utils";
import LoginPage from "@/pages/login/LoginPage";

describe("Login", () => {
  it("Should render the login page", () => {
    customRender(<LoginPage />);

    expect(
      screen.getByRole("heading", {
        level: 3,
      })
    ).toHaveTextContent(/log in/i);
  });

  it("Should show form error when submitting empty form", async () => {
    const { user } = customRender(<LoginPage />);

    const loginButton = screen.getByRole("button", { name: /log in/i });
    await user.click(loginButton);

    expect(await screen.findByText(/email is required/i)).toBeVisible();
    expect(await screen.findByText(/password is required/i)).toBeVisible();
  });

  it("Should show form error when submitting invalid email", async () => {
    const { user } = customRender(<LoginPage />);

    const emailInput = screen.getByPlaceholderText(/email/i);
    const passwordInput = screen.getByPlaceholderText(/password/i);
    const loginButton = screen.getByRole("button", { name: /log in/i });

    await user.type(emailInput, "invalid email");
    await user.type(passwordInput, "123456");
    await user.click(loginButton);

    expect(await screen.findByText(/invalid email/i)).toBeVisible();
  });

  it("Should show form error when submitting invalid password", async () => {
    const { user } = customRender(<LoginPage />);

    const emailInput = screen.getByPlaceholderText(/email/i);
    const passwordInput = screen.getByPlaceholderText(/password/i);
    const loginButton = screen.getByRole("button", { name: /log in/i });

    await user.type(emailInput, "a@example.com");
    await user.type(passwordInput, "12");
    await user.click(loginButton);

    expect(
      await screen.findByText(/password must have at least 3/i)
    ).toBeVisible();
  });

  it("Should switch remember me checkbox", async () => {
    const { user } = customRender(<LoginPage />);

    const rememberMeCheckbox = screen.getByRole("checkbox", {
      name: /keep me logged in/i,
    });

    await user.click(rememberMeCheckbox);
    expect(rememberMeCheckbox).toBeChecked();

    await user.click(rememberMeCheckbox);
    expect(rememberMeCheckbox).not.toBeChecked();
  });

  it("When logged in, should redirect to home", async () => {
    server.use(LoginMock.mockLogin());
    const { user } = customRender(<LoginPage />);

    const emailInput = screen.getByPlaceholderText(/email/i);
    const passwordInput = screen.getByPlaceholderText(/password/i);
    const loginButton = screen.getByRole("button", { name: /log in/i });

    await user.type(emailInput, "a@example.com");
    await user.type(passwordInput, "123456");
    await user.click(loginButton);

    expect(await screen.findByText(/ready to ride/i)).toBeVisible();
    expect(await screen.findByText(/successfully logged in/i)).toBeVisible();
  });

  it("When credentials are invalid, should show error", async () => {
    server.use(LoginMock.mockInvalidLogin());
    const { user } = customRender(<LoginPage />);

    const emailInput = screen.getByPlaceholderText(/email/i);
    const passwordInput = screen.getByPlaceholderText(/password/i);
    const loginButton = screen.getByRole("button", { name: /log in/i });

    await user.type(emailInput, "a@example.com");
    await user.type(passwordInput, "123456");
    await user.click(loginButton);

    expect(await screen.findByText(/log in failed/i)).toBeVisible();
    expect(
      await screen.findByText(/the email or password is invalid/i)
    ).toBeVisible();
  });

  it("When user is not verified, should show error", async () => {
    server.use(LoginMock.mockNonVerifiedLogin());
    const { user } = customRender(<LoginPage />);

    const emailInput = screen.getByPlaceholderText(/email/i);
    const passwordInput = screen.getByPlaceholderText(/password/i);
    const loginButton = screen.getByRole("button", { name: /log in/i });

    await user.type(emailInput, "a@example.com");
    await user.type(passwordInput, "123456");
    await user.click(loginButton);

    expect(await screen.findByText(/log in failed/i)).toBeVisible();
    expect(
      await screen.findByText(/your account has not been verified/i)
    ).toBeVisible();
  });
});
