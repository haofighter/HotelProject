package com.sun.hotelproject.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class PopUtils {
    private static PopUtils popUtils = new PopUtils();
    private PopupWindow popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT);
    private View parentView;
    private View contextView;

    private PopUtils() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
        }
    }

    public static PopUtils getInstance() {
        if (popUtils == null) {
            popUtils = new PopUtils();
        }
        return popUtils;
    }

    public void build() {
        if (contextView == null) {
            throw new NullPointerException("popwindow is null,please setContentView");
        }
        if (parentView == null) {
            throw new NullPointerException("popwindow parent is null,please setShowView");
        }
        popupWindow.setContentView(contextView);
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    public PopUtils setShowView(View v) {
        parentView = v;
        return this;
    }


    public PopUtils setContentView(View v) {
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contextView = v;
        return this;
    }

    public PopUtils setAnim(int id) {
        popupWindow.setAnimationStyle(id);
        return this;
    }

    public PopUtils setContentView(int vId, Context context) {
        return setContentView(LayoutInflater.from(context).inflate(vId, null));
    }

    public PopUtils setOnClickListener(View.OnClickListener onClickListener) {
        recursionView(onClickListener, contextView);
        return this;
    }

    //对布局进行递归 只有button,textview,imageview可用于设置点击回调
    public void recursionView(View.OnClickListener onClickListener, View v) {
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View child = ((ViewGroup) v).getChildAt(i);
                if (child instanceof TextView || child instanceof Button || child instanceof ImageView || child instanceof ImageButton) {
                    child.setOnClickListener(onClickListener);
                } else if (child instanceof ViewGroup) {
                    recursionView(onClickListener, ((ViewGroup) v).getChildAt(i));
                }
            }
        }
    }

    public void dismiss() {
        popupWindow.dismiss();
    }
}
