package com.eyey.rippleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by zhengliao on 2017/5/18 0018.
 * Email:dantemustcry@126.com
 */

public class HexagonalRippleView extends View {
    Paint paint;
    Path path;


    private String DEFAULT_COLOR = "#00dcff";

    public HexagonalRippleView(Context context) {
        super(context);
    }


    public HexagonalRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public HexagonalRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    boolean isStart = false;

    private final static float MIN_SCALE = 0.4f;
    private final static float MAX_SCALE = 1.0f;
    private final static float INCREMENT_SCALE = 0.005f;


    private LinkedList<Float> scaleList = new LinkedList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha(255);
        drawHexagonal(canvas, paint, MIN_SCALE);
        if (scaleList.size() < 3) {
            scaleList.add(scaleList.get(scaleList.size() - 1) + 0.2f);
        }
        for (int i = 0; i < scaleList.size(); i++) {
            paint.setAlpha((int) (425 - 425 * scaleList.get(i)));
            drawHexagonal(canvas, paint, scaleList.get(i));
        }

        for (int i = 0; i < scaleList.size(); i++) {
            scaleList.set(i, scaleList.get(i) + INCREMENT_SCALE);

        }
        if (scaleList.get(scaleList.size() - 1) >= MAX_SCALE) {
            scaleList.remove(scaleList.size() - 1);
            scaleList.add(0, MIN_SCALE);
        }
        if (isStart) {
            invalidate();
        }
    }

    protected void drawHexagonal(Canvas canvas, Paint paint, float scale) {
        float sqrt3 = 1.7320508075689F;
        int radius = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2;
        int realRadius = (int) (radius * scale);
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        int startX = (w / 2 - realRadius);
        int startY = h / 2;
        float moveY = (sqrt3 * realRadius / 2);
        float moveX = realRadius / 2;
        path.reset();
        path.moveTo(startX, startY);
        path.lineTo(startX + moveX, startY + moveY);
        path.lineTo(startX + moveX + realRadius, startY + moveY);
        path.lineTo(startX + 2 * moveX + realRadius, startY);
        path.lineTo(startX + moveX + realRadius, startY - moveY);
        path.lineTo(startX + moveX, startY - moveY);
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HexagonalRippleView);
        int color = typedArray.getColor(R.styleable.HexagonalRippleView_hexColor, -1);
        int colorRe = typedArray.getResourceId(R.styleable.HexagonalRippleView_hexColor, -1);
        if (color != -1) {
            paint.setColor(color);
        } else if (colorRe != -1) {
            paint.setColor(ContextCompat.getColor(context, colorRe));
        } else {
            paint.setColor(Color.parseColor(DEFAULT_COLOR));
        }
        typedArray.recycle();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        scaleList.add(MIN_SCALE);
        path = new Path();
    }

    public void start() {
        isStart = true;
        invalidate();
    }

    public boolean isStart() {
        return isStart;
    }


    public void stop() {
        isStart = false;
        invalidate();
    }
}
