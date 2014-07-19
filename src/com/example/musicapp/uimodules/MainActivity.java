package com.example.musicapp.uimodules;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.example.musicapp.R.menu;
import com.example.musicapp.uimodules.SearchArtistFormDialogFragment.OnFormFilledCorrectlyListener;

public class MainActivity extends FragmentActivity implements OnClickListener {

	public static final int MAX_TABS = 4;
	private FragmentTabHost mTabHost;
	private ProgressBar progressBar;
	private Button searchArtistButton;
	int totalTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchArtistButton = (Button) findViewById(R.id.searchBtn);
		searchArtistButton.setOnClickListener(this);
		progressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
		progressBar.setVisibility(View.GONE);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab0").setIndicator("Tab 0"),
				FragmentTab.class, null);
		mTabHost.setVisibility(View.GONE);

	}

	SearchArtistFormDialogFragment.OnFormFilledCorrectlyListener formFilledCorrectlyListener = new OnFormFilledCorrectlyListener() {

		@Override
		public void onFormFilled(String artistName, String limit) {
			executeRequestArtistSearch(artistName, limit);

		}
	};

	private void executeRequestArtistSearch(String artistName, String limit) {
		APIManager apiManager = new APIManager(getApplicationContext(),
				apiListener, APIManager.FETCH_ARTISTS);
		apiManager.execute(artistName, limit);
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.searchBtn:
			searchArtistButtonClicked();
			break;

		default:
			break;
		}

	}

	private void searchArtistButtonClicked() {

		SearchArtistFormDialogFragment searchFormFragmentDialog = new SearchArtistFormDialogFragment();

		searchFormFragmentDialog
				.setOnFormFilledCorrectlyListener(formFilledCorrectlyListener);
		searchFormFragmentDialog.show(getSupportFragmentManager(),
				"search_form_fragment");
	}

	APIListener apiListener = new APIListener() {

		@Override
		public void onServiceEnd(Object response, int taskCode,
				Object... params) {

			switch (taskCode) {
			case APIManager.FETCH_ARTISTS:
				progressBar.setVisibility(View.GONE);
				if (response instanceof FetchArtistResponse) {
					artistFetchedSuccessfully((FetchArtistResponse) response);
				} else {
					Toast.makeText(getApplicationContext(),
							"There is some technical error please try again",
							Toast.LENGTH_SHORT).show();
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
				progressBar.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(),
						"There is some technical error please try again",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}

		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void artistFetchedSuccessfully(
			FetchArtistResponse fetchartistresponse) {
		ArrayList<ArtistInfo> arraylist = fetchartistresponse.artistList;
		int tabcount = 0;
		if (arraylist != null) {
			tabcount = arraylist.size();
		}
		if (tabcount == 0) {
			Toast.makeText(getApplicationContext(), "No Artists",
					Toast.LENGTH_SHORT).show();
			mTabHost.setVisibility(View.GONE);
			return;
		}
		if (tabcount < 4) {
			totalTab = tabcount;
		}
		((TextView) mTabHost.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title))
				.setText(((ArtistInfo) arraylist.get(0)).artistName);
		mTabHost.setVisibility(View.VISIBLE);
		searchArtistButton.setVisibility(View.GONE);
		int j = 1;

		for (int i = 1; i < totalTab; i++) {

			Bundle bundle = new Bundle();
			bundle.putString(FragmentTab.KEY_ARTIST_NAME,
					((ArtistInfo) arraylist.get(j)).artistName);
			mTabHost.addTab(
					mTabHost.newTabSpec(
							(new StringBuilder("tab")).append(j).toString())
							.setIndicator(
									((ArtistInfo) arraylist.get(j)).artistName), FragmentTab.class, bundle);

		}

		((FragmentTab) getSupportFragmentManager().findFragmentByTag("tab0"))
				.fetchData(((ArtistInfo) arraylist.get(0)).artistName);

	}

	protected void onDestroy() {
		super.onDestroy();
		mTabHost = null;
		searchArtistButton.setOnClickListener(null);
		searchArtistButton = null;
		progressBar = null;
		apiListener = null;
		formFilledCorrectlyListener = null;
	}
}
