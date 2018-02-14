package com.pennychecker.example.event;

import com.pennychecker.eventbus.Event;
import com.pennychecker.example.mvp.model.User;

/**
 *
 * @author Steffen Kämpke
 */
public final class UserChangedEvent extends Event<UserChangedEventHandler> {

    public final static Type<UserChangedEventHandler> TYPE = new Type<UserChangedEventHandler>();
    private final User user;

    public UserChangedEvent(User user) {
        this.user = user;
    }

    @Override
    public Type<UserChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserChangedEventHandler h) {
        h.onUserChangedEvent(this);
    }

    public User getUser() {
        return user;
    }
}

