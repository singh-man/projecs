import { useState, useCallback } from 'react';
import './App.css';

import ModelResponses from './ModelResponse';

function App() {
  const [sharedPrompt, setSharedPrompt] = useState('');
  const [responses, setResponses] = useState({
    ollama: '',
    anthropic: '',
    openai: ''
  });
  const [loading, setLoading] = useState({
    ollama: false,
    anthropic: false,
    openai: false
  });
  const [responseOrder, setResponseOrder] = useState([]);

  const models = [
    { id: 'openai', name: 'OpenAI (GPT-4o)', color: '#2ECC71' },
    { id: 'anthropic', name: 'Anthropic (Claude)', color: '#9B59B6' },
    { id: 'ollama', name: 'Ollama (Gemma 2)', color: '#E67E22' }
  ];

  const handlePromptChange = useCallback((value) => {
    setSharedPrompt(value);
  }, []);

  const fetchModelResponse = useCallback(async (model, prompt) => {
    try {
      const encodedPrompt = encodeURIComponent(prompt);
      const response = await fetch(`http://localhost:8080/api/${model}/${encodedPrompt}`);

      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }

      const data = await response.text();
      return data;
    } catch (error) {
      return `Error: ${error.message}`;
    }
  }, []);

  const handleSubmit = useCallback(async () => {
    if (!sharedPrompt.trim()) return;

    // Reset the response order
    setResponseOrder([]);

    // Set all models to loading
    setLoading({
      ollama: true,
      anthropic: true,
      openai: true
    });

    // Initialize all responses as loading
    setResponses({
      ollama: 'Loading...',
      anthropic: 'Loading...',
      openai: 'Loading...'
    });

    // Process each model independently
    models.forEach(model => {
      fetchModelResponse(model.id, sharedPrompt)
        .then(response => {
          // Update this specific model's response
          setResponses(prev => ({
            ...prev,
            [model.id]: response
          }));

          // Add this model to the response order
          setResponseOrder(prev => [...prev, model.id]);

          // Set this model's loading state to false
          setLoading(prev => ({
            ...prev,
            [model.id]: false
          }));
        })
        .catch(error => {
          // Handle errors for this specific model
          setResponses(prev => ({
            ...prev,
            [model.id]: `Error: ${error.message}`
          }));

          setLoading(prev => ({
            ...prev,
            [model.id]: false
          }));
        });
    });
  }, [sharedPrompt, fetchModelResponse, models]);

  const isLoading = Object.values(loading).some(status => status);

  return (
    <div className="app-container">
      <h1>Exploring Different LLM Models</h1>

      <div className="shared-prompt-container">
        <div className="shared-prompt-area">
          <textarea
            placeholder="Enter a prompt to send to all models..."
            value={sharedPrompt}
            onChange={(e) => handlePromptChange(e.target.value)}
            disabled={isLoading}
          />

          <button
            onClick={handleSubmit}
            disabled={isLoading || !sharedPrompt.trim()}
            className="submit-all-btn"
          >
            {isLoading ? 'Sending...' : 'Compare All Models'}
          </button>
        </div>
      </div>

      {responseOrder.length > 0 && (
        <div className="response-order">
          <h3>Response Order:</h3>
          <ol>
            {responseOrder.map((modelId, index) => {
              const model = models.find(m => m.id === modelId);
              return (
                <li key={modelId} style={{ color: model.color }}>
                  {model.name} {index === 0 ? '(fastest)' : ''}
                </li>
              );
            })}
          </ol>
        </div>
      )}

      <ModelResponses models={models} responses={responses} responseOrder={responseOrder} loading={loading} />
    </div>
  );
}

export default App;