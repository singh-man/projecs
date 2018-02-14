package com.state;

interface StateHandler {
    
	boolean handle();
    void state(State state);
}

