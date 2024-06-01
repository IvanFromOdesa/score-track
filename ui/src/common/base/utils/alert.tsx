import {ErrorMap} from "../models/generic.model";
import {Alert} from "react-bootstrap";

export const showErrors = (errors: ErrorMap) => {
    return Object.entries(errors).map(([key, value]) => {
        return <Alert variant="danger" key={"error." + key} dismissible>
            {
                Object.entries(value).map(([key, name]) => {
                    return <div key={key}>{name}<br/></div>;
                })
            }
        </Alert>;
    });
}