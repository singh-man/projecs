package com.ericsson.cm.common.exception;

import com.ericsson.cm.common.constant.MMErrorCode;
import com.ericsson.cm.common.constant.Mask;

public class MMRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4074540779104296248L;

	private final MMErrorCode myErrorCode;

	private final Mask mySource;

	public MMRuntimeException(MMErrorCode aErrorCode, Mask aSource) {
		super();
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMRuntimeException(MMErrorCode aErrorCode, Mask aSource,
			String aErrorMsg) {
		super(aErrorMsg);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMRuntimeException(MMErrorCode aErrorCode, Mask aSource,
			Throwable aBaseException) {
		super(aBaseException);
		this.myErrorCode = aErrorCode;
		this.mySource = aSource;
	}

	public MMRuntimeException(MMErrorCode aErrorCode, Mask aSource,
			String aErrorMsg, Throwable aBaseException) {
		super(aErrorMsg, aBaseException);
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
