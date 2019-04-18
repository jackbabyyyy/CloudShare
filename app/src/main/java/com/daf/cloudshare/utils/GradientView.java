package com.daf.cloudshare.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.daf.cloudshare.R;

/**
 * Created by PP on 2019/3/27.
 */
public class GradientView extends View {

    private int start=Color.parseColor("#e33721"), end=Color.parseColor("#df0010");

    public GradientView(Context context) {
        super(context);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int colorStart = start;
        int colorEnd = end;


        Paint paint = new Paint();
        LinearGradient backGradient = new LinearGradient(0, 0, 0, height, colorStart ,colorEnd, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);


    }

    public void setColor(){
        start=Color.parseColor("#ff0000");
        end=Color.parseColor("#0000ff");
        invalidate();

    }




}
