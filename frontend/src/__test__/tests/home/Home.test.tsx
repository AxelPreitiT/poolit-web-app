import {describe, expect} from "vitest";
import {customRender} from "@/__test__/utils.tsx";
import HomePage from "@/pages/home/HomePage.tsx";


const dummyJWT = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqbWVudGFzdGklMkJ0ZXN0YXBpJTQwaXRiYS5lZHUuYXIiLCJ1c2VyVXJsIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3Bhdy0yMDIzYS0wNy9hcGkvdXNlcnMvNTAiLCJpYXQiOjE3MDY5Nzc5MzAsImV4cCI6NDAwNjk5MjMzMH0.ec1D5KVO7GbXKjU5yoA9XMeIXy9lmk2DSBAuMIgw-kNa6vgIJMNJCFpmaGe_XWVTNw_zAEitJooSYxHxj4Uw8w";

describe("Home",()=>{
    it("Should show de tutorial page",()=>{
        localStorage.setItem("authToken",dummyJWT);
        customRender(<HomePage/>, {route:"/"})
        expect(localStorage.getItem("authToken")).toBe(dummyJWT)
        expect(window.location.pathname).toBe("/");
    });
});
