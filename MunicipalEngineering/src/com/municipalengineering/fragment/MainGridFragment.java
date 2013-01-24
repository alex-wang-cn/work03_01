package com.municipalengineering.fragment;

import com.municipalengineering.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainGridFragment extends Fragment {
	public interface MainGridItemClickLisner {
		void onItemSelect(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.home_grid_view, container, false);
	}
    
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

}
