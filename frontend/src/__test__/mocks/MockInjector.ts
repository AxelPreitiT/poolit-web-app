import BaseMock from "./BaseMock";

class MockInjector {
  private static mockClasses: (typeof BaseMock)[] = [];

  public static registerMock(mockClass: typeof BaseMock) {
    this.mockClasses.push(mockClass);
  }

  public static injectMockHandlers() {
    const mockHandlers = this.mockClasses.flatMap((mockClass) =>
      mockClass.injectMockHandlers()
    );
    return mockHandlers;
  }
}

export default MockInjector;
