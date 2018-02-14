package com.mbean;

public class Thermometer implements ThermometerMBean {

	private String message = null;
        
            private String[] mMessage = null;

	   public Thermometer() {
	      message = "Hello there";
              mMessage = new String[]{"aa", "bb", "cc"};
	   }

	   public Thermometer(String message) {
	      this.message = message;
	   }

	   public void setMessage(String message) {
	      this.message = message;
	   }

	   public String getMessage() {
	      return message;
	   }

	   public void sayHello() {
	      System.out.println(message);
	   }

    @Override
    public String[] getMMessage() {
return mMessage;
    }
}
