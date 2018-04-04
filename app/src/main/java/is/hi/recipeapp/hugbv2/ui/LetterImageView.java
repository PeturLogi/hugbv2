package is.hi.recipeapp.hugbv2.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import is.hi.recipeapp.hugbv2.R;

import java.util.Random;


/**
 * Created by Brynjar Árnason on 02/04/2018.
 * HBV601G Hugbúnaðarverkefni 2
 * Háskóli Íslands
 *
 * Heldur utan um upplýsingar um uppbyggingu á hönnun notendaviðmóts
 * í formi stafamyndar og texta sem notað er til að túlka  upphafstaf hvers vikudags
 */
public class LetterImageView extends android.support.v7.widget.AppCompatImageView {

    private char mLetter;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private int mTextColor = Color.WHITE;
    private boolean isOval;

    /**
     * Kallar á smiðinn
     * @param context
     * @param attrs
     */
    public LetterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Smiðurinn, gefur breytum gildi
    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(randomColor());
    }

    //getters og setters fyrir ofangreindar breytur

    public char getLetter() {
        return mLetter;
    }

    public void setLetter(char letter) {
        mLetter = letter;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        invalidate();
    }

    public void setOval(boolean oval) {
        isOval = oval;
    }

    public boolean isOval() {
        return isOval;
    }

    /**
     * Hannar og teiknar mynd af upphafstaf fyrir hvern vikudag
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getDrawable() == null) {
            // Setur stærð á stafatextanum í samræmi við hæð myndar
            mTextPaint.setTextSize(canvas.getHeight() - getTextPadding() * 2);
            if (isOval()) {
                canvas.drawCircle(canvas.getWidth() / 2f, canvas.getHeight() / 2f, Math.min(canvas.getWidth(), canvas.getHeight()) / 2f,
                        mBackgroundPaint);
            } else {
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
            }
            // Mæling á stafatexta
            Rect textBounds = new Rect();
            mTextPaint.getTextBounds(String.valueOf(mLetter), 0, 1, textBounds);
            float textWidth = mTextPaint.measureText(String.valueOf(mLetter));
            float textHeight = textBounds.height();
            // Teiknar stafatextann
            canvas.drawText(String.valueOf(mLetter), canvas.getWidth() / 2f - textWidth / 2f,
                    canvas.getHeight() / 2f + textHeight / 2f, mTextPaint);
        }
    }

    // Setur default padding í 8dp
    private float getTextPadding() {
        return 8 * getResources().getDisplayMetrics().density;
    }

    // Setur handahófskenndan lit á hvern staf þegar kallað er á aðferð
    private int randomColor() {
        Random random = new Random();
        String[] colorsArr = getResources().getStringArray(R.array.colors);
        return Color.parseColor(colorsArr[random.nextInt(colorsArr.length)]);
    }
}