import setAuthToken from "@/__test__/tests/utils.ts";
import {customRender, screen} from "@/__test__/utils.tsx";
import Navbar from "@/components/utils/Navbar.tsx";
import {server} from "@/__test__/setup.ts";
import UserMock from "@/__test__/mocks/UserMock.ts";
import {expect} from "vitest";

describe("Navbar",() => {

    it("Should not show create trip button", async ()=>{
        customRender(<Navbar/>);

        expect(await screen.queryByText(/Create trip/i)).not.toBeInTheDocument();
    })

    it("Should show the options for a User", async ()=>{
        setAuthToken();
        server.use(UserMock.getByIdPrivateRoleUser(),UserMock.optionsMock())

        customRender(<Navbar/>,{route:"/"});
        // await waitFor(()=>expect.)
        expect(await screen.findByText(/Create trip/i)).toBeVisible();
        expect(await screen.findByText(/Reserved/i)).toBeVisible();
        expect(screen.queryByText(/Created/i)).not.toBeInTheDocument();
    })

    it("Should show the options for a Driver", async ()=>{
        setAuthToken();
        server.use(UserMock.getByIdPrivateRoleDriver(),UserMock.optionsMock())

        customRender(<Navbar/>,{route:"/"});

        expect(await screen.findByText(/Create trip/i)).toBeVisible();
        expect(await screen.findByText(/Reserved/i)).toBeVisible();
        expect(await screen.findByText(/Created/i)).toBeVisible();
        expect(screen.queryByText(/Admin/i)).not.toBeInTheDocument();
        expect(window.location.pathname).toBe("/");
    });

    it("Should show the options for an Admin", async ()=>{
        setAuthToken();
        server.use(UserMock.getByIdPrivateRoleAdmin(),UserMock.optionsMock())

        customRender(<Navbar/>,{route:"/"});
        expect(await screen.findByText(/Reserved/i)).toBeVisible();
        expect(await screen.findByText(/Created/i)).toBeVisible();
        expect(await screen.findByText(/Admin/i)).toBeVisible();
        expect(window.location.pathname).toBe("/");
    });
});
