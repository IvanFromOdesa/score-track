import React from "react";
import imgPlaceholder from "assets/img_placeholder.webp";
import {Image} from "react-bootstrap";

interface ImgPreloadProps {
    width?: number;
    height?: number;
    id?: string;
    rounded?: boolean;
    roundedCircle?: boolean;
    fluid?: boolean;
    thumbnail?: boolean;
    imageSrc: string;
    loading?: boolean;
    className?: string;
}

export const ImgPreload: React.FC<ImgPreloadProps> = (props: ImgPreloadProps) => {
    const { width, height, id, rounded, roundedCircle, fluid, thumbnail, imageSrc, loading, className } = props;

    function getImage(src: string) {
        return <Image src={src}
                      id={id}
                      style={{width: width, height: height}}
                      alt={`img_${src}`}
                      rounded={rounded || false}
                      roundedCircle={roundedCircle || false}
                      fluid={fluid || false}
                      thumbnail={thumbnail || false}
                      className={className}
        />;
    }

    return(
        <>
            {(loading || !imageSrc) ? getImage(imgPlaceholder) : getImage(imageSrc)}
        </>
    )
}