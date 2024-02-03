import {describe, expect} from "vitest";
import {customRender} from "@/__test__/utils.tsx";
import HomePage from "@/pages/home/HomePage.tsx";
import setAuthToken from "@/__test__/tests/utils.ts";

describe("Home",()=>{
    it("Should show de tutorial page",()=>{
        setAuthToken();
        customRender(<HomePage/>, {route:"/"})
        expect(window.location.pathname).toBe("/");
    });
});
