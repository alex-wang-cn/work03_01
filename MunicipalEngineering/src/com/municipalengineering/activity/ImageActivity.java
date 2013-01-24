package com.municipalengineering.activity;

import com.municipalengineering.activity.R.drawable;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageActivity extends Activity {
	ImageView imageView;
	public final static String IMAGE_TAG = "iamge key";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_image);
		Display ds = getWindowManager().getDefaultDisplay();
		String filePath = getIntent().getExtras().getString(IMAGE_TAG);
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		if (bm != null) {
			float rate = (float) bm.getWidth() / bm.getHeight();
			bm = ThumbnailUtils.extractThumbnail(bm, (int) (ds.getHeight()*rate), ds.getHeight());

		}
		imageView = (ImageView) this.findViewById(R.id.imageView);
		imageView.setImageBitmap(bm);
		imageView.setOnTouchListener(new ImageTouchListener());
	}

	private class ImageTouchListener implements OnTouchListener {
		private PointF startPoint;
		private Matrix matrix = new Matrix();
		private Matrix currentMatrix = new Matrix();
		private float startDistance;
		private static final int DRAG = 1;
		private static final int ZOOM = 2;
		private int mode;
		private PointF midPoint;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// 01000000 01000100 01000010 01001000
			// & 00000000 00000000 00000000 11111111
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:// 按下屏幕
				mode = DRAG;
				startPoint = new PointF(event.getX(), event.getY());
				currentMatrix.set(imageView.getImageMatrix());
				break;

			case MotionEvent.ACTION_MOVE:// 按下屏幕拖动
				switch (mode) {
				case DRAG:
					float dx = event.getX() - startPoint.x;
					float dy = event.getY() - startPoint.y;
					matrix.set(currentMatrix);
					matrix.postTranslate(dx, dy);
					break;

				case ZOOM:
					float enddistance = distance(event);
					float scale = enddistance / startDistance;
					matrix.set(currentMatrix);
					matrix.postScale(scale, scale, midPoint.x, midPoint.y);
					break;
				}
				break;

			case MotionEvent.ACTION_POINTER_DOWN:// 已经有一个手指按住屏幕，再有一个手指按下屏幕就会触发该事件
				mode = ZOOM;
				startDistance = distance(event);
				if (startDistance > 10) {
					midPoint = mid(event);
					currentMatrix.set(imageView.getImageMatrix());// 得到进行缩放操作之前，照片的缩放倍数
				}
				break;

			case MotionEvent.ACTION_POINTER_UP:// 有一个手指离开屏幕，但还有手指在屏幕就会触发该事件
			case MotionEvent.ACTION_UP:// 最后一个手指离开屏幕，就会触发该事件
				mode = 0;
				break;
			}
			imageView.setImageMatrix(matrix);
			return true;
		}
	}

	/**
	 * 计算两点之间的距离
	 * 
	 * @param event
	 * @return
	 */
	public static float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		return FloatMath.sqrt(dx * dx + dy * dy);
	}

	/**
	 * 得到两点之间的中间点坐标
	 * 
	 * @param event
	 * @return
	 */
	public static PointF mid(MotionEvent event) {
		float x = (event.getX(1) + event.getX(0)) / 2;
		float y = (event.getY(1) + event.getY(0)) / 2;
		return new PointF(x, y);
	}
}