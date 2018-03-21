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
import android.graphics.RectF;
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

    public final String TAG = getClass().getSimpleName();
    public static final int REBOUND_REFERENCE_VELOCITY = 5000;
    public static final int REBOUND_REFERENCE_DISTANCE = 200;
    public static final int ANIM_DURATION_DEFAULT = 300;
    public static final int BG_COLOR_DEFAULT = 0xdd000000;
    public static final int BG_COLOR_TRANSPARENT = 0x00000000;
    public static final int DISTANCE_TRIGGER_DISMISS = 200;
    public static final float SCALE_RATIO_DEFAULT = 2.0f;  // 双击时默认的缩放比例
    public static final float SCALE_RATIO_INIT = 1.0f;
    public static final float SCALE_RATIO_MIN = 0.1f;
    public static final float SCALE_RATIO_MAX = 10.0f;
    private ImageView mImgView;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mImgRes;
    private ImageView mImgSrc;
    private Drawable mDrawableSrc;
    private ActionMode mActionMode;
    private Direction mDirection;
    private Matrix mMatrix = new Matrix();
    private Matrix mCurrentMatrix = new Matrix();
    private Matrix mInitMatrix = new Matrix(); // 初始状态的Matrix
    private PointF mDownPoint;  // 手指按下的点
    private PointF mLastPoint;  // 上次触摸的点
    private PointF mMiddlePoint;   // 双指缩放时的中点

    private int mTouchSlop;
    private int mMinVelocity;
    private float mDragDistanceX;
    private float mDragDistanceY;
    private float mCrossBorderX;  // fling时超出边界的值（此值由到达边界时的速度决定，速度越大，越界值越大）
    private float mCrossBorderY;  // fling时超出边界的值（此值由到达边界时的速度决定，速度越大，越界值越大）
    private double mStartDistance;
    private boolean mShowing;
    private boolean mIsNormal;
    private boolean mIsRebound; // 是否在执行回弹效果

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
            PointF pivot = new PointF(e.getX(), e.getY());
            if (mIsNormal) {
                zoomToDefault(pivot);
            } else {
                animateToInit(pivot);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > mMinVelocity || Math.abs(velocityY) > mMinVelocity) {
                Matrix m = mImgView.getImageMatrix();
                int translateX = (int) (MatrixUtil.getMatrixTranslateX(m)+05f);
                int translateY = (int) (MatrixUtil.getMatrixTranslateY(m)+0.5f);
                int vx = (int) (velocityX + 0.5f);
                int vy = (int) (velocityY + 0.5f);
                if (!mIsNormal) {
                    mScroller.fling(translateX, translateY, vx, vy, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    invalidate();
                } else if (vy > 0 && Math.abs(vy) > Math.abs(vx)) {
                    mScroller.fling(translateX, translateY, vx, vy, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    invalidate();
                    dismiss();
                } else {
                    animateToInit(null);
                }
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
            animateToInit(null);
            return true;
        }
        return false;
    }

    private void animateToInit(PointF pivot) {
        zoomToInit(pivot);
        Matrix current = mImgView.getImageMatrix();
        PointF from = new PointF(MatrixUtil.getMatrixTranslateX(current), MatrixUtil.getMatrixTranslateY(current));
        PointF to = new PointF(MatrixUtil.getMatrixTranslateX(mInitMatrix), MatrixUtil.getMatrixTranslateY(mInitMatrix));
        translate(from, to);
        int currentBg = ((ColorDrawable) getBackground().mutate()).getColor();
        if (getBackground() instanceof ColorDrawable && currentBg != BG_COLOR_DEFAULT) {
            ObjectAnimator.ofObject(this, "backgroundColor", new ArgbEvaluator(), currentBg, BG_COLOR_DEFAULT).setDuration(ANIM_DURATION_DEFAULT).start();
        }
    }

    protected void zoomToDefault(PointF pivot) {
        zoom(MatrixUtil.getMatrixScaleX(mMatrix), MatrixUtil.getMatrixScaleX(mInitMatrix)*SCALE_RATIO_DEFAULT, pivot);
        mIsNormal = false;
    }

    protected void zoomToInit(PointF pivot) {
        zoom(MatrixUtil.getMatrixScaleX(mMatrix), MatrixUtil.getMatrixScaleX(mInitMatrix), pivot);
        mIsNormal = true;
    }

    protected void zoom(final float scaleFrom, final float scaleTo, final PointF pivot) {
        if (scaleFrom == 0 || scaleTo == 0) {
            throw new IllegalArgumentException("缩放系数不能为0");
        }
        mMatrix.set(mImgView.getImageMatrix());
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f).setDuration(ANIM_DURATION_DEFAULT);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float friction = (float) animation.getAnimatedValue();
                float target = scaleFrom+(scaleTo - scaleFrom)*friction;
                float lastScale = MatrixUtil.getMatrixScaleX(mMatrix);
                float multiple = target/Math.max(lastScale, SCALE_RATIO_MIN);  // 增加的倍数，防止除数为0
                if (pivot == null) {
                    mMatrix.postScale(multiple, multiple);
                } else {
                    mMatrix.postScale(multiple, multiple, pivot.x, pivot.y);
                }
                mImgView.setImageMatrix(mMatrix);
            }
        });
        animator.start();
    }

    private void translate(final PointF from, final PointF to) {
        if (from == null || to == null) {
            return;
        }
        mMatrix.set(mImgView.getImageMatrix());
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f).setDuration(ANIM_DURATION_DEFAULT);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float friction = (float) animation.getAnimatedValue();
                float targetX = from.x + (to.x-from.x)*friction;
                float targetY = from.y + (to.y-from.y)*friction;
                float lastX = MatrixUtil.getMatrixTranslateX(mImgView.getImageMatrix());
                float lastY = MatrixUtil.getMatrixTranslateY(mImgView.getImageMatrix());
                mMatrix.postTranslate(targetX - lastX, targetY - lastY);
                mImgView.setImageMatrix(mMatrix);
            }
        });
        animator.start();
    }

    protected void dismiss() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ImageViewer.this, "alpha", getAlpha(), 0).setDuration(ANIM_DURATION_DEFAULT);
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
        if (context == null) {
            throw new IllegalArgumentException("需要传入图片所在的Activity");
        }
        if (src == null) {
            throw new IllegalArgumentException("ImageView不能为null");
        }
        mShowing = true;
        mImgSrc = src;
        mDrawableSrc = src.getDrawable();  // 获取源ImageView的Drawable，此处可能为null
        Rect r = new Rect();
        mImgSrc.getGlobalVisibleRect(r);
        Log.d(TAG, "image view bounds: "+r);
        ViewGroup decor = (ViewGroup) context.getWindow().getDecorView();
        if (getParent() == null) {
            decor.addView(this);
        }
        initImgView();
        setBackgroundColor(BG_COLOR_DEFAULT);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(ImageViewer.this, "alpha", 0, 1.0f).setDuration(ANIM_DURATION_DEFAULT).start();
            }
        }, ANIM_DURATION_DEFAULT);
    }

    private void initImgView() {
        if (mImgView == null) {
            mImgView = new ImageView(getContext());
            ViewGroup.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(mImgView, lp);
        }
        Drawable d = mDrawableSrc;
        if (d != null) {
            mImgView.setImageDrawable(d);
            mImgView.setScaleType(ImageView.ScaleType.MATRIX);
            RectF src = new RectF(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());  // 图片本身的范围
            RectF dst = new RectF(0, 0, ScreenUtil.getScreenWidth(getContext()), ScreenUtil.getScreenHeight(getContext())); // ImageView的大小
            mMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
            mImgView.setImageMatrix(mMatrix);
            mInitMatrix.set(mMatrix);
        }
        mIsNormal = true;
        Log.d(TAG, "init img matrix "+MatrixUtil.toString(mInitMatrix));
    }

    public ImageView getImgView() {
        return mImgView;
    }

    public void setImgDrawable(Drawable d) {
        mDrawableSrc = d;
        initImgView();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            float translateX = mScroller.getCurrX();
            float translateY = mScroller.getCurrY();
            float lastTranslateX = MatrixUtil.getMatrixTranslateX(mCurrentMatrix);
            float lastTranslateY = MatrixUtil.getMatrixTranslateY(mCurrentMatrix);
            if (mIsRebound) {
                // 反弹
                mMatrix.postTranslate(translateX-lastTranslateX, translateY-lastTranslateY);
                mImgView.setImageMatrix(mMatrix);
            }
            mMatrix.set(mCurrentMatrix);
            Log.d(TAG, "fling translateX: "+translateX+"; translateY: "+translateY+"; velocity: "+mScroller.getCurrVelocity());
            if (getScrollBound().contains(translateX, translateY)) {
                mMatrix.postTranslate(translateX-lastTranslateX, translateY-lastTranslateY);
                mImgView.setImageMatrix(mMatrix);
            } else { // 超出边界，触发反弹效果
                float v = mScroller.getCurrVelocity();
                float deltaX = translateX - lastTranslateX;
                float deltaY = translateY - lastTranslateY;
                double delta = Math.hypot(deltaX, deltaY);
                double vx = v*deltaX/delta;  // x方向的速度
                double vy = v*deltaY/delta;  // y方向的速度
                float friction = mScroller.getCurrVelocity() / REBOUND_REFERENCE_VELOCITY;
                mCrossBorderX = (float) (vx*REBOUND_REFERENCE_DISTANCE/REBOUND_REFERENCE_VELOCITY);
                mCrossBorderY = (float) (vy*REBOUND_REFERENCE_DISTANCE/REBOUND_REFERENCE_VELOCITY);
                Log.d(TAG, "trigger rebound. friction: "+friction+"; vx: "+vx+"; vy: "+vy);
                mScroller.startScroll((int)translateX, (int)translateY, -(int)mCrossBorderX, -(int)mCrossBorderY, ANIM_DURATION_DEFAULT*2);
                mIsRebound = true;
            }
            invalidate();
        } else {
            mIsRebound = false;
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
                    float translateX = MatrixUtil.getMatrixTranslateX(mMatrix);
                    float translateY = MatrixUtil.getMatrixTranslateY(mMatrix);
                    if (distance > mTouchSlop && getScrollBound().contains(translateX, translateY)) {
                        mMatrix.set(mCurrentMatrix);
                        mMatrix.postTranslate(mDragDistanceX, mDragDistanceY);
                        mImgView.setImageMatrix(mMatrix);
                    }
                    if (mIsNormal && mDragDistanceY > 0) {
                        float friction = Math.abs(mDragDistanceY)*2.0f/ScreenUtil.getScreenHeight(getContext());
                        float scale = 1 - Math.min(friction, 1);
                        mMatrix.set(mCurrentMatrix);
                        mMatrix.postScale(scale, scale, point.x, point.y);
                        mMatrix.postTranslate(mDragDistanceX, mDragDistanceY);
                        mImgView.setImageMatrix(mMatrix);
                        ArgbEvaluator evaluator = new ArgbEvaluator();
                        int color = (int) evaluator.evaluate(Math.min(friction, 1.0f), BG_COLOR_DEFAULT, BG_COLOR_TRANSPARENT);
                        setBackgroundColor(color);
                    }
                }
                mLastPoint = point;
                break;
            case MotionEvent.ACTION_UP:
                mActionMode = ActionMode.IDLE;
                mVelocityTracker.computeCurrentVelocity(1000);
                mCurrentMatrix.set(mImgView.getImageMatrix());
                int distance = (int) Math.hypot(mDragDistanceX , mDragDistanceY); // 拖动距离
                float velocityY = mVelocityTracker.getYVelocity();
                Direction direction = calculateDirection();
                if (mIsNormal && distance > mTouchSlop && (distance < DISTANCE_TRIGGER_DISMISS || direction != Direction.DOWN)) {
                    animateToInit(null);
                } else if (mIsNormal && direction == Direction.DOWN && (Math.abs(velocityY) > mMinVelocity || distance > DISTANCE_TRIGGER_DISMISS)) {
                    dismiss();
                }
                break;
        }
        mDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 图片滑动的边界
     * @return
     */
    private RectF getScrollBound() {
        float screenWidth = ScreenUtil.getScreenWidth(getContext());
        float screenHeight = ScreenUtil.getScreenHeight(getContext());
        float imgWidth = 0;
        float imgHeight = 0;
        float scale = MatrixUtil.getMatrixScaleX(mMatrix);
        if (mDrawableSrc != null) {
            imgWidth = mDrawableSrc.getIntrinsicWidth()*scale;
            imgHeight = mDrawableSrc.getIntrinsicHeight()*scale;
        }
        float left = -(imgWidth - screenWidth/2);
        float top = -(imgHeight - screenHeight/2);
        float right = screenWidth/2;
        float bottom = screenHeight/2;
        return new RectF(left, top, right, bottom);
    }

    /**
     * 回弹时的边界
     * @param r
     * @param expandX
     * @param expandY
     * @return
     */
    private RectF getExpandBound(RectF r, float expandX, float expandY) {
        return new RectF(r.left-expandX, r.top-expandY, r.right+expandX, r.bottom+expandY);
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
