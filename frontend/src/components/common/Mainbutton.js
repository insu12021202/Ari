import React from 'react';
import styled from 'styled-components';

const StyledButton = styled.button`
    width: 260px;
    height: 41px;
    margin-bottom: 11px;
    border-style: initial;
    border-radius: ${(props) => props.radius};
    color: ${(props) => props.color};
    background: ${(props) => props.disabled ? '#DCDCDC' : props.background};
    cursor: pointer;
    &:disabled {
        cursor: default;
    }
`;

const MainButton = ({ radius, color, background, disabled, text, onClick }) => {
    return (
        <StyledButton
            radius={radius}
            color={color}
            background={background}
            disabled={disabled}
            onClick={onClick}>
            {text}
        </StyledButton>
    );
};

export default MainButton;