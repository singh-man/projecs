package com.ericsson.cm.manager.datamanagement;

public interface SequenceManager {
    //~ Instance variables ---------------------------------------------------------------

    /**
     * The name by which the singleton ought to be known in spring config.
     */
    public String BEAN_NAME = "sequenceManager";

    //~ Methods --------------------------------------------------------------------------

    /**
     * Get next available identifier for a named sequence.
     *
     * @param sequenceName Sequence name (use entity class name, for example)
     * @return Next unique id (in cluster).
     */
    public long getNext(final String sequenceName);


    /**
     * Reset the sequence
     * Used for data inialization purposes.
     */
    public void reset();
}
