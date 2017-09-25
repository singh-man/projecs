package com.state;

interface State {
    boolean process(StateHandler context);
}
