/*
 * Copyright (C) 2017 Aromajoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aromajoin.actionsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by Quang Nguyen on 3/16/17.
 * <p>
 * Creates an ActionSheet view which is similar to UIAlertConroller(.actionsheet style) of iOS,
 * especially, shown in iPad.
 * It has an arrow which points to a anchor view.
 */

public class ActionSheet {

    /**
     * Defines the styles that ActionSheet supports
     * They are similar with iOS ActionSheet style
     */
    public enum Style {
        /**
         * For user's normal actions in which the button uses blue text color
         */
        DEFAULT,
        /**
         * For the actions in which users remove or modify app content and data.
         */
        DESTRUCTIVE
    }

    private PopupWindow popupWindow;

    private ContentView contentView;

    private View anchorView;

    private Context context;

    public ActionSheet(Context context) {
        init(context,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void init(Context context, int width, int height) {
        this.context = context;
        if (popupWindow == null) {
            contentView = new ContentView(this);
            popupWindow = new PopupWindow(contentView,
                    width,
                    height,
                    true);

            // Closes the popup window when touch outside
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            // Removes default background
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public Context getContext() {
        return context;
    }

    /**
     * Sets title for ActionSheet
     *
     * @param title the name of the ActionSheet view
     */
    public void setTitle(String title) {
        if (contentView != null) {
            contentView.setTitle(title);
        }
    }

    public String getTitle() {
        return contentView.getTitle();
    }

    /**
     * Shows ActionSheet view after initialization.
     */
    public void show() {
        // Add arrow to actionsheet
        contentView.addArrow(Arrow.DOWN);

        if (anchorView == null) return;
        if (popupWindow == null) return;

        Rect viewRect = ViewUtils.locateView(anchorView);
        if (viewRect == null) return;

        // Points SheetView to the center of its anchor view
        contentView.measure(contentView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        int xPoint = viewRect.left + anchorView.getWidth() / 2 - contentView.getMeasuredWidth() / 2; // left
        int yPoint = viewRect.top + anchorView.getHeight() / 2 - contentView.getMeasuredHeight(); // top

        // Makes sure that action sheet is always shown inside screen.
        if ( yPoint <= ViewUtils.getStatusBarHeight(context)) {
            yPoint = ViewUtils.getStatusBarHeight(context);
        }

        if (popupWindow.isShowing()) {
            popupWindow.update(xPoint,
                    yPoint,
                    -1,
                    -1);
        } else {
            popupWindow.showAtLocation(anchorView.getRootView(),
                    Gravity.NO_GRAVITY,
                    xPoint,
                    yPoint);
        }
    }

    /**
     * Hides ActionSheet view when completion.
     */
    public void dismiss() {
        popupWindow.dismiss();
    }

    /**
     * Declares the position in which the ActionSheet view will be shown.
     *
     * @param anchorView an element view of the container which the ActionShet will point at.
     */
    public void setSourceView(View anchorView) {
        this.anchorView = anchorView;
    }

    /**
     * Allows user to add action during initialization process
     *
     * @param title    title of action
     * @param style    style of action
     * @param listener call back when users click action
     */
    public void addAction(String title, Style style, OnActionListener listener) {
        if (contentView == null) return;
        if (listener == null) return;
        contentView.addActionView(title, style, listener);
    }
}
