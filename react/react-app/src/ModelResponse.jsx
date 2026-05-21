import React from 'react';

const ModelResponses = ({ models, responses, responseOrder, loading }) => {
    return (
        <div className="model-grid">
            {models.map(model => (
                <div
                    key={model.id}
                    className="model-box"
                    style={{
                        borderColor: model.color,
                        // Highlight the fastest model
                        boxShadow: responseOrder[0] === model.id ? `0 0 15px ${model.color}` : 'none'
                    }}
                >
                    <h2 style={{ color: model.color }}>
                        {model.name}
                        {responseOrder.includes(model.id) && (
                            <span className="response-badge">
                                {responseOrder.indexOf(model.id) + 1}
                            </span>
                        )}
                    </h2>

                    <div className="response-area">
                        <h3>Response:</h3>
                        <div className="response-content">
                            {responses[model.id] ? (
                                <div className="response-text">{responses[model.id]}</div>
                            ) : (
                                loading[model.id] ? (
                                    <div className="loading-spinner"></div>
                                ) : (
                                    <div className="no-response">No response yet.</div>
                                )
                            )}
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ModelResponses;
