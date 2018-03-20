package com.andy.dribbble.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.andy.dribbble.MatrixUtil;
import com.andy.dribbble.common_utils.ScreenUtil;

/**
 * Created by lixn on 2018/3/17.
 */

public class ImageViewer extends LinearLayout {

    public static final int BG_COLOR_DEFAULT = 0xdd000000;
    public static final int BG_COLOR_TRANSPARENT = 0x00000000;
    public static final int DISTANCE_TRIGGER_DISMISS = 200;
    private ImageView mImgView;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mImgRes;
    private ImageView mImgSrc;
    private ActionMode mActionMode;
    private Direction mDirection;
    private Matrix mMatrix = new Matrix();
    private Matrix mCurrentMatrix = new Matrix();
    private PointF mDownPoint;  // 手指按下的点
    private PointF mLastPoint;  // 上次触摸的点
    private PointF mMiddlePoint;   // 双指缩放时的中点

    private int mTouchSlop;
    private int mMinVelocity;
    private float mDragDistanceX;
    private float mDragDistanceY;
    private float mLastScale;
    private double mStartDistance;
    private boolean mShowing;
    private boolean mIsNormal;

    private GestureDetector mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mIsNormal) {
                dismiss();
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mIsNormal) {
//                zoomToMax();
            } else {
//                zoomToNormal();
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > mMinVelocity || Math.abs(velocityY) > mMinVelocity) {
                Matrix m = mImgView.getImageMatrix();
                float[] v = new float[9];
                m.getValues(v);
                int translateX = (int) (v[2]+0.5f);
                int translateY = (int) (v[5]+0.5f);
                int vx = (int) (mVelocityTracker.getXVelocity() + 0.5f);
                int vy = (int) (mVelocityTracker.getYVelocity() + 0.5f);
                mScroller.fling(translateX, translateY, vx, vy, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                invalidate();
//                mCurrentMatrix.set(mImgView.getImageMatrix());
//                    Log.d("aaa", "vx---> "+vx+"; vy---> "+vy);
//                    Log.d("aaa", "start x---> "+translateX+"; y--->"+translateY);
            }
            return true;
        }
    });

    private enum ActionMode {
        IDLE, DRAG, ZOOM
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private OnDragListener mDragListener;

    public ImageViewer(ImageView src, Context context) {
        super(context);
        mImgSrc = src;
        initView(context);
    }

    public ImageViewer(Context context) {
        super(context);
        initView(context);
    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setBackgroundColor(BG_COLOR_DEFAULT);
        setAlpha(0);
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMinVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public boolean handleBackEvent() {
        if (mShowing && mIsNormal) {
            dismiss();
            return true;
        } else if (mShowing) {
            zoomToNormal();
            return true;
        }
        return false;
    }


    protected void zoomToMax() {
//        zoom(2.0f);
//        mIsNormal = false;
        mMatrix.set(mCurrentMatrix);
        mMatrix.postTranslate(100, 100);
        mImgView.setImageMatrix(mMatrix);
    }

    protected void zoomToNormal() {
        zoom(1.0f);
        ObjectAnimator.ofInt(this, "scrollX", getScrollX(), 0).setDuration(300).start();
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), 0).setDuration(300).start();
        int currentBg = ((ColorDrawable) getBackground().mutate()).getColor();
        if (getBackground() instanceof ColorDrawable && currentBg != BG_COLOR_DEFAULT) {
            ObjectAnimator.ofObject(this, "backgroundColor", new ArgbEvaluator(), currentBg, BG_COLOR_DEFAULT).setDuration(300).start();
        }
        mIsNormal = true;
    }

    protected void zoom(float target) {
        ObjectAnimator.ofFloat(mImgView, "scaleX", mImgView.getScaleX(), target).setDuration(300).start();
        ObjectAnimator.ofFloat(mImgView, "scaleY", mImgView.getScaleY(), target).setDuration(300).start();
    }

    protected void dismiss() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ImageViewer.this, "alpha", getAlpha(), 0).setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewParent vp = getParent();
                if (vp instanceof ViewGroup) {
                    ((ViewGroup) vp).removeView(ImageViewer.this);
                }
                mShowing = false;
            }
        });
        animator.start();
    }

    public void show(Activity context, final ImageView src) {
        mShowing = true;
        mImgSrc = src;
        Rect r = new Rect();
        src.getGlobalVisibleRect(r);
        Log.d("aaa", "rect: "+r);
        ViewGroup decor = (ViewGroup) context.getWindow().getDecorView();
        if (getParent() == null) {
            decor.addView(this);
        }
        if (mImgView == null) {
            mImgView = new ImageView(getContext());
            mImgView.setScaleType(ImageView.ScaleType.MATRIX);
            mLastScale = mImgView.getScaleX();
            mIsNormal = true;
            ViewGroup.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(mImgView, lp);
        } else {
            reset();  // 再次进入时重置视图
        }
        if (mImgSrc != null) {
            mImgView.setImageDrawable(mImgSrc.getDrawable());
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(ImageViewer.this, "alpha", 0, 1.0f).setDuration(300).start();
            }
        }, 200);
    }

    /**
     * 恢复初始状态
     */
    private void reset() {
        setBackgroundColor(BG_COLOR_DEFAULT);
        if (getScrollX() != 0 || getScrollY() != 0) {
            scrollTo(0, 0);
        }
        if (mImgView.getScaleX() != 1 || mImgView.getScaleY() != 1) {
            mImgView.setScaleX(1.0f);
            mImgView.setScaleY(1.0f);
        }
    }

    private void initImgBounds() {
        Drawable d = mImgView.getDrawable();
        final int dwidth = d.getIntrinsicWidth();
        final int dheight = d.getIntrinsicHeight();
        final int vwidth = getWidth();
        final int vheight = getHeight();
        float dx = 0;
        float dy = 0;
        float scale = 1.0f;
        if (dwidth * vheight > vwidth * dheight) {
            scale = (float) vheight / (float) dheight;
            dx = (vwidth - dwidth * scale) * 0.5f;
        } else {
            scale = (float) vwidth / (float) dwidth;
            dy = (vheight - dheight * scale) * 0.5f;
        }
        Matrix m = new Matrix();
        m.set(mImgView.getImageMatrix());
        m.setScale(scale, scale);
        m.postTranslate(dx, dy);
        mImgView.setImageMatrix(m);
    }

    private void animateView(Rect bound) {
        final Rect rect = bound;
        final  ViewGroup.LayoutParams lp = mImgView.getLayoutParams();
        final float screenWidth = ScreenUtil.getScreenWidth(getContext());
        final float screenHeight = ScreenUtil.getScreenHeight(getContext());
        final int oldWidth = lp.width;
        final int oldHeight = lp.height;
        final int oldLeft = mImgView.getLeft();
        final int oldTop = mImgView.getTop();
        final int oldRight = mImgView.getRight();
        final int oldBottom = mImgView.getBottom();
        final float oldScaleX = mImgView.getScaleX();
        final float oldScaleY = mImgView.getScaleY();
        Drawable drawable = mImgView.getDrawable();
//        mImgView.setScaleType(ImageView.ScaleType.MATRIX);
//        mImgView.setImageResource(R.mipmap.mm);
//        mImgView.getImageMatrix().setScale(1.0f, -1.0f);
        final int width = drawable.getIntrinsicWidth();
        final int height = drawable.getIntrinsicHeight();
        float v = screenWidth/width;
        float h = screenHeight/height;
        final float scale = Math.min(v, h);
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 1.0f).setDuration(3000);
        ObjectAnimator.ofFloat(this, "alpha", 0, 1.0f).setDuration(1000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float per = (float) animation.getAnimatedValue();
            }
        });
        animator.start();
    }

    private int calculateTranslationX(Rect r) {
        float w = r.right - r.left;
        float screenWidth = ScreenUtil.getScreenWidth(getContext());
        return (int) (screenWidth/2 - w/2 - r.left+0.5f);
    }

    private int calculateTranslationY(Rect r) {
        float h = r.bottom - r.top;
        float screenHeight = ScreenUtil.getScreenHeight(getContext());
        return (int) (screenHeight/2 - h/2 - r.left+0.5f);
    }

    public ImageView getImgView() {
        return mImgView;
    }

    public void setImgRes(int res) {
        mImgView.setImageResource(res);
        invalidate();
    }



    public void setImgFrame(Rect r) {
        requestLayout();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            mMatrix.set(mCurrentMatrix);
            mMatrix.postTranslate(mScroller.getCurrX()-MatrixUtil.getMatrixTranslateX(mCurrentMatrix), mScroller.getCurrY()-MatrixUtil.getMatrixTranslateY(mCurrentMatrix));
            mImgView.setImageMatrix(mMatrix);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF point = new PointF(event.getX(), event.getY());
        checkVelocityTracker();
        mVelocityTracker.addMovement(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mActionMode = ActionMode.DRAG;
                mCurrentMatrix.set(mImgView.getImageMatrix());
                mDownPoint = mLastPoint = point;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mActionMode = ActionMode.ZOOM;
                mStartDistance = calculateDistance(event);
                mLastScale = mImgView.getScaleX();
                mMiddlePoint = calculateMiddlePoint(mLastPoint, point);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mActionMode = ActionMode.DRAG;
                mCurrentMatrix.set(mImgView.getImageMatrix());
                // 找到未抬起的手指
                int index = 0;
                for (int i=0; i < event.getPointerCount(); i ++) {
                    if (i != event.getActionIndex()) {
                        index = i;
                    }
                }
                mDownPoint = new PointF(event.getX(index), event.getY(index));
                break;
            case MotionEvent.ACTION_MOVE:
                mDragDistanceX = point.x - mDownPoint.x;
                mDragDistanceY = point.y - mDownPoint.y;
                if (mDragListener != null) {
                    mDragListener.onDrag(mDragDistanceX, mDragDistanceY);
                }
                if (mActionMode == ActionMode.ZOOM) {
                    double distance = calculateDistance(event);
                    float scale = (float) (distance/mStartDistance);
                    PointF middle = calculateMiddlePoint(mLastPoint, point);
                    mMatrix.set(mCurrentMatrix);
                    mMatrix.postScale(scale, scale, middle.x, middle.y);
                    mImgView.setImageMatrix(mMatrix);
                } else if (mActionMode == ActionMode.DRAG) {
                    double distance = Math.hypot(mDragDistanceX, mDragDistanceY);
                    if (distance > mTouchSlop) {
                        mMatrix.set(mCurrentMatrix);
                        mMatrix.postTranslate(mDragDistanceX, mDragDistanceY);
                        mImgView.setImageMatrix(mMatrix);
                    }
                    if (mIsNormal) {
                        float friction = Math.abs(mDragDistanceY)*2.0f/ScreenUtil.getScreenHeight(getContext());
                        ArgbEvaluator evaluator = new ArgbEvaluator();
                        int color = (int) evaluator.evaluate(Math.min(friction, 1.0f), BG_COLOR_DEFAULT, BG_COLOR_TRANSPARENT);
//                        setBackgroundColor(color);
                    }
                }
                mLastPoint = point;
                break;
            case MotionEvent.ACTION_UP:
                mActionMode = ActionMode.IDLE;
                mVelocityTracker.computeCurrentVelocity(1000);
                mCurrentMatrix.set(mImgView.getImageMatrix());
                int distance = (int) Math.sqrt(mDragDistanceX*mDragDistanceX + mDragDistanceY*mDragDistanceY); // 拖动距离
                Direction direction = calculateDirection();
//                if (mIsNormal && distance > mTouchSlop && (distance < DISTANCE_TRIGGER_DISMISS || direction != Direction.DOWN)) {
//                    zoomToNormal();
//                } else if (mIsNormal && direction == Direction.DOWN && distance > DISTANCE_TRIGGER_DISMISS) {
//                    dismiss();
//                }
                break;
        }
        mDetector.onTouchEvent(event);
        return true;
    }

    private PointF calculateMiddlePoint(PointF first, PointF second) {
        return new PointF((first.x+second.x)/2, (first.y+second.y)/2);
    }

    private Direction calculateDirection() {
        Direction d;
        if (mDragDistanceX < 0 && Math.abs(mDragDistanceX) > Math.abs(mDragDistanceY)) {
            d = Direction.LEFT;
        } else if (mDragDistanceY < 0 && Math.abs(mDragDistanceY) > Math.abs(mDragDistanceX)) {
            d = Direction.UP;
        } else if (mDragDistanceX > 0 && Math.abs(mDragDistanceX) > Math.abs(mDragDistanceY)) {
            d = Direction.RIGHT;
        } else if (mDragDistanceY > 0 && Math.abs(mDragDistanceY)> Math.abs(mDragDistanceX)) {
            d = Direction.DOWN;
        } else {
            d = Direction.DOWN;
        }
        return d;
    }

    private double calculateDistance(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            float x = event.getX(1) - event.getX(0);
            float y = event.getY(1) - event.getY(0);
            return Math.sqrt(x*x+y*y);
        }
        return 0;
    }
    private boolean isTouchInView(View target, float rawX, float rawY) {
        if (target == null) {
            return false;
        }
        Rect bounds = new Rect();
        boolean b = target.getGlobalVisibleRect(bounds);
        return b && rawX>bounds.left && rawX < bounds.right && rawY>bounds.top && rawY<bounds.bottom;

    }

    private void checkVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    public void setOnDragListener(OnDragListener listener) {
        mDragListener = listener;
    }

    public interface OnDragListener {
        void onDrag(float distanceX, float distanceY);
    }
}
