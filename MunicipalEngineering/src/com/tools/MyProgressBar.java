package com.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar {
	String text = "������..";
	Paint mPaint;

	public MyProgressBar(Context context) {
		super(context);
		System.out.println("1");
		initText();
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("2");
		initText();
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("3");
		initText();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.text, x, y, this.mPaint);
	}

	private void initText() {
		this.mPaint = new Paint();
		this.mPaint.setColor(Color.BLACK);
		this.mPaint.setTextSize(18);
		this.mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

	}

	private void setText(int progress) {
		int i = (progress * 100) / this.getMax();
		this.text = String.valueOf(i) + "%";
	}

	public void setText(String text) {
		this.text = text;
	}

}
