import React from "react";
import {OverlayTrigger, Tooltip} from "react-bootstrap";
import {QuestionCircle} from "react-bootstrap-icons";

const Hint: React.FC<{text: string | undefined, size: number}> = ({text, size}) => {
    return (
        <OverlayTrigger overlay={<Tooltip>{text}</Tooltip>}>
            <QuestionCircle size={size}/>
        </OverlayTrigger>
    )
}

export default Hint;