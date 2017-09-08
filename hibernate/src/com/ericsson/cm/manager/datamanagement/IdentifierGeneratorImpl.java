package com.ericsson.cm.manager.datamanagement;

import org.apache.log4j.Logger;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.Configurable;
import org.hibernate.type.Type;

import java.util.Properties;

//import org.hibernate.engine.spi.SessionImplementor;

public class IdentifierGeneratorImpl implements /*IdentifierGenerator,*/ Configurable {
	//~ Static variables/initializers ----------------------------------------------------

	private static final String NAME = "entity_name";
	private static final Logger LOG = Logger.getLogger(IdentifierGeneratorImpl.class);
	private static int instanceCounter = 0;


	private final int ordinal;
	private String seqName = "GLOBAL";

	public IdentifierGeneratorImpl() {
		this.ordinal = ++instanceCounter;

		LOG.trace(msg("created!"));
	}

	@SuppressWarnings("boxing")
//	public Serializable generate(SessionImplementor sessionImpl, Object entity) {
//		if(LOG.isTraceEnabled()) {
//			LOG.trace(msg("generate(SessionImplementor, " + entity.getClass().getName() + ") --> Getting long from sequence \"" + seqName + "\"."));
//		}
//
//		SequenceManager seqMan = SequenceManagerFactory.getSequenceManager();
//
//		if(seqMan == null) {
//			throw new IllegalStateException("No SequenceManager created yet!");
//		}
//
//		return seqMan.getNext(seqName);
//	}


	public void configure(@SuppressWarnings("unused")
	Type type, Properties props, @SuppressWarnings("unused")
	Dialect dialect) {
		if(LOG.isTraceEnabled()) {
			LOG.trace(msg("configure(" + type + ", props, " + dialect + ")\n Properties: " + props));
		}

		this.seqName = props.getProperty(NAME, this.seqName);
	}


	private String msg(String str) {
		return "#" + this.ordinal + " " + str;
	}
}

