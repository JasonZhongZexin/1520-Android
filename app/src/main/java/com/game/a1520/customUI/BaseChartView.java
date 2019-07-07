package com.game.a1520.customUI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public abstract class BaseChartView extends View {
    private Context context;
    public BaseChartView(Context context) {
        super(context);
        this.context = context;
    }

    public BaseChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
    }

    public BaseChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;
    }

    /** return the max value **/
    protected double getMaxArray(ArrayList<Double> array) {
        if (array.size() == 0)
            return 0;
        double max = array.get(0);
        for (double i : array) {
            max = max > i ? max : i;
        }
        return max;
    }

    /** return the min value **/
    protected double getMinArray(ArrayList<Double> array) {
        if (array.size() == 0)
            return 0;
        double min = array.get(0);
        for (double i : array) {
            min = min < i ? min : i;
        }
        return min;
    }

    protected int px2sp(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    protected int sp2px( float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * convert dp to px base on the screen pixel
     * @param dpValue
     * @return
     */
    protected int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
