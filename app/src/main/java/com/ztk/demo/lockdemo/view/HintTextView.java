package com.ztk.demo.lockdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author ztk
 */
public class HintTextView extends AppCompatTextView {

    private Paint paint;
    
    private int mWidth;
    private LinearGradient gradient;
    private Matrix matrix;
    /**
     * 渐变的速度
     */
    private int deltaX;

    public HintTextView(Context context) {
        super(context, null);
    }

    public HintTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = getPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mWidth == 0 ){
            mWidth = getMeasuredWidth();
            //颜色渐变器
            gradient = new LinearGradient(0, 0, mWidth, 0, new int[]{Color.GRAY, Color.WHITE, Color.GRAY}, new float[]{
                    0.3f,0.5f,1.0f
            }, Shader.TileMode.CLAMP);
            paint.setShader(gradient);

            matrix = new Matrix();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(matrix !=null){
            deltaX += mWidth / 8;
            if(deltaX > 2 * mWidth){
                deltaX = -mWidth;
            }
        }
        //通过矩阵的平移实现
        matrix.setTranslate(deltaX, 0);
        gradient.setLocalMatrix(matrix);
        postInvalidateDelayed(100);
    }
}