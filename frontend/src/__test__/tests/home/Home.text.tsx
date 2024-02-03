import {describe} from "vitest";
import {customRender} from "@/__test__/utils.tsx";
import HomePage from "@/pages/home/HomePage.tsx";
import {server} from "@/__test__/setup.ts";


describe("Home",()=>{
    it("Should show de tutorial page",()=>{
        server.use()
        customRender(<HomePage/>, {route:"/"})

    })
})