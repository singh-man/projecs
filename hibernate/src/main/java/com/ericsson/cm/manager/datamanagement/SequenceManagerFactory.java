package com.ericsson.cm.manager.datamanagement;

import org.apache.log4j.Logger;

public class SequenceManagerFactory {

    private static final Logger LOG = Logger.getLogger(SequenceManagerFactory.class);
    private static SequenceManager seqMan;

    public static SequenceManager getSequenceManager() {
        return seqMan;
    }

    public static void setSequenceManager(final SequenceManager newSeqMan) {
        if((seqMan != null) && (seqMan != newSeqMan)) {
            LOG.warn("Competing SequenceManager instances! " + seqMan + "!=" + newSeqMan + "! Current stack:", new Throwable());
        }

        seqMan = newSeqMan;
    }
}
