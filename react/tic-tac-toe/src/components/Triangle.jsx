import React, { useState, useCallback } from 'react';
function Triangle({value, x, clickCallback, clickUseRef}) {
    const x= fetch('https://jsonplaceholder.typicode.com/posts/1')
    x.json()
    console.log("Triangle", value, ' ', x);
    return (
        <>
            <h3>I am here {value}</h3>
            <button onClick={clickCallback}>useCallback</button> 
            <button onClick={clickUseRef}>useRef</button> 
            {/* <button
                className={`square ${value ? value.toLowerCase() : ''}`} onClick={onSquareClick} >
                {value}
            </button> */}
        </>
    );
}

export default React.memo(Triangle);
