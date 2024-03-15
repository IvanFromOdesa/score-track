import React from "react";
import Profile from "../profile";
import {Alert, Container, Row} from "react-bootstrap";
import ViewershipPlan from "../vp";
import {useStoreContext} from "../common/stores/store.context";
import Api from "../api";

const Home: React.FC = () => {
    return (
        <>
            <Container>
                <Alerts />
                <Row className="justify-content-center d-flex gap-3 gap-lg-0">
                    <Profile />
                    <ViewershipPlan />
                </Row>
            </Container>
            <br />
            <Api />
        </>
    )
}

const Alerts: React.FC = () => {
    const { rootStore: { authStore, uiStore: { bundle } } } = useStoreContext();
    const userData = authStore.userData;

    function alertVPExpired() {
        return <Alert variant="danger" dismissible>
            {bundle?.['vpExpiredAlertTitle']}&nbsp;{userData.getViewershipPlanEndDateTime(true)}.&nbsp;
            <Alert.Link href="#">{bundle?.['renewVPTitle']}</Alert.Link>
        </Alert>;
    }

    function alertProfileUpdated() {
        return <Alert variant="info" dismissible onClose={() => authStore.profileUpdated = false}>
            {bundle?.['profileUpdated']}
        </Alert>;
    }

    return (
        <>
            {!userData.viewershipPlan?.active && alertVPExpired()}
            {authStore.profileUpdated && alertProfileUpdated()}
        </>
    )
}

export default Home;