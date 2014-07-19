package com.example.musicapp.uimodules;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicmusicapp.network.APIManager;
import com.example.basicmusicapp.network.RestException;
import com.example.basicmusicapp.network.APIManager.APIListener;
import com.example.basicmusicapp.pojo.ArtistInfo;
import com.example.basicmusicapp.pojo.FetchArtistResponse;
import com.example.musicapp.R;
import com.example.musicapp.R.id;
import com.example.musicapp.R.layout;

public class FragmentTab extends Fragment {

	public static final String KEY_ARTIST_NAME = "artistName";
	private String artistName;
	private FetchArtistResponse fetchArtistResponse;
	private ProgressBar progressBar;
	private TextView textView;

	APIListener apiListener = new APIListener() {

		@Override
		public void onServiceEnd(Object response, int taskCode,
				Object... params) {

			switch (taskCode) {
			case APIManager.FETCH_ARTISTS:
				if (getActivity() != null && !isDetached()) {

					progressBar.setVisibility(View.GONE);
					if (response instanceof FetchArtistResponse) {

						artistInfoFetched((FetchArtistResponse) response);

					} else {

						Toast.makeText(
								getActivity().getApplicationContext(),
								"There is some technical error please try again",
								Toast.LENGTH_SHORT).show();

					}
				}

				break;

			default:
				break;
			}

		}

		@Override
		public void onServiceBegin(int taskCode) {
			switch (taskCode) {
			case APIManager.FETCH_ARTISTS:
				progressBar.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}

		}

		@Override
		public void onError(RestException re, Exception e, int taskCode,
				Object... params) {
			switch (taskCode) {
			case APIManager.FETCH_ARTISTS:
				if (progressBar != null && getActivity() != null) {
					progressBar.setVisibility(8);
					Toast.makeText(getActivity().getApplicationContext(),
							"There is some technical error please try again", 0)
							.show();
				}
				break;

			default:
				break;
			}

		}
	};

	protected void artistInfoFetched(FetchArtistResponse fetchartistresponse) {
		this.fetchArtistResponse = fetchartistresponse;
		setViewWithArtistinfo((ArtistInfo) fetchartistresponse.artistList
				.get(0));
	}

	public void fetchData(String artistName) {
		this.artistName = artistName;
		APIManager apimanager = new APIManager(getActivity()
				.getApplicationContext(), apiListener, APIManager.FETCH_ARTISTS);
		apimanager.execute(artistName, 1);
		Log.i("fragment", "fetchdata");
	}

	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		Log.i("fragment", "onActivityCreated");
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i("fragment", (new StringBuilder("onAttach")).append(artistName)
				.toString());
	}

	public void onCreate(Bundle bundle) {
		Log.i("fragment", (new StringBuilder("onCreate:")).append(artistName)
				.toString());
		super.onCreate(bundle);
	}

	public View onCreateView(LayoutInflater layoutinflater,
			ViewGroup viewgroup, Bundle bundle) {
		View view = layoutinflater.inflate(R.layout.fragment_tab, viewgroup,
				false);
		textView = (TextView) view.findViewById(R.id.artistInfo);
		progressBar = (ProgressBar) view.findViewById(R.id.fragmentProgressBar);
		progressBar.setVisibility(View.GONE);
		return view;
	}


	public void onPause() {
		super.onPause();
		Log.i("fragment", (new StringBuilder("onPause")).append(artistName)
				.toString());
	}

	public void onResume() {
		super.onResume();
		Log.i("fragment", (new StringBuilder("onResume()")).append(artistName)
				.toString());
	}

	public void onStart() {
		super.onStart();
		Log.i("fragment", (new StringBuilder("onStart")).append(artistName)
				.toString());
	}

	public void onStop() {
		super.onStop();
		Log.i("fragment", (new StringBuilder("onStop")).append(artistName)
				.toString());
	}

	public void onViewCreated(View view, Bundle bundle) {
		Bundle bundleInfo = getArguments();
		if (bundleInfo != null) {
			artistName = bundleInfo.getString(KEY_ARTIST_NAME);
			viewDataIfAvail();
		} else {
			viewDataIfAvail();
		}
	}

	private void viewDataIfAvail() {

		{
			if (!TextUtils.isEmpty(this.artistName)) {
				if (this.fetchArtistResponse != null) {
					setViewWithArtistinfo((ArtistInfo) fetchArtistResponse.artistList
							.get(0));
				} else {
				fetchData(this.artistName);
				}

			}

		}

	}

	public void setViewWithArtistinfo(ArtistInfo artistinfo) {
		if (artistinfo != null) {
			textView.setText((new StringBuilder("ArtistName ::> "))
					.append(artistinfo.artistName).append("\n TrackName::>")
					.append(artistinfo.trackName).toString());
		}
	}
	public void onDestroyView() {
		super.onDestroyView();
		progressBar = null;
		apiListener = null;
		textView = null;
	}


}
