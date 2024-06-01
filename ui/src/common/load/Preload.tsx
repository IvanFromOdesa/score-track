import React, {ReactNode} from "react";
import {Placeholder} from "react-bootstrap";

interface CommonProps {
    children: ReactNode;
    loading: boolean;
    size?: number;
}

interface TitleTextProps extends CommonProps {
    type?: React.ElementType;
}

export const TitlePreload: React.FC<TitleTextProps> = (props: TitleTextProps) => {
    const { loading, size, children, type } = props;
    return (
        <>
            {
                loading ? <Placeholder as={type || "h3"} animation="glow">
                    <Placeholder xs={size || 1} />
                </Placeholder> : children
            }
        </>
    )
}

export const BtnPreload: React.FC<CommonProps> = (props: CommonProps) => {
    const { loading, size, children } = props;
    return (
        <>
            {
                loading ? <Placeholder.Button variant="dark" xs={size || 1} /> : children
            }
        </>
    )
}

export const TextPreload: React.FC<TitleTextProps> = (props: TitleTextProps) => {
    const { loading, type, size, children } = props;
    return (
        <>
            {
                loading ? <Placeholder as={type || "p"} animation="glow">{[...Array(size)].map((e, idx) => <span key={idx}>
                    <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} />{' '}
                    <Placeholder xs={6} /> <Placeholder xs={8} /> <Placeholder xs={3} />
                </span>)}</Placeholder> : children
            }
        </>
    )
}