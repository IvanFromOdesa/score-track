import {ErrorMap} from "../models/generic.model";
import {Alert} from "react-bootstrap";

export const showErrors = (errors: ErrorMap) => {
    return Object.entries(errors).map(([key, value]) => {
        return <Alert variant="danger" key={"error." + key} dismissible>
            {
                value.msg || value.errors && Object.entries(value.errors).map(([key, value]) => {
                    return <div key={key}>{value}<br/></div>;
                })
            }
        </Alert>;
    });
}