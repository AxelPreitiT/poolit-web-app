import setAuthToken from "@/__test__/tests/utils.ts";
import {customRender, screen} from "@/__test__/utils.tsx";
import Navbar from "@/components/utils/Navbar.tsx";
import {server} from "@/__test__/setup.ts";
import UserMock from "@/__test__/mocks/UserMock.ts";

describe("Navbar",() => {

    it("Should not show create trip button", async ()=>{
        await customRender(<Navbar/>);

        expect(await screen.queryByText(/Create trip/i)).not.toBeInTheDocument();
    })

    it("Show de created menu", async ()=>{
        setAuthToken();
        server.use(UserMock.getByIdPrivateRoleDriver())
        await customRender(<Navbar/>,{route:"/"});


        expect(await screen.findByText(/Created/i)).toBeVisible();
        expect(window.location.pathname).toBe("/");
    });
});
