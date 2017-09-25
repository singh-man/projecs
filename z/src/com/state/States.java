package com.state;
public enum States implements State {
    START {
        public boolean process(StateHandler sh) {
            if (sh.handle()) 
            	sh.state(States.STARTED);
            return false;
        }
    }, STARTED {
    	public boolean process(StateHandler sh) {
            if (sh.handle()) 
            	sh.state(States.PROCESSING);
            return false;
        }
    }, PROCESSING {
    	public boolean process(StateHandler sh) {
            if (sh.handle()) 
            	sh.state(States.PROCESSING);
            return false;
        }
    }, STOP {
    	public boolean process(StateHandler sh) {
            if (sh.handle()) 
            	sh.state(States.PROCESSING);
            return false;
        }
    }
}

/*public void handle(Context context) {
    socket.read(context.buffer());
    while(context.state().process(context));
}*/