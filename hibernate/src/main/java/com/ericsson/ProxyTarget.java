package com.ericsson;

public interface ProxyTarget<T> {
    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns the target object. If this is a lazy proxy it means that the
     * factory responsible for creating the object might be invoked.
     */
    public T getRealObject();
}
