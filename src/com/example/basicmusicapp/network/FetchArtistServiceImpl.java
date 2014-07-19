package com.example.basicmusicapp.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.basicmusicapp.pojo.ArtistInfo;
import com.example.basicmusicapp.pojo.FetchArtistResponse;


public class FetchArtistServiceImpl extends ApiService {

	public static final String SEARCH_ARTIST_API_URL = "http://itunes.apple.com/search?term=%s&limit=%s";

	@Override
	public Object getData(Object[] object) throws JSONException, RestException {

		if (object != null && object.length == 2) {
			String name = "";
			try {
				name = URLEncoder.encode(object[0].toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RestException(100, "Invalid Name of artist");

			}

			String url = String.format(SEARCH_ARTIST_API_URL, name,
					object[1].toString());
			NetworkResponse networkResponse = doGet(url);
			if (networkResponse.isSuccess) {

				if (networkResponse.responseCode == 200) {

					JSONObject jsonobject = new JSONObject(
							networkResponse.getResponseData());
					int resultCnt = jsonobject.getInt("resultCount");
					ArrayList<ArtistInfo> artistList = new ArrayList<ArtistInfo>(
							resultCnt);
					JSONArray jsonarray = jsonobject.getJSONArray("results");

					for (int i = 0; i < resultCnt; i++) {
						ArtistInfo artistinfo = new ArtistInfo();
						JSONObject jsonobject1 = jsonarray.getJSONObject(i);
						artistinfo.artistName = jsonobject1
								.getString("artistName");
						artistinfo.trackName = jsonobject1
								.getString("trackName");
						artistList.add(artistinfo);

					}
					FetchArtistResponse fetchArtistResponse = new FetchArtistResponse();
					fetchArtistResponse.artistList = artistList;
					return fetchArtistResponse;
				} else {

					throw new RestException("There is some technical error please try again");
				}

			}

		}
			
			throw new RestException("Invalid Params");
		
	}
}
