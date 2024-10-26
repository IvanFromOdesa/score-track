import {ApiNbaStatCategory} from "../../common/models/stat.category.model";
import {ApiNbaStatCategoriesGroupKeys, ApiNbaStatCategoriesGroupMap} from "../models/player.stats.model";
import {Bundle} from "../../../../common/base/models/generic.model";
import React, {useEffect, useState} from "react";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";

export function PlayerStatCategoriesModal(modalShow: boolean,
                                          hideModal: () => void,
                                          selectedValues: ApiNbaStatCategory[],
                                          setSelectedValues: (values: ApiNbaStatCategory[]) => void,
                                          statCategories: ApiNbaStatCategoriesGroupMap,
                                          helpText: Bundle | undefined) {

    const [localSelectedValues, setLocalSelectedValues] = useState<ApiNbaStatCategory[]>(selectedValues);
    const [selectedGroup, setSelectedGroup] = useState<string | null>(null);

    useEffect(() => {
        if (modalShow) {
            setLocalSelectedValues(selectedValues);
        }
    }, [modalShow, selectedValues]);

    const handleCheckboxChange = (value: ApiNbaStatCategory, selectedGroup: string) => {
        if (localSelectedValues.includes(value)) {
            const newSelectedValues = localSelectedValues.filter((item) => item !== value);
            setLocalSelectedValues(newSelectedValues);
            if (newSelectedValues.length === 0) {
                setSelectedGroup(null);
            }
        } else {
            setLocalSelectedValues([...localSelectedValues, value]);
            setSelectedGroup(selectedGroup);
        }
    }

    const handleSave = () => {
        setSelectedValues(localSelectedValues);
        hideModal();
    };

    function getStatCategoriesGroup(key: ApiNbaStatCategoriesGroupKeys) {
        const groupTitle = statCategories[key].title;
        return <Col>
            <h5><b>{groupTitle}</b></h5>
            {
                statCategories[key].statCategories.map((statCategory, idx) =>
                    <Form.Check
                        key={idx}
                        type="checkbox"
                        label={statCategory.uiText}
                        checked={localSelectedValues.includes(statCategory)}
                        onChange={() => handleCheckboxChange(statCategory, groupTitle)}
                        disabled={selectedGroup !== null && selectedGroup !== groupTitle}
                    />
                )
            }
        </Col>;
    }

    return <Modal
        show={modalShow}
        onHide={hideModal}
        centered
        backdrop="static"
    >
        <Modal.Header closeButton>
            <Modal.Title className="w-100 text-center"><b>{helpText?.['customStatsChartModalTitle']}</b></Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <p>{helpText?.['customStatsChartModalDescription']}</p>
            <Form>
                <Row>
                    {getStatCategoriesGroup('main')}
                    {getStatCategoriesGroup('rebounds')}
                    {getStatCategoriesGroup('miscellaneous')}
                </Row>
                <Row className="mt-2">
                    {getStatCategoriesGroup('fieldGoals')}
                    {getStatCategoriesGroup('freeThrows')}
                    {getStatCategoriesGroup('threePoints')}
                </Row>
            </Form>
        </Modal.Body>
        <Modal.Footer className="justify-content-center">
            <Button variant="secondary" onClick={hideModal}>
                {helpText?.['customStatsChartModalCancelButtonTitle']}
            </Button>
            <Button variant="primary" onClick={handleSave}>
                {helpText?.['customStatsChartModalApplyButtonTitle']}
            </Button>
        </Modal.Footer>
    </Modal>;
}