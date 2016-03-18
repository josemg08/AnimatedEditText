package com.alimuzaffar.lib.widgets;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import org.w3c.dom.Text;

/**
 * An EditText field that can animate the typed text.
 * Current only support LTR languages.
 *
 * @author Ali Muzaffar
 */
public class AnimatedEditText extends AppCompatEditText {
    private Paint mPaint;
    private Paint mAnimPaint;
    private ColorStateList mOriginalTextColors;
    private boolean mAnimated = true;
    private AnimationType mAnimationType = AnimationType.BOTTOM_UP;
    private String mMask = null;
    private StringBuilder mMaskChars = null;

    private float mRightOffset = 0;
    private float mBottomOffset = 0;
    private int mStart = 0;
    private int mEnd = 0;


    public enum AnimationType {
        RIGHT_TO_LEFT, BOTTOM_UP, MIDDLE_UP, POP_IN
    }

    public AnimatedEditText(Context context) {
        super(context);
        init(context, null);
    }

    public AnimatedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimatedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnimatedEditText, 0, 0);
        try {
            TypedValue outValue = new TypedValue();
            ta.getValue(R.styleable.AnimatedEditText_animationType, outValue);
            if (outValue.data == 0) {
                setAnimationType(AnimationType.BOTTOM_UP);
            } else if (outValue.data == 1) {
                setAnimationType(AnimationType.RIGHT_TO_LEFT);
            } else if (outValue.data == 2) {
                setAnimationType(mAnimationType = AnimationType.MIDDLE_UP);
            } else if (outValue.data == 3) {
                setAnimationType(mAnimationType = AnimationType.POP_IN);
            }
            mMask = ta.getString(R.styleable.AnimatedEditText_textMask);
        } finally {
            ta.recycle();
        }

        //If input type is password and no mask is set, use a default mask
        if ((getInputType() & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD && TextUtils.isEmpty(mMask)) {
            mMask = "\u25CF";
        } else if ((getInputType() & InputType.TYPE_NUMBER_VARIATION_PASSWORD) == InputType.TYPE_NUMBER_VARIATION_PASSWORD && TextUtils.isEmpty(mMask)) {
            mMask = "\u25CF";
        }

        if (!TextUtils.isEmpty(mMask)) {
            mMaskChars = new StringBuilder();
        }
    }

    public void setTextAnimated(boolean animated) {
        mAnimated = animated;
        if (animated) {
            setTextColor(Color.TRANSPARENT);
        } else {
            setTextColor(mOriginalTextColors);
        }
    }

    public void setAnimationType(AnimationType animationType) {
        if (mAnimationType == null) {
            mAnimationType = AnimationType.BOTTOM_UP;
            setTextAnimated(false);
        } else {
            mAnimationType = animationType;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint(getPaint());
        mAnimPaint = new Paint(getPaint());
        mOriginalTextColors = getTextColors();
        // This is needed, otherwise the cursor doesn't stay in sync
        // when input type is not set to password.
        if (!TextUtils.isEmpty(mMask)) {
            mPaint.setTypeface(Typeface.MONOSPACE);
            mAnimPaint.setTypeface(Typeface.MONOSPACE);
        }
        setTextColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mAnimated) {
            return;
        }

        if ((getGravity() & Gravity.RIGHT) == Gravity.RIGHT) {
            drawGravityRight(canvas);
        } else if ((getGravity() & Gravity.LEFT) == Gravity.LEFT) {
            drawGravityLeft(canvas);
        }else {
            drawGravityCenterHorizontal(canvas);
        }
    }


    private void drawGravityLeft(Canvas canvas) {
        String fixedText = getFixedText();
        float fixedTextWidth = mPaint.measureText(fixedText);
        float startX = getCompoundPaddingLeft();
        canvas.drawText(fixedText, startX, getLineBounds(0, null), mPaint);
        canvas.drawText(getFullText(), mStart, mEnd, startX + fixedTextWidth + mRightOffset, getLineBounds(0, null) + mBottomOffset, mAnimPaint);
    }

    /*
    Should work, but doesn't nothing seems to draw, dont know why!
     */
    private void drawGravityRight(Canvas canvas) {
        canvas.translate(getScrollX(), 0);
        String fixedText = getFixedText();
        float fixedTextWidth = mPaint.measureText(fixedText);

        String animChar = getAnimChar();
        float animCharWidth = mPaint.measureText(animChar);
        float fullTexWidth = fixedTextWidth + animCharWidth;

        float startX = getWidth() - getCompoundPaddingRight();
        canvas.drawText(fixedText, startX - fullTexWidth, getLineBounds(0, null), mPaint);
        canvas.drawText(getFullText(), mStart, mEnd, startX - animCharWidth + mRightOffset, getLineBounds(0, null) + mBottomOffset, mAnimPaint);

    }

    private void drawGravityCenterHorizontal(Canvas canvas) {
        canvas.translate(getScrollX(), 0);
        String fixedText = getFixedText();
        float fixedTextWidth = mPaint.measureText(fixedText);

        String animChar = getAnimChar();
        float animCharWidth = mPaint.measureText(animChar);
        float fullTexWidth = fixedTextWidth + animCharWidth;

        float startX = getWidth()/2 - fullTexWidth/2;
        float startXAnim = getWidth()/2 + fullTexWidth/2 - animCharWidth;
        canvas.drawText(fixedText, startX, getLineBounds(0, null), mPaint);
        canvas.drawText(getFullText(), mStart, mEnd, startXAnim + mRightOffset, getLineBounds(0, null) + mBottomOffset, mAnimPaint);
    }

    private String getFullText() {
        if (TextUtils.isEmpty(mMask)) {
            return getText().toString();
        } else {
            return TextUtils.substring(getMaskChars(), 0, getText().length());
        }
    }

    private String getFixedText() {
        if (TextUtils.isEmpty(mMask)) {
            return TextUtils.substring(getText(), 0, mStart);
        } else {
            return TextUtils.substring(getMaskChars(), 0, mStart);
        }
    }

    private String getAnimChar() {
        if (TextUtils.isEmpty(mMask)) {
            return TextUtils.substring(getText(), mStart, mEnd);
        } else {
            return TextUtils.substring(getMaskChars(), mStart, mEnd);
        }
    }

    private StringBuilder getMaskChars() {
        if (mMaskChars == null) {
            mMaskChars = new StringBuilder();
        }
        int textLength = getText().length();
        while (mMaskChars.length() != textLength) {
            if (mMaskChars.length() < textLength) {
                mMaskChars.append(mMask);
            } else {
                mMaskChars.deleteCharAt(mMaskChars.length() - 1);
            }
        }
        return mMaskChars;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (lengthAfter > lengthBefore && start == text.length() - 1) {
            mStart = start;
            mEnd = start + lengthAfter;
            switch (mAnimationType) {
                case RIGHT_TO_LEFT:
                    animateInFromRight();
                    break;
                case MIDDLE_UP:
                    animateInFromMiddle();
                    break;
                case POP_IN:
                    animatePopIn();
                    break;
                default:
                    animateInFromBottom();
            }
        } else {
            mStart = 0;
            mEnd = text.length();
        }
    }

    private void animateInFromBottom() {
        ValueAnimator animUp = ValueAnimator.ofFloat(getHeight() - getCompoundPaddingBottom() - getCompoundPaddingTop(), 0);
        animUp.setDuration(300);
        animUp.setInterpolator(new OvershootInterpolator());
        animUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBottomOffset = (Float) animation.getAnimatedValue();
                AnimatedEditText.this.invalidate();
            }
        });
        ValueAnimator animAlpha = ValueAnimator.ofInt(0, 255);
        animAlpha.setDuration(300);
        animAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a = (Integer) animation.getAnimatedValue();
                mAnimPaint.setAlpha(a);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animUp, animAlpha);
        set.start();
    }

    private void animateInFromRight() {
        ValueAnimator va = ValueAnimator.ofFloat(getWidth() + (getContext().getResources().getDisplayMetrics().widthPixels - getWidth()), 0);
        va.setDuration(300);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRightOffset = (Float) animation.getAnimatedValue();
                AnimatedEditText.this.invalidate();
            }
        });
        va.start();
    }

    private void animateInFromMiddle() {
        String fixed = TextUtils.substring(getText(), 0, mStart);
        final float textWidth = mPaint.measureText(fixed);
        ValueAnimator animMiddle = ValueAnimator.ofFloat(getWidth() / 2, textWidth);
        animMiddle.setInterpolator(new DecelerateInterpolator());
        animMiddle.setDuration(200);
        animMiddle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRightOffset = (Float) animation.getAnimatedValue();
                mRightOffset -= textWidth;
                AnimatedEditText.this.invalidate();
            }
        });
        ValueAnimator animUp = ValueAnimator.ofFloat(getHeight() - getCompoundPaddingBottom() - getCompoundPaddingTop(), 0);
        animUp.setDuration(200);
        animUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBottomOffset = (Float) animation.getAnimatedValue();
                AnimatedEditText.this.invalidate();
            }
        });
        ValueAnimator animAlpha = ValueAnimator.ofInt(0, 255);
        animAlpha.setDuration(300);
        animAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a = (Integer) animation.getAnimatedValue();
                mAnimPaint.setAlpha(a);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animUp, animAlpha, animMiddle);
        set.start();
    }

    private void animatePopIn() {
        ValueAnimator va = ValueAnimator.ofFloat(1, mPaint.getTextSize());
        va.setDuration(200);
        va.setInterpolator(new OvershootInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimPaint.setTextSize((Float) animation.getAnimatedValue());
                AnimatedEditText.this.invalidate();
            }
        });
        va.start();
    }

}
