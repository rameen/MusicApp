package com.example.basicmusicapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

public abstract class ApiService {


	public NetworkResponse doGet(String url) {

		URL URLObject = null;
		NetworkResponse networkResponse = null;
		BufferedReader inputBUfferReader = null;
		try {
			URLObject = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) URLObject
					.openConnection();
			networkResponse = new NetworkResponse();
			networkResponse.setResponseCode(connection.getResponseCode());

			inputBUfferReader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = inputBUfferReader.readLine()) != null) {
				response.append(inputLine);
			}
			inputBUfferReader.close();
			networkResponse.setResponseData(response.toString());
			networkResponse.setSuccess(true);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			if (networkResponse == null) {
				networkResponse = new NetworkResponse();
			}
			networkResponse.setSuccess(false);
		} catch (IOException e) {
			e.printStackTrace();
			if (networkResponse == null) {
				networkResponse = new NetworkResponse();
			}
			networkResponse.setSuccess(false);
		}

		return networkResponse;

	}

	public abstract Object getData(Object object[]) throws JSONException,
			RestException;
}
