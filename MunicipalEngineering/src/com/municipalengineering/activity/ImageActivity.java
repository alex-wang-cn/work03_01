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
			case MotionEvent.ACTION_DOWN:// ������Ļ
				mode = DRAG;
				startPoint = new PointF(event.getX(), event.getY());
				currentMatrix.set(imageView.getImageMatrix());
				break;

			case MotionEvent.ACTION_MOVE:// ������Ļ�϶�
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

			case MotionEvent.ACTION_POINTER_DOWN:// �Ѿ���һ����ָ��ס��Ļ������һ����ָ������Ļ�ͻᴥ�����¼�
				mode = ZOOM;
				startDistance = distance(event);
				if (startDistance > 10) {
					midPoint = mid(event);
					currentMatrix.set(imageView.getImageMatrix());// �õ��������Ų���֮ǰ����Ƭ�����ű���
				}
				break;

			case MotionEvent.ACTION_POINTER_UP:// ��һ����ָ�뿪��Ļ����������ָ����Ļ�ͻᴥ�����¼�
			case MotionEvent.ACTION_UP:// ���һ����ָ�뿪��Ļ���ͻᴥ�����¼�
				mode = 0;
				break;
			}
			imageView.setImageMatrix(matrix);
			return true;
		}
	}

	/**
	 * ��������֮��ľ���
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
	 * �õ�����֮����м������
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