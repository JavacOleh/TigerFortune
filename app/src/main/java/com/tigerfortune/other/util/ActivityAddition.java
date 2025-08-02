package com.tigerfortune.other.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.other.layout.size.LayoutSize;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ActivityAddition {
    Activity activity;

    public final void actBySize() {
        var size = LayoutSize.detect(activity);

        switch (size.height) {

            case Middle -> {
                switch (size.width) {
                    case Big -> onLargeLandScap();
                }
            }

            case Small -> {
                switch (size.width) {
                    case High -> onBasic();
                    case Small -> onSmall();
                    // Можно добавить другие случаи для Small x другие комбинации
                }
            }

            case High -> {
                switch (size.width) {
                    case Middle -> onBasic();
                    case High -> onLargePortrait();
                    // Можно добавить другие случаи для High x другие комбинации
                }
            }

            case Large -> {
                switch (size.width) {
                    case Large -> {
                        // Действие для Large x Large
                    }
                    // Добавьте другие комбинации по необходимости
                }
            }

            // Дополнительные обработчики для других высот (если нужно)
        }
    }

    public abstract void onBasic();

    public abstract void onSmall();

    public abstract void onLargeLandScap();

    public abstract void onLargePortrait();

    public void updateMargin(View view,
                             Integer leftMargin,
                             Integer topMargin,
                             Integer rightMargin,
                             Integer bottomMargin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();

        params.setMargins(
                leftMargin == null ? params.leftMargin : leftMargin,
                topMargin == null ? params.topMargin : topMargin,
                rightMargin == null ? params.rightMargin : rightMargin,
                bottomMargin == null ? params.bottomMargin : bottomMargin
        );

        view.setLayoutParams(params);
    }

    public void updateSize(View view,
                           Integer width,
                           Integer height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();

        if (params instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams frameParams = (FrameLayout.LayoutParams) params;
            frameParams.width = width != null ? width : frameParams.width;
            frameParams.height = height != null ? height : frameParams.height;
            view.setLayoutParams(frameParams);
        } else if (params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) params;
            linearParams.width = width != null ? width : linearParams.width;
            linearParams.height = height != null ? height : linearParams.height;
            view.setLayoutParams(linearParams);
        } else if (params instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams constraintParams = (ConstraintLayout.LayoutParams) params;
            constraintParams.width = width != null ? width : constraintParams.width;
            constraintParams.height = height != null ? height : constraintParams.height;
            view.setLayoutParams(constraintParams);
        }
        // Добавьте дополнительные условия для других типов макетов, если необходимо.
    }


}
