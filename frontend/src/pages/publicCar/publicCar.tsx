import {useParams} from "react-router-dom";

const PublicCarPage = () => {
    const params = useParams();

    return (
        <div>
            <h1>CARS</h1>
            <h1>{params.id}</h1>
        </div>
    );
};

export default PublicCarPage;
