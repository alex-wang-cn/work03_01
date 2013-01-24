package com.municipalengineering.activity;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.municipalengineering.adapter.HandleAdapter;
import com.municipalengineering.fragment.MainGridFragment;
import com.municipalengineering.fragment.MainGridFragment.MainGridItemClickLisner;
import com.municipalengineering.view.HomeView;
import com.municipalengineering.view.TendersInspectionView;
import com.municipalengineering.view.ViewDirector;
import com.tools.InitIconBeans;
import com.wang.wdiget.Panel;
import com.wang.wdiget.Panel.OnPanelListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class MasterActivity extends FragmentActivity implements
		MainGridItemClickLisner {

	private Panel mPanel;
	private GridView mPanelContent;
	private RelativeLayout mParenLayout;
	private HandleAdapter mHandlerAdapter;
	private ImageView mProjectPhoto;
	private ImageView mCMMPhoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		mPanel = (Panel) findViewById(R.id.topPanel);
		mPanelContent = (GridView) findViewById(R.id.panelContent);
		mParenLayout = (RelativeLayout) findViewById(R.id.main_content);
		mHandlerAdapter = new HandleAdapter(this, mPanelContent.getId(),
				InitIconBeans.getInstance().getHanderIcons());
		mPanelContent.setAdapter(mHandlerAdapter);

		mPanelContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				changeContentFragment(mHandlerAdapter.getItem(position)
						.getClassName());
			}
		});

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_content, new MainGridFragment()).commit();

	}

	protected void changeContentFragment(String className) {
		Fragment fragment = null;
		try {
			fragment = (Fragment) Class.forName(className).getConstructor()
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_content, fragment).commit();
		getSupportFragmentManager().beginTransaction().addToBackStack(null);
	}

	public static class ViewTag {
		View converview;
		Button converButton;
	}

	private String mFilePath;
	public final int TAKE_HONTO = 1;

	public void showBigImage(View view) {
		if (mFilePath != null && !mFilePath.equals("")) {
			Intent intent = new Intent(this, ImageActivity.class);
			intent.putExtra(ImageActivity.IMAGE_TAG, mFilePath);
			startActivity(intent);
		}
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == ProjectPhoto || requestCode == CMMPPhoto) {
				Bitmap bm = BitmapFactory.decodeFile(mPaths.get(requestCode));
				if (bm != null) {
					bm = ThumbnailUtils.extractThumbnail(bm, 150, 100);
					if (requestCode == ProjectPhoto) {
						mProjectPhoto.setImageBitmap(bm);
					} else {
						mCMMPhoto.setImageBitmap(bm);
					}

				}
			}
		} else if (requestCode == ProjectPhoto || requestCode == CMMPPhoto) {
			mPaths.remove(requestCode);
		}
	}

	private int ProjectPhoto = 1;
	private int CMMPPhoto = 2;
	private HashMap<Integer, String> mPaths = new HashMap<Integer, String>();

	private void onClickStartCamer(final int reusltCode) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStorageDirectory(),
				"myPhoto" + reusltCode + ".jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		mPaths.put(reusltCode, photo.getAbsolutePath());
		startActivityForResult(intent, reusltCode);
	}

	public void onTakeProjectPhoto(View v) {
		if (mProjectPhoto == null) {
			mProjectPhoto = (ImageView) findViewById(R.id.tenders_show_project_photo);
		}
		onClickStartCamer(ProjectPhoto);

	}

	public void onTakeCCMPhoto(View v) {
		if (mCMMPhoto == null) {
			mCMMPhoto = (ImageView) findViewById(R.id.tenders_show_ccm_photo);
		}
		onClickStartCamer(CMMPPhoto);
	}

	@Override
	public void onItemSelect(int position) {
		changeContentFragment(InitIconBeans.getInstance().getHomeIcons()
				.get(position).getClassName());

	}

}
