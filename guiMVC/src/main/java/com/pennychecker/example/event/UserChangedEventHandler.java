package com.pennychecker.example.event;

import com.pennychecker.eventbus.EventHandler;

/**
 *
 * @author Steffen Kämpke
 */
public interface UserChangedEventHandler extends EventHandler {
    void onUserChangedEvent(UserChangedEvent event);
}


