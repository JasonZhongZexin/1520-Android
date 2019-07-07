package com.game.a1520.customUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.game.a1520.customUI.BaseChartView;

import java.util.ArrayList;
import java.util.List;

public class BarChartView extends BaseChartView {
    private List<Double> data_total = new ArrayList<Double>();
    private List<String> data_title = new ArrayList<String>();
    private float margin;

    private Paint paint;
    private float total_y = 0;
    private float scale;
    private float scaleWidth;
    private int width;
    private int textSize;
    private Context context;
    private Bitmap bitmap;

    public BarChartView(Context context, ArrayList<Double> data_total,
                        ArrayList<String> data_title) {
        super(context);
        this.context = context;
        width = getResources().getDisplayMetrics().widthPixels;
        this.data_total = data_total;
        this.data_title = data_title;
        total_y = (float) (getMaxArray(data_total) * 1.2);
        scale = width / (3 * total_y);
        scaleWidth = width * 9 / (20 * data_total.size() - 10);
        paint = new Paint();
        paint.setAntiAlias(true);
        margin = scaleWidth;
        textSize = sp2px( 20);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        width = getResources().getDisplayMetrics().widthPixels;
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setData(ArrayList<Double> data_total,
                        ArrayList<String> data_title) {
        this.data_total = data_total;
        this.data_title = data_title;
        total_y = (float) (getMaxArray(data_total) * 1.2);
        scale = width / (3 * total_y);
        scaleWidth = width * 9 / (20 * data_total.size() - 10);
        margin = scaleWidth;
        textSize = sp2px(20);
        invalidate();
    }

    public void drawAxis(Canvas canvas) {
        paint.setStrokeWidth(1);
        PathEffect effects = new DashPathEffect(new float[] { width / 60,
                width / 100, width / 50, width / 100 }, 1);
        paint.setPathEffect(effects);
        paint.setColor(Color.parseColor("#FFD1D1D1"));
        canvas.drawLine(width / 36, scale * total_y + 0.5f, width * 31 / 32,
                scale * total_y + 0.5f, paint);

        int x = (int) width / 20;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        paint.setTextSize(textSize);
        paint.setColor(Color.parseColor("#00000000"));
        for (int i = 0; i < data_title.size(); i++) {
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(textSize);
            textPaint.setStrokeWidth(2);
            textPaint.setTextAlign(Paint.Align.CENTER);
            String aboutTheGame = data_title.get(i);
            StaticLayout layout = new StaticLayout(aboutTheGame, textPaint,
                    (int) margin * 5 / 4, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F,
                    true);
            Rect targetRect = new Rect((int) (x - margin / 4), (int) (scale
                    * total_y + 5), (int) (x + margin * 5 / 4), (int) (scale
                    * total_y * 1.2 + 5));
            canvas.drawRect(targetRect, paint);
            int baseline = (targetRect.top - fontMetrics.bottom);
            textPaint.setColor(Color.parseColor("#000000"));
            canvas.translate(targetRect.centerX(), baseline);
            layout.draw(canvas);
            x += margin * 2;
            canvas.translate(-targetRect.centerX(), -baseline);
        }
    }

    public void drawChart(Canvas canvas) {

        paint.setTextAlign(Paint.Align.CENTER);
        float temp = width / 20;
        paint.setTextSize(textSize);
        for (int i = 0; i < data_total.size(); i++) {
            paint.setColor(Color.parseColor("#4f91e1"));
            Rect targetRect = new Rect((int) temp,
                    (int) (scale * (total_y - data_total.get(i))),
                    (int) (temp + margin), (int) (scale * total_y));
            if (data_total.get(i) != 0) {
                canvas.drawRect(targetRect, paint);
            }
            int baseline = targetRect.top - 10;
            paint.setColor(Color.parseColor("#000000"));
            canvas.drawText(data_total.get(i) + "", targetRect.centerX(),
                    baseline, paint);
            temp = temp + margin * 2;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawChart(canvas);
        drawAxis(canvas);
    }
}
