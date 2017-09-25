package com.async.response;

public abstract class AbstractOnCompletion implements OnCompletion {

	private static int classCounter;
	private final int hashCode;

	protected AbstractOnCompletion() {
		hashCode = ++classCounter;
	}

	@Override
	public final int hashCode() {
		return hashCode;
	}
}
