package com.ericsson;

import org.apache.commons.collections4.Transformer;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;


/**
 * Utility to remove proxying from an proxied object. Could be modified to be
 * extensible, however currently supports Advised and ProxyTarget proxy
 * interfaces.
 *
 * @author jukka.lindstrom@tecnomen.com
 *
 * @param <OBJECT>
 *            {@link Advised} {@link ProxyTarget}
 */
public class UnWrapperImpl<OBJECT> implements Transformer<OBJECT, OBJECT> {
	//~ Static variables/initializers ----------------------------------------------------

	private static final Logger log = Logger.getLogger(UnWrapperImpl.class);

	@SuppressWarnings("unchecked")
	public OBJECT transform(OBJECT target) {
		target = getTarget(target);

		return target;
	}

	@SuppressWarnings("unchecked")
	public boolean isNull(OBJECT original) {
		return (getTarget(original) == null);
	}


	@SuppressWarnings("unchecked")
	private OBJECT getTarget(OBJECT original) {
		return unwrap(original);
	}


	public static <T> T unwrap(T original) {
		if(original == null) {
			return null;
		}

		T target = original;

		try {
			while(target instanceof Advised || target instanceof ProxyTarget) {
				if(target instanceof Advised) {
					target = (T)((Advised)target).getTargetSource().getTarget();
				}

				if(target == null) {
					return null;
				}

				if(target instanceof ProxyTarget) {
					target = (T)((ProxyTarget)target).getRealObject();
				}

				if(target == null) {
					return null;
				}
			}
		} catch(Exception e) {
			log.error("Failed to get target from Proxy", e);
		}

		return target;
	}
}
