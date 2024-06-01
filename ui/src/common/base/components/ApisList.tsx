import React from "react";
import {SportApi} from "../models/generic.model";
import {ListGroup} from "react-bootstrap";
import {LazyLoadImage} from "react-lazy-load-image-component";

interface ApisListProps {
    className?: string;
    imgSize?: number;
    hideName?: boolean;
    apis: SportApi[];
}

const ApisList: React.FC<ApisListProps> = ({apis, className, imgSize, hideName}) => {
    return (
        <ListGroup horizontal className={className || ''}>
            {
                apis.map((api, idx) => {
                    return (
                        <ListGroup.Item key={idx}>
                            <div className="text-center mt-auto">
                                <LazyLoadImage alt={"logo_" + idx} src={api.logoUrl} style={{width: imgSize, height: imgSize}} effect="blur"/>
                            </div>
                            {!hideName && <div className="api-name">{api.name}</div>}
                        </ListGroup.Item>
                    )
                })
            }
        </ListGroup>
    )
}

export default ApisList;