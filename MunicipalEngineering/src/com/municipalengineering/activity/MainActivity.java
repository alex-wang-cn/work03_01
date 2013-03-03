package com.municipalengineering.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.municipalengineering.adapter.HandleAdapter;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.view.HomeView;
import com.municipalengineering.view.TendersInspectionView;
import com.municipalengineering.view.ViewDirector;
import com.tools.InitIconBeans;
import com.wang.wdiget.Panel;
import com.wang.wdiget.Panel.OnPanelListener;

public class MainActivity extends Activity implements OnPanelListener {

	private Panel mPanel;
	private GridView mPanelContent;
	private RelativeLayout mParenLayout;
	private HandleAdapter mHandlerAdapter;
	private ImageView mProjectPhoto;
	private ImageView mCMMPhoto;
	private ViewDirector viDirector;

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

		viDirector = ViewDirector.getInstance(mParenLayout);
		viDirector.clearView();
		viDirector.changeView(HomeView.class.getName());

		mPanelContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				viDirector.changeView(mHandlerAdapter.getItem(position)
						.getClassName());
			}
		});

		mPanel.setOnPanelListener(this);

	}

	@Override
	public void onPanelClosed(Panel panel) {

	}

	@Override
	public void onPanelOpened(Panel panel) {

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
		if (!viDirector.viewBack()) {
			viDirector.clearView();
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addCategory(Intent.CATEGORY_HOME);
			startActivity(i);
		}
	}

	public void upLoadData(View view) {
		((TendersInspectionView) viDirector.getCurrentView()).upLoad(mPaths);
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

}
