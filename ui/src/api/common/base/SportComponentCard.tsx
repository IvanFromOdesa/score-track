import React from "react";
import {Badge, Button, Card} from "react-bootstrap";
import {useStoreContext} from "../../../common/base/stores/store.context";
import './sport-component-card.css';

interface ISportComponentCardProps {
    img: string;
    title: string;
    badges: string[];
    btnTitle?: string;
    info?: string;
    infoJSX?: () => React.ReactElement;
    btnOnClick?: () => void;
}

const SportComponentCard: React.FC<ISportComponentCardProps> = ({...props}) => {
    const { rootStore: { uiStore: { bundle} } } = useStoreContext();

    return (
        <>
            <Card className="game-card h-100">
                <div className="text-center m-2">
                    <Card.Img variant="top" src={props.img} style={{width: '120px', height:'120px'}} loading={"lazy"}/>
                </div>
                <Card.Body className="game-card-body d-flex flex-column">
                    <Card.Title className="text-center">
                        <h4>{props.title}</h4>
                    </Card.Title>
                    &nbsp;
                    <span>
                        {props.info || props.infoJSX?.()}
                    </span>
                    <h3>
                        {props.badges.map((name, i) => <Badge bg="primary" key={i} style={{marginRight: '5px', marginBottom: '3px'}}>{name}</Badge>)}
                    </h3>
                    <div className="text-center mt-auto">
                        <Button variant="dark" onClick={props.btnOnClick}>{props.btnTitle || bundle?.['defaultSportComponentBtnTitle']}</Button>
                    </div>
                </Card.Body>
            </Card>
        </>
    )
}

export default SportComponentCard;