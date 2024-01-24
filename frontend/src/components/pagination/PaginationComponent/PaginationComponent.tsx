import styles from "./styles.module.scss";
import PaginationModel from "@/models/PaginationModel.tsx";
import {useState} from "react";
import { useLocation } from 'react-router-dom';
import { createBrowserHistory } from 'history';

interface PaginationComponentProps<T> {
    empty_component: React.ReactNode;
    uri: string
    component_name: React.FC<T>;
    useFuction: (uri:string) => {isLoading: boolean; trips: PaginationModel<T> | undefined; }
}

const PaginationComponent = <T,>({
                                empty_component,
                                uri,
                                component_name,
                                     useFuction
                            }: PaginationComponentProps<T>) => {

    const [uriResult, setUriResult] = useState(uri);
    const location = useLocation();
    const history = createBrowserHistory();
    const { isLoading: isLoadingTrips, trips:pageTrips } = useFuction(uriResult);

    let generateItems = <T,>(data: T[], Component: React.FC<T>) => {
        const items = [];
        for (let i = 0; i < data.length; i++) {
            const item = <Component key={i} {...data[i]} />;
            items.push(
                <div className={styles.travel_info_list} key={i}>
                    {item}
                </div>
            );
        }
        return items;
    };

    const handleNextPage = () => {
        setUriResult(pageTrips?.next as string);
        // Get the current URL search parameters
        const searchParams = new URLSearchParams(location.search);

        // Add or update the query parameters as needed
        searchParams.set('page', '2'); // For example, adding a 'page' parameter with value '2'

        // Create a new URL with the updated search parameters
        const newUrl = `${location.pathname}?${searchParams.toString()}`;

        // Push the new URL to the history to navigate to the next page
        history.push(newUrl);
    };

    let props = isLoadingTrips || pageTrips==undefined? generateItems([], component_name) :  generateItems(pageTrips.data, component_name);

    return (
        <div className={styles.list_container}>
            {props && props.length > 0 ? (
                <div>
                    <div>{props}</div>
                    <button onClick={handleNextPage}>Next Page</button>
                    <h1>{pageTrips?.total_pages}</h1>
                    <h1>{uriResult}</h1>
                </div>
            ) : (
                <div>{empty_component}</div>
            )}
        </div>
    );
};

export default PaginationComponent;
