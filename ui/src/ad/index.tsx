import React from "react";
import {useStoreContext} from "../common/stores/store.context";
import {Button, Carousel, Col, Container, Row} from "react-bootstrap";
import {LazyLoadImage} from "react-lazy-load-image-component";
import 'react-lazy-load-image-component/src/effects/blur.css';
import './index.css';
import nbaImg from "../assets/slide/nba.webp";
import nflImg from "../assets/slide/nfl.webp";
import soccerImg from "../assets/slide/soccer.webp";
import InfoAccordion from "./components/InfoAccordion";
import {BtnPreload, TextPreload, TitlePreload} from "common/components/load/Preload";
import {Loading} from "common/components/load/Loading";
import SupportedApis from "./components/SupportedApis";

const Ad: React.FC = () => {
    const { rootStore } = useStoreContext();
    const bundle = rootStore.uiStore.bundle;
    const loading = rootStore.loading;

    return (
        <>
            <Loading loading={loading} />
            <Container className={loading ? 'loading' : ''}>
                <Row className="justify-content-center align-items-center">
                    <Col>
                        <Carousel>
                            <Carousel.Item className="ad-item">
                                <LazyLoadImage alt="1" src={nbaImg} effect="blur" className="d-block w-100 ad-img"/>
                                <Carousel.Caption>
                                    <h3>{bundle?.['adTitle']}</h3>
                                    <p>{bundle?.['adDescription']}</p>
                                </Carousel.Caption>
                            </Carousel.Item>
                            <Carousel.Item className="ad-item">
                                <LazyLoadImage alt="2" src={nflImg} effect="blur" className="d-block w-100 ad-img"/>
                            </Carousel.Item>
                            <Carousel.Item className="ad-item">
                                <LazyLoadImage alt="3" src={soccerImg} effect="blur" className="d-block w-100 ad-img"/>
                            </Carousel.Item>
                        </Carousel>
                    </Col>
                    <Col xl={5}>
                        <br/>
                        <TitlePreload loading={loading} size={6}>
                            <h2>{bundle?.['aboutTitle']}</h2>
                        </TitlePreload>
                        <TextPreload loading={loading} size={2}>
                            <p>{bundle?.['aboutDescription']}</p>
                        </TextPreload>
                        <div className="text-center mt-auto">
                            <BtnPreload loading={loading} size={2}>
                                <Button variant="dark" size="lg">{bundle?.['adSubscribe']}</Button>
                            </BtnPreload>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <InfoAccordion bundle={bundle} loading={loading} />
                </Row>
                <Row>
                    <SupportedApis bundle={bundle} loading={loading} />
                </Row>
            </Container>
        </>
    );
}

export default Ad;