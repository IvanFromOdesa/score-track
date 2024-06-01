import React from "react";
import {Button, Col, Row} from "react-bootstrap";
import {useStoreContext} from "../common/base/stores/store.context";
import ApisList from "../common/base/components/ApisList";
import {PlannedViewership} from "../common/base/stores/auth.store";
import {SportApi} from "../common/base/models/generic.model";
import {XCircle} from "react-bootstrap-icons";

// TODO: VP page
const ViewershipPlan: React.FC = () => {
    const { rootStore: { authStore: { userData }, uiStore: { bundle } } } = useStoreContext();
    const plannedViewership = userData.viewershipPlan?.plannedViewership;
    const customAvailableApis = userData.viewershipPlan?.customAvailableApis;

    function getPlannedViewershipInfo(plannedViewership: PlannedViewership | null | undefined) {
        return <>
           <h5>{bundle?.['plannedViewershipTitle']}:&nbsp;{plannedViewership?.name}</h5>
           <h5>{bundle?.['plannedViewershipApisTitle']}</h5>
           <ApisList apis={plannedViewership?.sportApis || []} />
        </>;
    }

    function getCustomViewershipInfo(customAvailableApis: SportApi[] | null | undefined) {
        return <>
            <h5>{bundle?.['customAvailableApisTitle']}</h5>
            <ApisList apis={customAvailableApis || []} />
        </>;
    }

    function getVPInfo() {
        return <>
            <h4 className="fw-bold text-center mb-3">{bundle?.['viewershipPlanTitle']}</h4>
            {plannedViewership && getPlannedViewershipInfo(plannedViewership)}
            {customAvailableApis?.length ? getCustomViewershipInfo(customAvailableApis) : null}
            <h5 className="mt-3">{bundle?.['viewershipPlanEndTitle']}:&nbsp;{userData.getViewershipPlanEndDateTime(false)}</h5>
            <div className="text-center mt-3">
                <Button variant="dark">{bundle?.['viewVPTitle']}</Button>
            </div>
        </>;
    }

    function getVPExpired() {
        return <>
            <div className="text-center">
                <h4 className="fw-bold">
                    {bundle?.['vpExpiredTitle']}&nbsp;
                    <a href="#">{bundle?.['renewVPTitle']}</a>
                </h4>
                <XCircle color="crimson" size={96} />
            </div>
        </>
    }

    return (
        <Col lg={5} className="widget p-3 offset-lg-1">
            <Row>
                <Col>
                    {
                        userData.viewershipPlan?.active ? getVPInfo() : getVPExpired()
                    }
                </Col>
            </Row>
        </Col>
    )
}

export default ViewershipPlan;