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

    private PopupWindow mPopupWindow;

    private ContentView mContentView;

    private View mAnchorView;

    private Context mContext;

    public ActionSheet(Context context) {
        init(context,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void init(Context context, int width, int height) {
        mContext = context;
        if (mPopupWindow == null) {
            mContentView = new ContentView(this);
            mPopupWindow = new PopupWindow(mContentView,
                    width,
                    height,
                    true);

            // Closes the popup window when touch outside
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            // Removes default background
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * Sets title for ActionSheet
     *
     * @param title the name of the ActionSheet view
     */
    public void setTitle(String title) {
        if (mContentView != null) {
            mContentView.setTitle(title);
        }
    }

    public String getTitle() {
        return mContentView.getTitle();
    }

    /**
     * Shows ActionSheet view after initialization.
     */
    public void show() {
        // Add arrow to actionsheet
        mContentView.addArrow(Arrow.DOWN);

        if (mAnchorView == null) return;
        if (mPopupWindow == null) return;

        Rect viewRect = ViewUtils.locateView(mAnchorView);
        if (viewRect == null) return;

        // Points SheetView to the center of its anchor view
        mContentView.measure(ContentView.DEFAULT_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
        int xPoint = viewRect.left + mAnchorView.getWidth() / 2 - mContentView.getMeasuredWidth() / 2; // left
        int yPoint = viewRect.top + mAnchorView.getHeight() / 2 - mContentView.getMeasuredHeight(); // top

        // Makes sure that action sheet is always shown inside screen.
        if ( yPoint <= ViewUtils.getStatusBarHeight(mContext)) {
            yPoint = ViewUtils.getStatusBarHeight(mContext);
        }

        if (mPopupWindow.isShowing()) {
            mPopupWindow.update(xPoint,
                    yPoint,
                    -1,
                    -1);
        } else {
            mPopupWindow.showAtLocation(mAnchorView.getRootView(),
                    Gravity.NO_GRAVITY,
                    xPoint,
                    yPoint);
        }
    }

    /**
     * Hides ActionSheet view when completion.
     */
    public void dismiss() {
        mPopupWindow.dismiss();
    }

    /**
     * Declares the position in which the ActionSheet view will be shown.
     *
     * @param anchorView an element view of the container which the ActionShet will point at.
     */
    public void setSourceView(View anchorView) {
        mAnchorView = anchorView;
    }

    /**
     * Allows user to add action during initialization process
     *
     * @param title    title of action
     * @param style    style of action
     * @param listener call back when users click action
     */
    public void addAction(String title, Style style, OnActionListener listener) {
        if (mContentView == null) return;
        if (listener == null) return;
        mContentView.addActionView(title, style, listener);
    }
}
