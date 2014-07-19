package com.example.basicmusicapp.network;

import android.content.Context;
import android.os.AsyncTask;

public class APIManager extends AsyncTask<Object, Object, Object> {

	public interface APIListener {

		public void onServiceBegin(int taskCode);

		public void onServiceEnd(Object response, int taskCode,
				Object... params);

		public void onError(RestException re, Exception e, int taskCode,
				Object... params);

	}

	public static final int FETCH_ARTISTS = 1;
	public static final String TAG = "Asyntask";
	private APIListener apiServiceListener;
	private Context context;
	private Exception e;
	private Object params[];
	private int taskCode;

	public APIManager(Context context, APIListener apiListener, int taskCode) {
		this.apiServiceListener = apiListener;
		this.context = context;
		this.taskCode = taskCode;

	}

	@Override
	protected Object doInBackground(Object... params) {
		Object response = null;
		ApiService service = ServiceFactory.getInstance(context, taskCode);

		try {
			this.params = params;
			response = service.getData(params);
		} catch (Exception e) {
			e.printStackTrace();
			this.e = e;
		}

		return response;
	}

	public int getCurrentTaskCode() {
		return taskCode;
	}

	protected void onPostExecute(Object obj) {
		super.onPostExecute(obj);
		if (context != null && apiServiceListener != null) {
			if (e != null) {
				if (e instanceof RestException) {
					apiServiceListener.onError((RestException) e, null,
							taskCode, params);
				} else {
					apiServiceListener.onError(null, e, taskCode, params);
				}
			} else {
				apiServiceListener.onServiceEnd(obj, taskCode, params);
			}
		}

	}

	protected void onPreExecute() {
		super.onPreExecute();
		if (apiServiceListener != null) {
			apiServiceListener.onServiceBegin(taskCode);
		}

	}
}
