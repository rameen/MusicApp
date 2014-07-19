package com.example.basicmusicapp.network;

import java.util.Map;


public class NetworkResponse
{

	public boolean isSuccess;
	public String response;
	public int responseCode;
	public void setResponseCode(int responseCode) {

		this.responseCode = responseCode;
	}
	public void setSuccess(boolean b) {
		this.isSuccess = b;
		
	}
	public void setResponseData(String response) {
		
		this.response = response;
		
	}
	public String getResponseData() {
		// TODO Auto-generated method stub
		return  this.response;
	}
	

   
}
