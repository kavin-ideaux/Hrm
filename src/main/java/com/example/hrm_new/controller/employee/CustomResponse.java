package com.example.hrm_new.controller.employee;

public class CustomResponse {
	
	 private boolean status;
	    private Object data;
	    
	    

		public boolean isStatus() {
			return status;
		}



		public void setStatus(boolean status) {
			this.status = status;
		}



		public Object getData() {
			return data;
		}



		public void setData(Object data) {
			this.data = data;
		}



		public CustomResponse(boolean status, Object data) {
	        this.status = status;
	        this.data = data;
	    }

}