import styles from "./styles.module.scss";
import PaginationModel from "@/models/PaginationModel.tsx";
import {useState} from "react";
import { useLocation } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import {ButtonGroup, ToggleButton} from "react-bootstrap";

interface PaginationComponentProps<T> {
    empty_component: React.ReactNode;
    uri: string;
    current_page: number;
    component_name: React.FC<T>;
    useFuction: (uri:string) => {isLoading: boolean; trips: PaginationModel<T> | undefined; }
}

const PaginationComponent = <T,>({
                                empty_component,
                                uri,
                                component_name,
                                current_page,
                                     useFuction
                            }: PaginationComponentProps<T>) => {

    const [uriResult, setUriResult] = useState(uri);
    const [currentPage, setcurrentPage] = useState(current_page);
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
        setcurrentPage(currentPage+1)
        const searchParams = new URLSearchParams(location.search);
        searchParams.set('page', currentPage.toString());
        const newUrl = `${location.pathname}?${searchParams.toString()}`;
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
                    <h1>{uri}</h1>
                    <h1>{currentPage}</h1>
                    <h1>{pageTrips?.prev == null ? "null" : pageTrips?.prev}</h1>
                    <div>
                        <ButtonGroup className="mb-2">
                            <ToggleButton
                                id={`radio-pepe2`}
                                value="pepe2"
                                onClick={handleNextPage}
                            ><i className="bi bi-chevron-double-left"></i>
                            </ToggleButton>
                            <ToggleButton
                                id={`radio-pepe2`}
                                value="pepe2"
                                disabled={true}
                            ><i className="bi bi-three-dots"></i>
                            </ToggleButton>
                            <ToggleButton
                                id={`radio-pepe`}
                                value="pepe"
                                onClick={handleNextPage}
                            >pepe
                            </ToggleButton>
                            <ToggleButton
                                id={`radio-pepe2`}
                                value="pepe2"
                                disabled={true}
                            ><i className="bi bi-three-dots"></i>
                            </ToggleButton>
                            <ToggleButton
                                key="jose"
                                id={`radio-"jose"`}
                                type="radio"
                                variant="secondary"
                                value="jose"
                                name="radio"
                                onClick={handleNextPage}
                            ><i className="bi bi-chevron-double-right"></i>
                            </ToggleButton>
                        </ButtonGroup>
                    </div>
                </div>
            ) : (
                <div>{empty_component}</div>
            )}
        </div>
    );
};

export default PaginationComponent;
