import React, {useState} from "react";
import {Button, Col, Container, Form, FormLabel, InputGroup, Row} from "react-bootstrap";
import './index.css';
import {useStoreContext} from "common/stores/store.context";
import {ImgPreload} from "common/components/load/ImgPreload";
import {PROFILE_PAGE_KEY, SPORTS_KEY} from "common/config/tanstack.query.client";
import {useQuery} from "@tanstack/react-query";
import {initProfilePage, searchSports, updateProfile} from "./requests";
import {At, BalloonHeart, Instagram, Twitter} from "react-bootstrap-icons";
import {htmlFrom} from "common/utils/html.parse";
import {AsyncTypeahead} from "react-bootstrap-typeahead";
import {_DEFAULT} from "./model/profile_page.model";
import {Formik, FormikHelpers, FormikValues} from "formik";
import {CommonProfile, getDefaultCommonProfile} from "common/stores/auth.store";
import {getProfileSchema} from "./schemas";
import {showErrors} from "common/utils/alert";
import {ErrorMap} from "common/models/generic.model";
import {useNavigate} from "react-router-dom";
import {HOME} from "common/routes/routes";
import {merge, MergeParams} from "common/utils/obj";

interface ProfileForm extends CommonProfile {
    nickname?: string | undefined;
    profileImg?: File;
}

const ProfilePage: React.FC = () => {
    const { rootStore: { authStore } } = useStoreContext();
    const [sportSearchQ, setSportSearchQ] = useState<string>('');
    const [validationErrors, setValidationErrors] = useState<ErrorMap>();
    const userData = authStore.userData;
    const fullName = userData.getFullName();
    const profile = userData.getProfile();
    const navigate = useNavigate();

    /**
     * This makes input values initially controlled replacing undefined with default values
     */
    const initFormValues = merge(getDefaultCommonProfile() as MergeParams, {...(profile as CommonProfile)}) as ProfileForm;
    initFormValues.profileImg = undefined;

    /*const initFormValues: ProfileForm = {
        ...spreadElements,
        nickname: profile.nickname,
        profileImg: undefined
    }*/

    const { data: { bundle, maxUploadFileSize } = _DEFAULT } = useQuery({
        queryKey: [PROFILE_PAGE_KEY],
        queryFn: () => initProfilePage()
    });

    const { data, isLoading, refetch } = useQuery({
        queryKey: [SPORTS_KEY, { sportSearchQ }],
        queryFn: () => searchSports(sportSearchQ),
        enabled: !!sportSearchQ
    });

    function submit(values: FormikValues, actions: FormikHelpers<FormikValues>) {
        setValidationErrors({});
        updateProfile(values).then(res => {
            const errors = res.errors;
            if (errors) {
                setValidationErrors(errors);
            } else {
                actions.resetForm();
                authStore.profile = res.data!;
                navigate(HOME);
            }
        });
    }

    return (
        <Container>
            <Row className="mb-4">
                <h1 className="text-center">
                    {bundle?.['profilePageTitle']}
                    {fullName ? <>&nbsp;<span style={{color: '#808080'}}>({fullName})</span></> : null}
                </h1>
                <hr/>
                {validationErrors && showErrors(validationErrors)}
            </Row>
            <Formik
                initialValues={initFormValues}
                validationSchema={getProfileSchema(bundle, maxUploadFileSize)}
                onSubmit={(values, actions) => {submit(values, actions);}}
            >
                {({ handleSubmit, handleChange, values, setFieldValue, touched, errors }) => (
                    <Form noValidate onSubmit={handleSubmit}>
                        <Row>
                            <Col lg={5}>
                                <Form.Group>
                                    <div id="profile-page-img-wrapper">
                                        <ImgPreload imageSrc={userData.getProfileUrl() || ''} id="profile-page-img" roundedCircle />
                                    </div>
                                </Form.Group>
                                <Form.Group controlId="formFile" className="mb-5 mb-lg-3 col-12 col-lg-8">
                                    <Form.Label>{bundle?.['profilePicInfoTitle']}</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            name="profileImg"
                                            type="file"
                                            accept=".jpg, .jpeg, .png, .webp"
                                            onChange={(event) => {
                                                const target = event.target as HTMLInputElement & {
                                                    files: FileList
                                                }
                                                setFieldValue("profileImg", target.files[0]);
                                            }}
                                            isValid={touched.profileImg && !errors.profileImg}
                                            isInvalid={!!errors.profileImg || !!validationErrors?.['profileImg']}
                                        />
                                        <Form.Control.Feedback type="invalid" tooltip>
                                            {errors.profileImg}
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                                <h2 className="mb-md-3">{bundle?.['profileEditSocialsTitle']}</h2>
                                <Form.Group className="col-12 col-lg-8" controlId="formInstLink">
                                    <Form.Label>{bundle?.['profileEditInstagramLinkTitle']}</Form.Label>
                                    <InputGroup>
                                        <InputGroup.Text><Instagram /></InputGroup.Text>
                                        <Form.Control
                                            name="instagramLink"
                                            placeholder={''}
                                            value={values.instagramLink}
                                            onChange={handleChange}
                                            isValid={touched.instagramLink && !errors.instagramLink}
                                            isInvalid={!!errors.instagramLink || !!validationErrors?.['instagramLink']}
                                        />
                                        <Form.Control.Feedback type="invalid" tooltip>
                                            {errors.instagramLink}
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                                <Form.Group className="col-12 col-lg-8 mt-4" controlId="formXLink">
                                    <Form.Label>{bundle?.['profileEditTwitterLinkTitle']}</Form.Label>
                                    <InputGroup>
                                        <InputGroup.Text><Twitter /></InputGroup.Text>
                                        <Form.Control
                                            name="xLink"
                                            placeholder={''}
                                            value={values.xLink}
                                            onChange={handleChange}
                                            isValid={touched.xLink && !errors.xLink}
                                            isInvalid={!!errors.xLink || !!validationErrors?.['xLink']}
                                        />
                                        <Form.Control.Feedback type="invalid" tooltip>
                                            {errors.xLink}
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                            </Col>
                            <Col>
                                <Row>
                                    <h2 className="mt-5 mt-lg-0 mb-md-3">{bundle?.['profileProfileInfoTitle']}</h2>
                                    <Form.Group className="col-md-6" controlId="formFirstName">
                                        <Form.Label>{bundle?.['profileEditFirstNameTitle']}</Form.Label>
                                        <InputGroup>
                                            <Form.Control
                                                name="firstName"
                                                value={values.firstName}
                                                onChange={handleChange}
                                                isValid={touched.firstName && !errors.firstName}
                                                isInvalid={!!errors.firstName || !!validationErrors?.['firstName']}
                                            />
                                            <Form.Control.Feedback type="invalid" tooltip>
                                                {errors.firstName}
                                            </Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                    <Form.Group className="col-md-6 mt-4 mt-md-0" controlId="formLastName">
                                        <Form.Label>{bundle?.['profileEditLastNameTitle']}</Form.Label>
                                        <InputGroup>
                                            <Form.Control
                                                name="lastName"
                                                value={values.lastName}
                                                onChange={handleChange}
                                                isValid={touched.lastName && !errors.lastName}
                                                isInvalid={!!errors.lastName || !!validationErrors?.['lastName']}
                                            />
                                            <Form.Control.Feedback type="invalid" tooltip>
                                                {errors.lastName}
                                            </Form.Control.Feedback>
                                        </InputGroup>
                                    </Form.Group>
                                </Row>
                                <Row className="mt-4">
                                    <Form.Group controlId="formNickname">
                                        <Form.Label>{bundle?.['profileEditNicknameTitle']}</Form.Label>
                                        <InputGroup>
                                            <InputGroup.Text><At /></InputGroup.Text>
                                            <Form.Control
                                                name="nickname"
                                                placeholder={userData.profile?.nickname || ''}
                                                value={values.nickname}
                                                onChange={handleChange}
                                                aria-describedby="fnHelpText"
                                                isValid={touched.nickname && !errors.nickname}
                                                isInvalid={!!errors.nickname || !!validationErrors?.['nickname']}
                                            />
                                            <Form.Control.Feedback type="invalid" tooltip>
                                                {errors.nickname}
                                            </Form.Control.Feedback>
                                        </InputGroup>
                                        <Form.Text id="fnHelpText" muted>{bundle?.['profileEditNicknameHelpText']}</Form.Text>
                                    </Form.Group>
                                </Row>
                                <Row className="mt-4">
                                    <Form.Group controlId="formDob">
                                        <Form.Label>{bundle?.['profileEditDobTitle']}</Form.Label>
                                        <InputGroup>
                                            <InputGroup.Text><BalloonHeart /></InputGroup.Text>
                                            <Form.Control
                                                type="date"
                                                name="dob"
                                                value={values.dob}
                                                onChange={handleChange}
                                                aria-describedby="dobHelpText"
                                                isValid={touched.dob && !errors.dob}
                                                isInvalid={!!errors.dob || !!validationErrors?.['dob']}
                                            />
                                            <Form.Control.Feedback type="invalid" tooltip>
                                                {errors.dob}
                                            </Form.Control.Feedback>
                                        </InputGroup>
                                        <Form.Text id="dobHelpText" muted>{htmlFrom(bundle?.['profileEditDobHelpText'])}</Form.Text>
                                    </Form.Group>
                                </Row>
                                <Row className="mt-4">
                                    <Form.Group controlId="formBio">
                                        <Form.Label>{bundle?.['profileEditBioTitle']}</Form.Label>
                                        <Form.Control
                                            name="bio"
                                            placeholder={bundle?.['profileEditBioHelpText']}
                                            value={values.bio}
                                            onChange={handleChange}
                                            as="textarea"
                                            className="def-textarea"
                                            isValid={touched.bio && !errors.bio}
                                        />
                                    </Form.Group>
                                </Row>
                                <Row className="mt-4">
                                    <Form.Group>
                                        <FormLabel>{bundle?.['profileEditSportPreferenceTitle']}</FormLabel>
                                        <AsyncTypeahead
                                            id="sports-async-typeahead-multi"
                                            labelKey="name"
                                            multiple
                                            delay={50}
                                            isLoading={isLoading}
                                            promptText={bundle?.['profileEditSportPreferencePromptText']}
                                            searchText={bundle?.['profileEditSportPreferenceSearchText']}
                                            onSearch={(query) => {
                                                setSportSearchQ(query);
                                                refetch();
                                            }}
                                            onChange={(selected) => setFieldValue('sportPreference', selected)}
                                            options={data || []}
                                            placeholder={bundle?.['profileEditSportPreferencePromptText'] || ''}
                                            defaultSelected={values.sportPreference || []}
                                        />
                                        <Form.Text id="spHelpText" muted>{bundle?.['profileEditSportPreferenceHelpText']}</Form.Text>
                                    </Form.Group>
                                </Row>
                                <Row className="mt-3" sm={3} xs={2}>
                                    <Form.Group>
                                        <Button variant="dark" type="submit">{bundle?.['updateProfileBtnTitle']}</Button>
                                    </Form.Group>
                                </Row>
                            </Col>
                        </Row>
                    </Form>
                )}
            </Formik>
        </Container>
    )
}

export default ProfilePage;