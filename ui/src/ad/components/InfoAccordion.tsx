import React from "react";
import {WithLoadingProps} from "../../common/models/generic.model";
import {Accordion} from "react-bootstrap";
import {htmlFrom} from "../../common/utils/html.parse";
import {TextPreload, TitlePreload} from "../../common/components/load/Preload";

const InfoAccordion: React.FC<WithLoadingProps> = ({ bundle, loading}) => {
    return (
        <div>
            <br/>
            <div className="text-center mt-auto">
                <TitlePreload loading={loading} size={2}>
                    <h2>{bundle?.['whyST']}</h2>
                </TitlePreload>
            </div>
            <br/>
            <Accordion>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>{bundle?.['easyToUseTitle']}</Accordion.Header>
                    <Accordion.Body>{bundle?.['easyToUseDescription']}</Accordion.Body>
                </Accordion.Item>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>{bundle?.['personalizedContentTitle']}</Accordion.Header>
                    <Accordion.Body>{htmlFrom(bundle?.['personalizedContentDescription'])}</Accordion.Body>
                </Accordion.Item>
                <Accordion.Item eventKey="2">
                    <Accordion.Header>{bundle?.['analyticsTitle']}</Accordion.Header>
                    <Accordion.Body>{bundle?.['analyticsDescription']}</Accordion.Body>
                </Accordion.Item>
            </Accordion>
            <br/>
            <TextPreload loading={loading}>
                <p>{bundle?.['summary']}</p>
            </TextPreload>
        </div>
    );
};

export default InfoAccordion;