function Square({ value, onSquareClick }) {
    console.log("Square");
    return (
        <button
            className={`square ${value ? value.toLowerCase() : ''}`}
            onClick={onSquareClick}
        >
            {value}
        </button>
    );
}

export default Square;
