package com.zxk147.maple.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.zxk147.maple.R;


public class AccountItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;
    private DecorationCallBack callback;

    public AccountItemDecoration(Context context, DecorationCallBack decorationCallback) {
        Resources res = context.getResources();
        this.callback = decorationCallback;
        paint = new Paint();
        paint.setColor(res.getColor(R.color.colorgrey));
        textPaint = new TextPaint();
        //textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //textPaint.setAntiAlias(true);
        textPaint.setTextSize(64);
        //textPaint.setColor(Color.BLACK);
        //textPaint.getFontMetrics(fontMetrics);
        //textPaint.setTextAlign(Paint.Align.LEFT);
        //fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.threeTwo);//32dp
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        Log.i(TAG, "getItemOffsets：" + position);
        //int groupId = callback.getGroupId(position);
        //if (groupId < 0) return;
        if (position == 0 || isFirstInGroup(position)) {//同组的第一个才添加padding
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        int x = 0;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            //String groupId = callback.getGroupDate(position);
            //if (groupId < 0) return;

            String textLine = String.valueOf(callback.getGroupFirstDate(position));

            if (position == 0 || isFirstInGroup(position)) {

                float top = view.getTop() - topGap;
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, paint);//绘制矩形
                c.drawText(textLine, left+14, bottom-20, textPaint);//绘制文本
            }
        }
    }

    private boolean isFirstInGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            String prevGroupId = callback.getGroupFirstDate(position - 1);
            String groupId = callback.getGroupDate(position);
            return !prevGroupId.equals(groupId);
        }
    }

    public interface DecorationCallBack {
        String getGroupDate(int position);

        String getGroupFirstDate(int position);

        boolean getGroupType(int position);

        String getGroupAmount(int position);

    }
}
