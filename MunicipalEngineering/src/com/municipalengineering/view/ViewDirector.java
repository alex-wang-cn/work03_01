package com.municipalengineering.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import com.municipalengineering.exception.ViewDirectorInitException;
import com.municipalengineering.myinterface.IMyView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/*
 * alex.wang 
 */
public class ViewDirector {
	private static RelativeLayout mRootView;

	private HashMap<String, IMyView> mConverViews = new HashMap<String, IMyView>();
	private IMyView mLastIMyView = null;
	private static ViewDirector mInstance;
	public final static String Tag = "ViewDirector";

	private ViewDirector(RelativeLayout rootView) {
		this.mRootView = rootView;
	}

	public static ViewDirector getInstance() {
		if (mInstance == null) {
			throw new ViewDirectorInitException("ViewDirector  didn't init");
		}
		return mInstance;
	}

	public static ViewDirector getInstance(RelativeLayout rootView) {
		mInstance = new ViewDirector(rootView);
		return mInstance;
	}

	//
	public void changeView(String className) {
		if (mConverViews.get(className) == null) {
			createViewByName(className);
		}

		View v = mConverViews.get(className).getMyView();
		if (mRootView.getChildAt(0) != null) {
			if (mRootView.getChildAt(0).equals(v))
				return;
			mRootView.removeViewAt(0);
			mLastIMyView.onPause();
		}
		mLastIMyView = mConverViews.get(className).reusme();
		mRootView.addView(v, 0);
	}

	private void createViewByName(String className) {
		try {
			IMyView myIView = (IMyView) Class.forName(className)
					.getConstructor(Context.class)
					.newInstance(mRootView.getContext());
			mConverViews.put(className, myIView);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean viewBack() {
		if (mLastIMyView.getClass().getName().equals(HomeView.class.getName()))
			return false;
        changeView(HomeView.class.getName());
		return true;
	}

	public void clearView() {

		mConverViews.clear();
	}

	public IMyView getCurrentView() {
		return mLastIMyView;
	}
}