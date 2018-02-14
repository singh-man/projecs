package com.ericsson.cm.common.exception;

import java.text.MessageFormat;

import com.ericsson.cm.common.constant.MMErrorCode;
import com.ericsson.cm.common.constant.Mask;

public abstract class MMException extends Exception {

	private static final long serialVersionUID = -6574284477488834358L;

	private final MMErrorCode myErrorCode;

	private final Mask mySource;

	public MMException(MMErrorCode aErrorCode, Mask aSource) {
		super(aErrorCode.getErrorDescription());
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}
	
	public MMException(MMErrorCode aErrorCode, Mask aSource, Object[] aFormatter) {
		super(MessageFormat.format(aErrorCode.getErrorDescription(), aFormatter));
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMException(MMErrorCode aErrorCode, Mask aSource, String aErrorMsg) {
		super(aErrorCode.getErrorDescription() + " : " + aErrorMsg);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMException(MMErrorCode aErrorCode, Mask aSource, String aErrorMsg, Object aFormatter) {
		super(MessageFormat.format(aErrorCode.getErrorDescription(), aFormatter) + " : " + aErrorMsg);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMException(MMErrorCode aErrorCode, Mask aSource,
			Throwable aBaseException) {
		super(aErrorCode.getErrorDescription(), aBaseException);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMException(MMErrorCode aErrorCode, Mask aSource,
			Throwable aBaseException, Object[] aFormatter) {
		super(MessageFormat.format(aErrorCode.getErrorDescription(), aFormatter), aBaseException);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMException(MMErrorCode aErrorCode, Mask aSource, String aErrorMsg,
			Throwable aBaseException) {
		super(aErrorCode.getErrorDescription() + " : " + aErrorMsg, aBaseException);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMException(MMErrorCode aErrorCode, Mask aSource, String aErrorMsg,
			Throwable aBaseException, Object aFormatter) {
		super(MessageFormat.format(aErrorCode.getErrorDescription(), aFormatter) + " : " + aErrorMsg, aBaseException);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMErrorCode getCode() {
		return this.myErrorCode;
	}

	public Mask getSource() {
		return this.mySource;
	}
}
