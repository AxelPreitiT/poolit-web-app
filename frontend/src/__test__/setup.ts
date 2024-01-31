import "@testing-library/jest-dom";
import { afterAll, afterEach, beforeAll } from "vitest";
import { setupServer } from "msw/node";
import { cleanup } from "@testing-library/react";
import { __setToastStackTimeout } from "@/stores/ToastStackStore/ToastStackStore";

export const server = setupServer();

// Start server before all tests
// onUnhandledRequest: 'error' will throw an error if there is an unhandled request
beforeAll(() => server.listen({ onUnhandledRequest: "error" }));

// Set toast timeout to 10ms for faster tests
beforeAll(() => __setToastStackTimeout(10));

//  Close server after all tests
afterAll(() => server.close());

// Reset handlers after each test `important for test isolation`
afterEach(() => server.resetHandlers());

// Clean DOM after each test
afterEach(() => cleanup());
