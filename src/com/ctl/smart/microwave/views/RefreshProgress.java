package com.ctl.smart.microwave.views;

import com.ctl.smart.microwave.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by SongNick on 15/9/8.
 */
public class RefreshProgress extends View {

	private static final String TAG = "RefreshProgress";

	private int bigCircleRadius = 100;

	private int smallCircleRadius = 10;

	private int bigCircleRadiusStrokeWidth = 8;

	private Bitmap cirCleBitmap = null;

	private int startColor = Color.parseColor("#00A8D7A7");

	private int endColor = Color.parseColor("#ffA8D7A7");

	private RectF rectF = null;

	private float startAngle = 0.0f;

	public RefreshProgress(Context context) {
		this(context, null);
	}

	public RefreshProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RefreshProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshProgress, 0, 0);

			bigCircleRadius = a.getInteger(R.styleable.RefreshProgress_bigCircleRadius, 100);

			smallCircleRadius = a.getInteger(R.styleable.RefreshProgress_smallCircleRadius, 10);

			bigCircleRadiusStrokeWidth = a.getInteger(R.styleable.RefreshProgress_bigCircleRadiusStrokeWidth, 8);

			Drawable d = a.getDrawable(R.styleable.RefreshProgress_cirCleBitmap);
			
			if (d!=null) {
				
				cirCleBitmap = ((BitmapDrawable) d).getBitmap();
				
			}

			startColor = a.getColor(R.styleable.RefreshProgress_startColor, Color.parseColor("#00A8D7A7"));

			endColor = a.getColor(R.styleable.RefreshProgress_startColor, Color.parseColor("#ffA8D7A7"));

			a.recycle();
		}

		rectF = new RectF(0, 0, bigCircleRadius, bigCircleRadius);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int defaultWidth = 0;
		int defaultHeight = 0;

		defaultWidth = getPaddingLeft() + getPaddingRight() + defaultWidth;
		defaultHeight = defaultHeight + getPaddingTop() + getPaddingBottom();
		switch (widthMode) {
		case MeasureSpec.EXACTLY:
			// the size is confirmed - match parent or dd
			break;
		case MeasureSpec.AT_MOST:
			// wrap_content
			width = bigCircleRadius * 2;
			break;
		case MeasureSpec.UNSPECIFIED:
			break;

		}

		switch (heightMode) {
		case MeasureSpec.EXACTLY:
			break;
		case MeasureSpec.AT_MOST:
			height = bigCircleRadius * 2;
			break;
		case MeasureSpec.UNSPECIFIED:

			break;

		}
		setMeasuredDimension(width, height);
		rectF.set(17, 17, width - 17, height - 17);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawAccProgressbar(startAngle, canvas);
		
		if (isRoate) {
			startAngle+=10;
			postInvalidateDelayed(5);
		}

	}

	private void drawAccProgressbar(float startAngle, Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(bigCircleRadiusStrokeWidth);
		paint.setStyle(Paint.Style.STROKE);
		int[] f = { startColor, endColor };
		float[] p = { .0f, 1.0f };
		SweepGradient sweepGradient = new SweepGradient(rectF.centerX(), rectF.centerX(), f, p);
		Matrix matrix = new Matrix();
		sweepGradient.getLocalMatrix(matrix);
		matrix.postRotate(startAngle, rectF.centerX(), rectF.centerY());
		sweepGradient.setLocalMatrix(matrix);
		paint.setShader(sweepGradient);

		canvas.drawArc(rectF, 0, 360, true, paint);

		if (cirCleBitmap != null) {

			int redius = (int) rectF.width() / 2 - smallCircleRadius;

			float leftB = rectF.centerX() - redius;

			float topB = rectF.centerY() - redius;

			canvas.drawBitmap(getCroppedRoundBitmap(cirCleBitmap, redius), leftB, topB, null);
		}

		int count = canvas.save();
		canvas.translate(rectF.centerX(), rectF.centerY());
		double sweepAngle = Math.PI / 180 * startAngle;
		Path path = new Path();
		float y = (float) Math.sin(sweepAngle) * (rectF.width() / 2);
		float x = (float) Math.cos(sweepAngle) * (rectF.width() / 2);
		// path.moveTo(rectF.centerX(), rectF.centerY());
		path.moveTo(x, y);
		path.addCircle(x, y, smallCircleRadius, Path.Direction.CCW);
		Paint circlePaint = new Paint();
		circlePaint.setAntiAlias(true);
		circlePaint.setColor(Color.WHITE);
		// circlePaint.setStrokeWidth(7);
		circlePaint.setStyle(Paint.Style.FILL);
		canvas.drawPath(path, circlePaint);
		canvas.restoreToCount(count);
	}

	private boolean isRoate = false;
	
	private void startRotate(long duration, boolean acc) {
		// clearAnimation();
//		LinearAnimation animation = new LinearAnimation();
//		animation.setDuration(duration);
//		animation.setRepeatCount(Integer.MAX_VALUE);
//		animation.setRepeatMode(Animation.INFINITE);
//		animation.setInterpolator(new LinearInterpolator());
//		animation.setLinearAnimationListener(new LinearAnimation.LinearAnimationListener() {
//			@Override
//			public void applyTans(float interpolatedTime) {
//				startAngle = 360 * interpolatedTime;
//				invalidate();
//			}
//		});
//		startAnimation(animation);
		isRoate=true;
		invalidate();
	}

	private void stopRotate() {
//		clearAnimation();
		isRoate=false;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopRotate();
	}

	/**
	 * 开始旋转
	 */
	public void start(){
		startRotate(2 * 1000, true);
	}
	/**
	 * 结束旋转
	 */
	public void stop(){
		stopRotate();
	}
	
	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布局文件XML看不到效果)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	/**
	 * 设置大圆的半径
	 * @param bigCircleRadius
	 */
	public void setBigCircleRadius(int bigCircleRadius) {
		this.bigCircleRadius = bigCircleRadius;
	}


	/**
	 * 设置小圆的半径
	 * @param bigCircleRadius
	 */
	public void setSmallCircleRadius(int smallCircleRadius) {
		this.smallCircleRadius = smallCircleRadius;
	}


	/**
	 * 设置大圆的线条宽度
	 * @param bigCircleRadius
	 */
	public void setBigCircleRadiusStrokeWidth(int bigCircleRadiusStrokeWidth) {
		this.bigCircleRadiusStrokeWidth = bigCircleRadiusStrokeWidth;
	}


	/**
	 * 设置圆内图片
	 * @param bigCircleRadius
	 */
	public void setCirCleBitmap(Bitmap cirCleBitmap) {
		this.cirCleBitmap = cirCleBitmap;
		invalidate();
	}


	/**
	 * 设置大圆的渐变开始颜色
	 * @param bigCircleRadius
	 */
	public void setStartColor(int startColor) {
		this.startColor = startColor;
	}

	

	/**
	 * 设置大圆的渐变结束颜色
	 * @param bigCircleRadius
	 */
	public void setEndColor(int endColor) {
		this.endColor = endColor;
	}
	
	
}
