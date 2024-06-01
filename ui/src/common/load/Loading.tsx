import React from "react";
import './loading.css';

interface ILoadingProps {
    fixed?: boolean;
}

export const Loading: React.FC<ILoadingProps> = ({fixed}) => {
    return (
        <div className={`spinner-wrapper${fixed ? '-fixed' : ''}`}>
            <div className="spinner">
                <div className="sk-folding-cube">
                    <div className="sk-cube1 sk-cube"></div>
                    <div className="sk-cube2 sk-cube"></div>
                    <div className="sk-cube4 sk-cube"></div>
                    <div className="sk-cube3 sk-cube"></div>
                </div>
            </div>
        </div>
    )
};