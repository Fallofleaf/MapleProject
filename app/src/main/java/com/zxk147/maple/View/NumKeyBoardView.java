package com.zxk147.maple.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.zxk147.maple.R;

public class NumKeyBoardView extends View {
    private static final int[] NEXT_STATE_ENABLE = {R.attr.next_enable};
    private static final int[] NEXT_STATE_DISABLE = {-R.attr.next_enable};
    public Paint mPaint;
    public boolean isPressed = false;
    private float scale = 0;
    private int view_bg_color;
    private int line_color;
    private int btn_press_bg_color;
    private int btn_text_color;
    private ColorStateList csl_next_bg_color;
    private ColorStateList csl_next_text_color;
    private int next_bg_color;
    private int next_text_color;
    private Drawable back_icon;
    private int text_size = 30;
    private boolean next_enable = true;
    private String next_text = "完成";
    private String[] numValues = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "delete", "next", ".", "+"
    };
    private Rect[] rects = new Rect[14];
    private float[] pts;
    private Rect pressedRect;
    private Bitmap mBpDelete;
    //控件宽度
    private float mBpWidth;
    //控件高度
    private float mBpHeight;
    //单元格宽度
    private int _witth;
    //单元格高度
    private int _height;
    private int _cell_width;
    private int _cell_height;
    private int linewidth = 1;
    private int textwidth = 1;
    private boolean isinitDate = false;
    private int clickX = 0;
    private int clickY = 0;
    private String number = null;
    private OnKeyBoardClickListener mListener;
    public NumKeyBoardView(Context context) {
        this(context, null);
    }
    public NumKeyBoardView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public NumKeyBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumKeyBoardView);

        next_enable = array.getBoolean(R.styleable.NumKeyBoardView_next_enable, true);

        csl_next_bg_color = array.getColorStateList(R.styleable.NumKeyBoardView_next_background);

        upNextEnable();

        init(array);

    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        this._witth = MeasureSpec.getSize(widthMeasureSpec);

        this._height = MeasureSpec.getSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (!isinitDate) {

            initdata();

        }

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        drawBackground(canvas);

        drawLine(canvas);

        drawCell(canvas);

        drawContent(canvas);

    }

    private void init(TypedArray typedArray) {

        if (typedArray != null) {

            view_bg_color = typedArray.getColor(R.styleable.NumKeyBoardView_view_background,

                    getResources().getColor(R.color.dong_color_white));

            line_color = typedArray.getColor(R.styleable.NumKeyBoardView_line_color,

                    getResources().getColor(R.color.dong_color_line_gray));

            btn_press_bg_color = typedArray.getColor(R.styleable.NumKeyBoardView_background_btn_press,

                    getResources().getColor(R.color.dong_color_btn_press_gray));

            btn_text_color = typedArray.getColor(R.styleable

                    .NumKeyBoardView_text_color_btn, getResources().getColor(R.color.dong_cokor_btn_text));

            back_icon = typedArray.getDrawable(R.styleable.NumKeyBoardView_icon_back);

            if (back_icon == null) {

                back_icon = getResources().getDrawable(R.drawable.dong_widget_icon_num_back);

            }

            next_text = typedArray.getString(R.styleable.NumKeyBoardView_text_next);

            if (TextUtils.isEmpty(next_text)) {
                next_text = "完成";
            }
            next_enable = typedArray.getBoolean(R.styleable.NumKeyBoardView_next_enable, true);

            text_size = typedArray.getDimensionPixelSize(R.styleable.NumKeyBoardView_text_size,

                    dip2px(20f));

            csl_next_bg_color = typedArray.getColorStateList(R.styleable.NumKeyBoardView_next_background);

            csl_next_text_color = typedArray.getColorStateList(R.styleable.NumKeyBoardView_text_next);

            upNextEnable();

        } else {

            view_bg_color = getResources().getColor(R.color.dong_color_white);

            line_color = getResources().getColor(R.color.dong_color_line_gray);

            btn_press_bg_color = getResources().getColor(R.color.dong_color_btn_press_gray);

            btn_text_color = getResources().getColor(R.color.dong_color_font_gray);

            if (next_enable) {

                next_bg_color = getResources().getColor(R.color.dong_color_next_bg_enable);

                next_text_color = getResources().getColor(R.color.dong_color_next_text_enable);

            } else {

                next_bg_color = getResources().getColor(R.color.dong_color_next_bg_disable);

                next_text_color = getResources().getColor(R.color.dong_color_next_text_disable);

            }

            back_icon = getResources().getDrawable(R.drawable.dong_widget_icon_num_back);

            upNextEnable();

        }

    }

    private void initdata() {
        isinitDate = true;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(linewidth);
        _cell_width = _witth / 4;
        _cell_height = _height / 4;
        mBpDelete = ((BitmapDrawable) back_icon).getBitmap();
        mBpWidth = mBpDelete.getWidth();
        mBpHeight = mBpDelete.getHeight();
        //10条分割线数组
        pts = new float[]{
                0, 0, _witth, 0,
                0, _cell_height, _cell_width * 3, _cell_height,
                0, _cell_height * 2, _witth, _cell_height * 2,
                0, _cell_height * 3, _cell_width * 3, _cell_height * 3,
                0, _height, _witth, _height,
                0, 0, 0, _height,
                _cell_width, 0, _cell_width, _height,
                _cell_width * 2, 0, _cell_width * 2, _height,
                _cell_width * 3, 0, _cell_width * 3, _height,
                _witth, 0, _witth, _height
        };
        rects[0] = new Rect(_cell_width, _cell_height * 3, _cell_width * 2, _height);

        rects[1] = new Rect(0, 0, _cell_width, _cell_height);

        rects[2] = new Rect(_cell_width, 0, _cell_width * 2, _cell_height);

        rects[3] = new Rect(_cell_width * 2, 0, _cell_width * 3, _cell_height);

        rects[4] = new Rect(0, _cell_height, _cell_width, _cell_height * 2);

        rects[5] = new Rect(_cell_width, _cell_height, _cell_width * 2, _cell_height * 2);

        rects[6] = new Rect(_cell_width * 2, _cell_height, _cell_width * 3, _cell_height * 2);

        rects[7] = new Rect(0, _cell_height * 2, _cell_width, _cell_height * 3);

        rects[8] = new Rect(_cell_width, _cell_height * 2, _cell_width * 2, _cell_height * 3);

        rects[9] = new Rect(_cell_width * 2, _cell_height * 2, _cell_width * 3, _cell_height * 3);

        rects[10] = new Rect(_cell_width * 3, 0, _witth, _cell_height * 2);

        rects[11] = new Rect(_cell_width * 3, _cell_height * 2, _witth, _height);

        //默认空按钮位置

        rects[12] = new Rect(0, _cell_height * 3, _cell_width, _height);

        rects[13] = new Rect(_cell_width * 2, _cell_height * 3, _cell_width * 3, _height);
    }
    /**
     * 绘制底色
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        canvas.drawColor(view_bg_color);
    }

    /**
     * 绘制线条
     *
     * @param canvas
     */

    private void drawLine(Canvas canvas) {

        mPaint.setColor(line_color);

        mPaint.setStrokeWidth(linewidth);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawLines(pts, mPaint);

    }

    /**
     * 绘制点击的按钮颜色 和 下一步按钮背景色
     *
     * @param canvas
     */

    private void drawCell(Canvas canvas) {

        if (isPressed && pressedRect != null) {

            mPaint.setColor(btn_press_bg_color);

            mPaint.setStrokeWidth(linewidth);

            mPaint.setStyle(Paint.Style.FILL);

            canvas.drawRect(pressedRect, mPaint);

        }

        mPaint.setColor(next_bg_color);

        mPaint.setStrokeWidth(linewidth);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawRect(rects[11], mPaint);

    }

    /**
     * 绘制文字及icon颜色
     *
     * @param canvas
     */

    private void drawContent(Canvas canvas) {

        mPaint.setColor(btn_text_color);

        mPaint.setStrokeWidth(textwidth);

        mPaint.setTextSize(text_size);

        mPaint.setTextAlign(Paint.Align.CENTER);

        for (int i = 0; i < 10; i++) {

            drawTextCenter(rects[i], mPaint, numValues[i], canvas);

        }

        mPaint.setColor(next_text_color);

        mPaint.setStrokeWidth(textwidth);

        mPaint.setTextSize(text_size);

        mPaint.setTextAlign(Paint.Align.CENTER);

        drawTextCenter(rects[11], mPaint, next_text, canvas);

        drawBitmapCenter(rects[10], mPaint, mBpDelete, canvas);

    }

    private void drawBitmapCenter(Rect rect, Paint paint, Bitmap bitmap, Canvas canvas) {

        float x = rect.centerX() - (mBpWidth / 2);

        float y = rect.centerY() - (mBpHeight / 2);

        canvas.drawBitmap(bitmap, x, y, paint);

    }

    private void drawTextCenter(Rect rect, Paint textPaint, String text, Canvas canvas) {

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top

        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式

        canvas.drawText(text, rect.centerX(), baseLineY, textPaint);

    }

    /**
     * 设置下一步操作控制名称
     *
     * @param nexttext
     */

    public void setNextText(String nexttext) {

        if (!TextUtils.isEmpty(nexttext)) {

            this.next_text = nexttext;

            invalidate();

        }

    }

    public void setNextEnable(boolean enable) {

        if (next_enable != enable) {

            next_enable = enable;

            refreshDrawableState();

            upNextEnable();

            invalidate();

        }

    }

    private void upNextEnable() {

        if (csl_next_bg_color != null) {

            next_bg_color = csl_next_bg_color.getColorForState(getDrawableState(), 0);

        } else {

            if (next_enable) {

                next_bg_color = getResources().getColor(R.color.dong_color_next_bg_enable);

            } else {

                next_bg_color = getResources().getColor(R.color.dong_color_next_bg_disable);

            }

        }

        if (csl_next_text_color != null) {

            next_text_color = csl_next_text_color.getColorForState(getDrawableState(), 0);

        } else {

            if (next_enable) {

                next_text_color = getResources().getColor(R.color.dong_color_next_text_enable);

            } else {

                next_text_color = getResources().getColor(R.color.dong_color_next_text_disable);

            }

        }

    }

    public void setOnKeyBoardClickListener(@Nullable OnKeyBoardClickListener l) {

        if (l != null) {

            mListener = l;

        }

    }

    private int dip2px(float dpValue) {

        /*当前view的缩放比例*/

        if (0 == scale) {

            scale = this.getContext().getResources().getDisplayMetrics().density;

        }

        return (int) (dpValue * scale + 0.5f);

    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {

        clickX = (int) event.getX();

        clickY = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: //按下

                isPressed = true;

                handleDown();

                invalidate();

                return true;

            case MotionEvent.ACTION_UP: //弹起

                if (mListener != null) {

                    switch (number) {

                        case "null":

                            break;

                        case "delete":

                            mListener.onDetele();

                            break;

                        case "next":

                            if (next_enable) {

                                mListener.onNextClick();

                            }

                            break;

                        default:

                            mListener.onOutPut(number);

                            break;

                    }

                }

                setDefault();

                invalidate();

                return true;

            case MotionEvent.ACTION_CANCEL: //取消

//恢复默认值

                setDefault();

                return true;

            default:

                break;

        }

        return false;

    }

    private void handleDown() {

        for (int i = 0; i < numValues.length; i++) {

            if (rects[i].contains(clickX, clickY)) {

                number = numValues[i];

                pressedRect = rects[i];

                switch (number) {

                    case "null":

                    case "delete":

                    case "next":

                        pressedRect = null;

                        break;

                    default:

                        break;

                }

                return;

            }

        }

    }

    private void setDefault() {

        clickX = 0;

        clickY = 0;

        number = null;

        pressedRect = null;

        isPressed = false;

    }

    @Override

    protected int[] onCreateDrawableState(int extraSpace) {

        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (next_enable) {

            mergeDrawableStates(drawableState, NEXT_STATE_ENABLE);

        } else {

            mergeDrawableStates(drawableState, NEXT_STATE_DISABLE);

        }

        return drawableState;

    }

    public interface OnKeyBoardClickListener {


        void onOutPut(String num);


        void onDetele();


        void onNextClick();


    }

}


