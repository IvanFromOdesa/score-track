import React from "react";
import {OverlayTrigger, Tooltip} from "react-bootstrap";
import {getDateTime} from "../../../../common/base/utils/date";
import {InfoCircle} from "react-bootstrap-icons";

interface IUpdateTooltipProps {
    updateText: string | undefined;
    updated: number | undefined;
    locale: string;
}

const UpdateTooltip: React.FC<IUpdateTooltipProps> = ({updateText, updated, locale}) => {
    return (
        <div className="float-end">
            <OverlayTrigger overlay={<Tooltip>{updateText} {getDateTime(updated, locale)}</Tooltip>}>
                <InfoCircle size={36}/>
            </OverlayTrigger>
        </div>
    )
}

export default UpdateTooltip;