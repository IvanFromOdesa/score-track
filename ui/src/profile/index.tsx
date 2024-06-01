import React from "react";
import {Button, Col, Form, FormLabel, Row} from "react-bootstrap";
import {ImgPreload} from "../common/load/ImgPreload";
import {useStoreContext} from "../common/base/stores/store.context";
import './index.css';
import {Typeahead} from "react-bootstrap-typeahead";
import {At, PencilSquare, PersonFill} from "react-bootstrap-icons";
import {useNavigate} from "react-router-dom";
import {PROFILE_PAGE} from "../common/base/routes/routes";

// TODO: highlight typeahead tokens on hover
const Profile: React.FC = () => {
    const { rootStore: { authStore: { userData }, uiStore: { bundle } } } = useStoreContext();
    const sportPreference = userData.profile?.sportPreference;
    const navigate = useNavigate();

    return (
        <Col lg={6} className="widget d-flex gap-3 p-3">
            <Row className="flex-grow-1">
                <Col md={5}>
                    <ImgPreload imageSrc={userData.getProfileUrl() || ''} id="profile-img" roundedCircle />
                    <br />
                    <h4 className="highlighted"><At />{userData.profile?.nickname || bundle?.['addNicknameTitle']}</h4>
                    <h5 className="highlighted"><PersonFill />&nbsp;{userData.getFullName() || bundle?.['addFullNameTitle']}</h5>
                </Col>
                <Col md={6} className="d-flex align-items-start flex-column">
                    <Form.Group>
                        <FormLabel>
                            <h4>{bundle?.['sportPreferencesTitle']}</h4>
                        </FormLabel>
                        {
                            sportPreference?.length ?
                                <Typeahead
                                    id="sports-typeahead-multi"
                                    labelKey="name"
                                    className="mb-2"
                                    disabled
                                    multiple
                                    options={sportPreference || []}
                                    defaultSelected={sportPreference || []}
                                /> :
                                <>
                                    <br/>
                                    <Button variant="light" className="mb-4">{bundle?.['addPreferencesTitle']}</Button>
                                </>
                        }
                    </Form.Group>
                    <Button variant="dark" onClick={() => navigate(PROFILE_PAGE)} className="mt-auto"><PencilSquare/>&nbsp;{bundle?.['editProfileTitle']}</Button>
                </Col>
            </Row>
        </Col>
    )
}

export default Profile;