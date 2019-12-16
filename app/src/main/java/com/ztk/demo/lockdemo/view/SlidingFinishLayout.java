package com.ztk.demo.lockdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * @author ztk
 */
public class SlidingFinishLayout extends RelativeLayout {
    /**
     * 滑动的最小距离
     */
    private int mTouchSlop;

    private Scroller mScroller;

    /**
     * 父布局
     */
    private ViewGroup mParentView;

    /**
     * 按下X坐标
     */
    private int downX;
    /**
     * 按下Y坐标
     */
    private int downY;
    /**
     * 临时存X坐标
     */
    private int tempX;

    private int viewWidth;
    /**
     * 是否正在滑动
     */
    private boolean isSliding;

    private OnSlidingFinishListener onSlidingFinishListener;
    private boolean isFinish;
    public SlidingFinishLayout(Context context) {
        super(context);
    }

    public SlidingFinishLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    public interface OnSlidingFinishListener {
        /**
         * 滑动销毁页面回调
         */
        void onSlidingFinish();
    }

    public void setOnSlidingFinishListener(OnSlidingFinishListener onSlidingFinishListener) {
        this.onSlidingFinishListener = onSlidingFinishListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 获取SlidingFinishLayout布局的父布局
            mParentView = (ViewGroup) this.getParent();
            viewWidth = this.getWidth();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (Math.abs(moveX - downX) > mTouchSlop
                        && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                    isSliding = true;

                }

                if (moveX - downX >= 0 && isSliding) {
                    mParentView.scrollBy(deltaX, 0);

                }
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                if (mParentView.getScrollX() <= -viewWidth / 4) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void scrollRight() {
        final int delta = (viewWidth + mParentView.getScrollX());
        //滚动出界面
        mScroller.startScroll(mParentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    private void scrollOrigin() {
        int delta = mParentView.getScrollX();
        //滚动到起始位置
        mScroller.startScroll(mParentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished()) {
                if (onSlidingFinishListener != null && isFinish) {
                    onSlidingFinishListener.onSlidingFinish();
                }
            }
        }
    }
}
