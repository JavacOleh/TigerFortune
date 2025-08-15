package com.tigerfortune.engine.addition.common;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.other.util.UiUtil;

public class ViewCoordinates {
    private Tiger tiger;
    private View view;

    public int topView;
    public int bottomView;
    public int endView;
    public int startView;

    public int top;
    public int bottom;
    public int end;
    public int start;
    public ConstraintLayout.LayoutParams tigerLayoutParams;
    public ConstraintLayout.LayoutParams view1LayoutParams;

    public ViewCoordinates(View view, Tiger tiger) {
        this.tiger = tiger;
        this.view = view;
        tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();
        view1LayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        this.topView = view.getTop();
        this.bottomView = view.getBottom(); // Примечание: используем именно view.getBottom(), так как для tiger - это будет другое значение
        this.startView = view1LayoutParams.leftMargin;
        this.endView = startView + view1LayoutParams.width;

        this.top = tiger.view.getTop();
        this.bottom = tiger.view.getBottom();
        this.start = tigerLayoutParams.leftMargin; // + tiger.view.getWidth() / 2;
        this.end = start + tiger.view.getWidth();
    }

    // Можно добавить методы, если логика обработки сложнее
    public boolean isTigerUnderElButCloseToBottom(int pogreshonstY) {
        tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();
        view1LayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        return Math.max(top - bottomView, 0) < pogreshonstY;
    }

    public boolean isTigerBottomCloseToTopView(int pogreshonstY) {
        tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();
        view1LayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        return Math.max(bottom - bottomView, 0) < pogreshonstY;
    }

    public boolean isTigerUnderEl() {
        tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();
        view1LayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        return tigerLayoutParams.bottomMargin < view1LayoutParams.bottomMargin;
    }

    public boolean isTigerInElHorizontally() {
        return isAlthoughOnePixelInElHorizontally(); //(start + UiUtil.dpToPx(100 / 2)) > startView && start < endView;
    }

    public boolean isTigerAndElOnSameGround() {
        return tigerLayoutParams.bottomMargin == view1LayoutParams.bottomMargin;
    }

    public boolean isAlthoughOnePixelInElHorizontally() {
        this.start = tigerLayoutParams.leftMargin;
        for (int i = start; i < start + (tiger.view.getWidth()); i++) {
            if (i > startView && i < endView) {
                return true;
            }
        }
        return false;
    }
}
