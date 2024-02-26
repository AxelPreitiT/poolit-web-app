/// <reference types="vitest" />
/// <reference types="vite/client" />

import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";
import tsconfigPaths from "vite-tsconfig-paths";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd());
  return {
    define: {
      // those two env vars are used by this lib
      // ref: https://github.com/thlorenz/parse-link-header/blob/f380d3f99de4a5411b2d7f8da6069bb7529cbf4a/index.js#L7
      "process.env.PARSE_LINK_HEADER_MAXLEN": JSON.stringify(
        env.PARSE_LINK_HEADER_MAXLEN
      ),
      "process.env.PARSE_LINK_HEADER_THROW_ON_MAXLEN_EXCEEDED": JSON.stringify(
        env.PARSE_LINK_HEADER_THROW_ON_MAXLEN_EXCEEDED
      ),
    },
    plugins: [react(), tsconfigPaths()],
    resolve: {
      alias: {
        "@": "/src",
      },
    },
    base: env.VITE_BASE_PATH,
    test: {
      globals: true,
      environment: "jsdom",
      setupFiles: "./src/__test__/setup.ts",
    },
  };
});
