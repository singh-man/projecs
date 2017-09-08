package com.gui.action;

public abstract class AbstractEvent<EH extends EventHandler>  implements Event<EH> {

	public static class Type<EH> {
		private static int classCounter;
		private final int hashCode;

		public Type() {
			hashCode = ++classCounter;
		}

		@Override
		public final int hashCode() {
			//int i = super.hashCode();
			return hashCode;
		}
	}

	public abstract Type<EH> getType();
}
