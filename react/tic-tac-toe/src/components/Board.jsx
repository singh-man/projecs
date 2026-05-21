import Square from './Square';
import Triangle from './Triangle';
import React, { useState, useCallback, useMemo, useRef } from 'react';

function Board({ xIsNext, squares, onPlay }) {
    const [tic, setTic] = useState(1);
    var x = useRef(1)
    var handleClick = function(i) {
        if (calculateWinner(squares) || squares[i]) {
            return;
        }
        const nextSquares = squares.slice();
        nextSquares[i] = xIsNext ? "X" : "O";
        onPlay(nextSquares);
        x.current += 1;
        // setTic(tic + 1);
    }

    var clickCallback = useCallback(() => {
        setTic(tic + 1); // comment this to stop rendering of triangle
        console.log("useCallback");
    }, [tic]);

    var clickUseRef = () => {
        x.current += 1;
        console.log("useRef");
    }

    const winner = calculateWinner(squares);
    let status;
    if (winner) {
        status = "Winner: " + winner;
    } else if (squares.every(Boolean)) {
        status = "Draw!";
    } else {
        status = "Next player: " + (xIsNext ? "X" : "O");
    }

    const mySquare = () => {
        const tmpSq = [];
        for (let i = 0; i < 9; i++) {
            tmpSq.push(<Square value={squares[i]} onSquareClick={() => handleClick(i)} />);
        }
        return tmpSq;
    }
    // console.log("Board");
    return (
        <>
            <div className="status">{status}</div>
            <div className="board">
                {mySquare()}
                {/* <Square value={squares[0]} onSquareClick={() => handleClick(0)} />
                <Square value={squares[1]} onSquareClick={() => handleClick(1)} />
                <Square value={squares[2]} onSquareClick={() => handleClick(2)} />
                <Square value={squares[3]} onSquareClick={() => handleClick(3)} />
                <Square value={squares[4]} onSquareClick={() => handleClick(4)} />
                <Square value={squares[5]} onSquareClick={() => handleClick(5)} />
                <Square value={squares[6]} onSquareClick={() => handleClick(6)} />
                <Square value={squares[7]} onSquareClick={() => handleClick(7)} />
                <Square value={squares[8]} onSquareClick={() => handleClick(8)} /> */}
            </div>
            <Triangle value={tic} x={x} clickCallback={clickCallback} clickUseRef={clickUseRef} />
        </>
    );
}

function calculateWinner(squares) {
    const lines = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6]
    ];
    for (let i = 0; i < lines.length; i++) {
        const [a, b, c] = lines[i];
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
            return squares[a];
        }
    }
    return null;
}

export default Board;
