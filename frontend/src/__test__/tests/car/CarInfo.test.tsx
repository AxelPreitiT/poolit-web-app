import CarBrandMock from "@/__test__/mocks/CarBrandMock";
import CarFeatureMock from "@/__test__/mocks/CarFeatureMock";
import CarMock from "@/__test__/mocks/CarMock";
import { customRender, screen } from "@/__test__/utils";
import CarInfo from "@/pages/car/CarInfo";
import { UserEvent } from "@testing-library/user-event";
import { setAuthToken } from "../utils";
import { server } from "@/__test__/setup";
import UserMock from "@/__test__/mocks/UserMock";
import CarModel from "@/models/CarModel";
import CarBrandModel from "@/models/CarBrandModel";

describe("CarInfo", () => {
  let user: UserEvent;
  let rerender: ReturnType<typeof customRender>["rerender"];
  const testCar = CarMock.CARS[0];

  const readonlyProps = {
    car: CarMock.getCarByIdProp(testCar.carId),
    userCars: CarMock.CARS,
    carBrand: CarBrandMock.getCarBrandByUriProp(testCar.brandUri),
    carFeatures: testCar.featuresUri.map((uri) =>
      CarFeatureMock.getCarFeatureByUriProp(uri)
    ),
    allCarFeatures: CarFeatureMock.CAR_FEATURES,
  };

  let props: typeof readonlyProps;

  window.URL.createObjectURL = vi.fn();

  beforeEach(() => {
    setAuthToken();
    server.use(UserMock.getByIdPrivateRoleDriver(), CarMock.getCar());
    props = {
      ...readonlyProps,
    };
    const render = customRender(<CarInfo {...props} />);
    user = render.user;
    rerender = render.rerender;
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });

  const rerenderWithProps = (props: typeof readonlyProps) => {
    rerender(<CarInfo {...props} />);
  };

  const expectViewMode = async (car: CarModel, carBrand: CarBrandModel) => {
    expect(await screen.findByText(car.infoCar)).toBeInTheDocument();
    expect(screen.getByText(carBrand.name)).toBeInTheDocument();
    expect(screen.getByText(car.seats.toString())).toBeInTheDocument();
    expect(screen.getByText(car.plate)).toBeInTheDocument();
    car.featuresUri.forEach(async (featureUri) => {
      expect(
        await screen.findByText(
          CarFeatureMock.getCarFeatureByUriProp(featureUri).id
        )
      ).toBeInTheDocument();
    });
    expect(screen.getByRole("img")).toHaveAttribute("src", car.imageUri);

    expect(screen.getByRole("button", { name: /edit/i })).toBeInTheDocument();
  };

  const expectEditMode = async () => {
    expect(
      await screen.findByPlaceholderText(/description/i)
    ).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/seats/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/image input/i)).toBeInTheDocument();
    CarFeatureMock.CAR_FEATURES.forEach((feature) => {
      expect(screen.getByText(feature.name)).toBeInTheDocument();
    });

    expect(screen.getByRole("button", { name: /cancel/i })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /save/i })).toBeInTheDocument();
  };

  it("Should render", async () => {
    await expectViewMode(props.car, props.carBrand);
  });

  it("If edit button is clicked, it should render the form", async () => {
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();
  });

  it("If cancel button is clicked, it should render the profile info", async () => {
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    user.click(screen.getByRole("button", { name: /cancel/i }));

    await expectViewMode(props.car, props.carBrand);
  });

  it("If save button is clicked, it should render the profile info", async () => {
    server.use(CarMock.updateCarSuccess());
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    user.click(screen.getByRole("button", { name: /save/i }));

    await expectViewMode(props.car, props.carBrand);
  });

  it("If save button is clicked and the request fails, it should render the form", async () => {
    server.use(CarMock.updateCarFail());
    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    user.click(screen.getByRole("button", { name: /save/i }));

    expect(await screen.findByText(/car update failed/i)).toBeInTheDocument();
    await expectEditMode();
  });

  it("If the car features change, it should render the new features", async () => {
    server.use(CarMock.updateCarSuccess());

    const newCarFeatures = CarFeatureMock.CAR_FEATURES.map((feature, index) => {
      if (index % 2 === 0 && props.car.featuresUri.includes(feature.selfUri)) {
        return feature;
      }
      if (index % 2 === 1 && !props.car.featuresUri.includes(feature.selfUri)) {
        return feature;
      }
      return null;
    })
      .filter((feature) => feature !== null)
      .map((feature) => feature!);
    const newCar = {
      ...props.car,
      featuresUri: newCarFeatures.map((feature) => feature.selfUri),
    };

    await expectViewMode(props.car, props.carBrand);

    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    newCarFeatures.forEach((feature, index) => {
      for (let i = 0; i < index; i++) {
        user.click(screen.getByText(feature.name));
      }
    });

    user.click(screen.getByRole("button", { name: /save/i }));

    await expectViewMode(newCar, props.carBrand);

    rerenderWithProps({
      ...props,
      car: newCar,
      carFeatures: newCarFeatures,
    });

    await expectViewMode(newCar, props.carBrand);
    expect(window.URL.createObjectURL).not.toHaveBeenCalled();
  });

  it("If the car is updated, it should render the new info", async () => {
    const imageFile = new File([""], "image.png", { type: "image/png" });
    const newCar = {
      ...props.car,
      infoCar: "new info",
      seats: 5,
    };

    server.use(CarMock.updateCarSuccess());

    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    const descriptionInput = screen.getByPlaceholderText(/description/i);
    const seatsInput = screen.getByPlaceholderText(/seats/i);
    const imageInput = screen.getByPlaceholderText(/image input/i);

    user.type(descriptionInput, newCar.infoCar);
    user.type(seatsInput, newCar.seats.toString());
    user.upload(imageInput, imageFile);

    user.click(screen.getByRole("button", { name: /save/i }));

    rerenderWithProps({
      ...props,
      car: newCar,
    });

    await expectViewMode(newCar, props.carBrand);
    expect(window.URL.createObjectURL).toHaveBeenCalledWith(imageFile);
  });

  it("If the car is updated, but not its image, it should not call createObjectURL", async () => {
    const newCar = {
      ...props.car,
      infoCar: "new info",
      seats: 5,
    };

    server.use(CarMock.updateCarSuccess());

    user.click(screen.getByRole("button", { name: /edit/i }));

    await expectEditMode();

    const descriptionInput = screen.getByPlaceholderText(/description/i);
    const seatsInput = screen.getByPlaceholderText(/seats/i);

    user.type(descriptionInput, newCar.infoCar);
    user.type(seatsInput, newCar.seats.toString());

    user.click(screen.getByRole("button", { name: /save/i }));

    rerenderWithProps({
      ...props,
      car: newCar,
    });
    await expectViewMode(newCar, props.carBrand);
    expect(window.URL.createObjectURL).not.toHaveBeenCalled();
  });
});
