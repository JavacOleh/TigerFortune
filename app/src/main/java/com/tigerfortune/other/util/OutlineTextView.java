package com.tigerfortune.other.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.tigerfortune.R;

/*
Без этого ниже работать не бyдет!
добавить в: res/values/attrs.xml
<resources>
    <declare-styleable name="OutlineTextView">
        <attr name="strokeColor" format="color" />
        <attr name="strokeWidth" format="dimension" />
        <attr name="fillColor" format="color" />
    </declare-styleable>
</resources>
 */

public class OutlineTextView extends AppCompatTextView {

    private int strokeColor = Color.BLACK; // по умолчанию
    private float strokeWidth = 4f; // по умолчанию

    public OutlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OutlineTextView);
        try {
            strokeColor = a.getColor(R.styleable.OutlineTextView_strokeColor, strokeColor);
            strokeWidth = a.getDimension(R.styleable.OutlineTextView_strokeWidth, strokeWidth);
        } finally {
            a.recycle();
        }
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();

        // Настраиваем Paint для обводки
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(strokeWidth);
        getPaint().setColor(strokeColor);

        // Рисуем текст с обводкой
        canvas.drawText(text, getPaddingLeft(), getBaseline(), getPaint());

        // Настраиваем Paint для заливки
        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setColor(getCurrentTextColor());

        // Рисуем заливку
        canvas.drawText(text, getPaddingLeft(), getBaseline(), getPaint());
    }
}