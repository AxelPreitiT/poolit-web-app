import "@testing-library/jest-dom";
import { afterAll, afterEach, beforeAll } from "vitest";
import { setupServer } from "msw/node";
import { __setToastStackTimeout } from "@/stores/ToastStackStore/ToastStackStore";
import DiscoveryMock from "@/__test__/mocks/DiscoveryMock.ts";
import { cleanup } from "@testing-library/react";
import { removeAuthToken } from "./tests/utils";
import { queryClient } from "./utils";

export const server = setupServer(DiscoveryMock.mockDiscovery());

// Start server before all tests
// onUnhandledRequest: 'error' will throw an error if there is an unhandled request
beforeAll(() => server.listen({ onUnhandledRequest: "error" }));

// Set toast timeout to 50ms for faster tests
beforeAll(() => __setToastStackTimeout(50));

//  Close server after all tests
afterAll(() => server.close());

// Reset handlers after each test `important for test isolation`
afterEach(async () => {
  server.resetHandlers();
  cleanup();
  removeAuthToken();
  queryClient.clear();
});

// Clean DOM after each test
// afterEach(() => cleanup());
