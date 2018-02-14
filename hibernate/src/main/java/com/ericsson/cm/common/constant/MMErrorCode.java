package com.ericsson.cm.common.constant;

import java.io.Serializable;
import java.util.ResourceBundle;

public class MMErrorCode implements Serializable {

	private static final long serialVersionUID = -6013128352449410529L;

	private static ResourceBundle rb = ResourceBundle
			.getBundle("ExceptionResource");

	/**
	 * General Error Code Range: 1000 - 1999
	 */
	public static final MMErrorCode GENERAL_BASE_ERROR_CODE = new MMErrorCode(
			1000);

	public static final MMErrorCode NULL_PARAM_ERROR_CODE = new MMErrorCode(1001);

	public static final MMErrorCode INVALID_CONFIG_PARAMETERS = new MMErrorCode(1002);

	public static final MMErrorCode INVALID_IPADDR_ARGUMENT = new MMErrorCode(1003);

	public static final MMErrorCode INVALID_PORTNO_ARGUMENT = new MMErrorCode(1004);

	public static final MMErrorCode INVALID_POLLTIMEOUT_ARGUMENT = new MMErrorCode(1005);

	public static final MMErrorCode INVALID_ARGUMENT = new MMErrorCode(1006);

	public static final MMErrorCode INVALID_RESPONSE = new MMErrorCode(1007);

	public static final MMErrorCode NO_RESPONSE_RECEIVED = new MMErrorCode(1008);

	public static final MMErrorCode ARRAY_LENGTH_MISMATCH = new MMErrorCode(1009);

	public static final MMErrorCode KEY_NOT_FOUND = new MMErrorCode(1010);

	public static final MMErrorCode DUPLICATE_KEY = new MMErrorCode(1011);

	public static final MMErrorCode THREAD_INTERRUPTED = new MMErrorCode(1012);

	public static final MMErrorCode INVALID_LENGTH_ARRAY = new MMErrorCode(1013);

	public static final MMErrorCode FILE_NOT_FOUND_EXCEPTION = new MMErrorCode(1014);

	public static final MMErrorCode FILE_IO_EXCEPTION = new MMErrorCode(1015);

	public static final MMErrorCode STREAM_OPEN_ERROR = new MMErrorCode(1016);

	public static final MMErrorCode CLASS_NOT_FOUND = new MMErrorCode(1017);
	
	public static final MMErrorCode OPERATION_NOT_SUPPORTED = new MMErrorCode(1018);
	

	/**
	 * GUI Error Code Range: 2000 - 2999
	 */
	public static final MMErrorCode GUI_BASE_ERROR_CODE = new MMErrorCode(2000);
	
	public static final MMErrorCode PASSWORD_MISMATCH = new MMErrorCode(2001);

	/**
	 * Messaging Framework Error Code Range: 3000 - 3999
	 */
	public static final MMErrorCode MESSAGING_FRMWRK_BASE_ERROR_CODE = new MMErrorCode(3000);

	public static final MMErrorCode EMPTY_MESSAGE_RECIEVED = new MMErrorCode(3001);
	
	public static final MMErrorCode BEAN_CAST_EXCEPTION = new MMErrorCode(3002);
	
	public static final MMErrorCode BEAN_NOT_DEFINED = new MMErrorCode(3003);
	
	

	/**
	 * Communication Management Error Code Range: 4000 - 4999
	 */
	public static final MMErrorCode COMMUNICATION_MGMNT_BASE_ERROR_CODE = new MMErrorCode(4000);

	public static final MMErrorCode COMMUNICATION_MGMNT_ERROR_CODE_1 = new MMErrorCode(4001);

	/**
	 * Session Management Error Code Range: 5000 - 5999
	 */
	public static final MMErrorCode SESSION_MGMNT_BASE_ERROR_CODE = new MMErrorCode(5000);

	public static final MMErrorCode INVALID_SESSION_ID = new MMErrorCode(5001);

	public static final MMErrorCode UNAUTHORIZED_REQUEST = new MMErrorCode(5002);

	public static final MMErrorCode SESSION_ALREADY_EXISTS = new MMErrorCode(5003);

	public static final MMErrorCode SESSION_ALREADY_LOCKED = new MMErrorCode(5004);

	public static final MMErrorCode SESSION_ALREADY_UNLOCKED = new MMErrorCode(5005);

	public static final MMErrorCode AUTHENTICATION_FAILURE_USER_NOT_FOUND = new MMErrorCode(5006);
	
	public static final MMErrorCode AUTHENTICATION_FAILURE_USER_LOCKED = new MMErrorCode(5007);
	
	public static final MMErrorCode AUTHENTICATION_FAILURE_MAX_FAILURE_REACHED = new MMErrorCode(5008);

	public static final MMErrorCode AUTHENTICATION_FAILURE_PASSWORD_MISMATCH = new MMErrorCode(5009);
	/**
	 * Data Management Error Code Range: 6000 - 6999
	 */
	public static final MMErrorCode DATA_MGMNT_BASE_ERROR_CODE = new MMErrorCode(6000);

	public static final MMErrorCode DATA_MGMNT_DB_OPERATION_FAILED = new MMErrorCode(6001);

	/**
	 * Server Management Error Code Range: 7000 - 7999
	 */
	public static final MMErrorCode SERVER_MGMNT_BASE_ERROR_CODE = new MMErrorCode(7000);

	public static final MMErrorCode SERVER_MGMNT_ERROR_CODE_1 = new MMErrorCode(7001);
	
	public static final MMErrorCode SERVER_MGMNT_DU_FORMAT_INCORRECT = new MMErrorCode(7002);
	
	
	/**
	 * ASN.1 Error Code Range : 8000 - 8099
	 */
	public static final MMErrorCode ASN1_BASE_ERROR_CODE = new MMErrorCode(8000);

	public static final MMErrorCode ASN1_DEFINITION_ERROR_CODE = new MMErrorCode(8001);

	public static final MMErrorCode UNRESOLVED_ASN1_TYPE_EXCEPTION = new MMErrorCode(8002);

	/**
	 * DataUnit Error Code Range : 8100 - 8199
	 */
	public static final MMErrorCode DATAUNIT_BASE_ERROR_CODE = new MMErrorCode(8100);
	
	public static final MMErrorCode VALUE_OUT_OF_RANGE = new MMErrorCode(8101);

	public static final MMErrorCode DUVALUE_CONVERSION_ERROR_CODE = new MMErrorCode(8102);

	public static final MMErrorCode DUVALUE_ILLEGAL_ASSIGNMENT_ERROR_CODE = new MMErrorCode(8103);

	public static final MMErrorCode DUVALUE_NOT_FOUND_ERROR_CODE = new MMErrorCode(8104);

	public static final MMErrorCode DUVALUE_XML_ENCODING_ERROR_CODE = new MMErrorCode(8105);
	
	/**
	 * Configuration Error Code Range : 9100 - 9199
	 */
	
	public static final MMErrorCode CONFIGURATION_ERROR_CODE = new MMErrorCode(9100);
	
	/**
	 * Offline Configuration Error Code Range : 9200 - 9299 
	 */
	public static final MMErrorCode OFFLINE_ERROR_CODE = new MMErrorCode(9200);
	
	public static final MMErrorCode OFFLINE_ACTIVITY_LINK_INSTNTIATION_FAILURE = new MMErrorCode(9201);

	public static final MMErrorCode CONFIGURTION_PARSING_ERROR_CODE = new MMErrorCode(9202); 

	private final int myErrorCode;
	private final String myErrorDesc;

	private MMErrorCode(int aErrorCode) {
		this.myErrorCode = aErrorCode;
		this.myErrorDesc = rb.getString(String.valueOf(aErrorCode));
	}

	public int getErrorCode() {
		return this.myErrorCode;
	}

	public String getErrorDescription() {
		return this.myErrorDesc;
	}
}
