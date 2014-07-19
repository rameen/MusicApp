package com.example.musicapp.uimodules;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SearchArtistFormDialogFragment extends DialogFragment implements
		android.view.View.OnClickListener {
	public static interface OnFormFilledCorrectlyListener {

		public abstract void onFormFilled(String s, String s1);
	}

	private static final String ARTIST_VALIDATION = "jack";
	private EditText artistNameEt;
	private TextView errorView;
	private OnFormFilledCorrectlyListener formFilledCorrectlyListener;
	private EditText limitEt;

	public SearchArtistFormDialogFragment() {
	}

	public void onClick(View view) {
		String s = artistNameEt.getText().toString().toLowerCase().trim();
		String s1 = limitEt.getText().toString().trim();
		int limit = 100;
		if (!TextUtils.isEmpty(s1)) {
			limit = Integer.parseInt(s1);
		}

		if (ARTIST_VALIDATION.equals(s) && limit > 0 && limit <= 4) {
			errorView.setText("");
			if (formFilledCorrectlyListener != null) {
				formFilledCorrectlyListener.onFormFilled(s, s1);
				dismiss();
			}
			return;
		} else {
			errorView
					.setText("Please fill artist as \"jack\" and limit  from  \"1\" to \"4\"");
			errorView.setTextColor(Color.RED);
			return;
		}
	}

	public View onCreateView(LayoutInflater layoutinflater,
			ViewGroup viewgroup, Bundle bundle) {
		View view = layoutinflater.inflate(0x7f03001a, viewgroup);
		view.findViewById(0x7f050044).setOnClickListener(this);
		artistNameEt = (EditText) view.findViewById(0x7f050041);
		limitEt = (EditText) view.findViewById(0x7f050042);
		errorView = (TextView) view.findViewById(0x7f050043);
		return view;
	}

	public void onDestroyView() {
		super.onDestroyView();
		artistNameEt = null;
		limitEt = null;
		errorView = null;
		formFilledCorrectlyListener = null;
	}

	public void setOnFormFilledCorrectlyListener(
			OnFormFilledCorrectlyListener onformfilledcorrectlylistener) {
		formFilledCorrectlyListener = onformfilledcorrectlylistener;
	}
}
