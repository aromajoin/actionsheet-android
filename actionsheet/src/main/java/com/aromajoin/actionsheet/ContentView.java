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
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Quang Nguyen on 3/16/17.
 *
 * Contains ActionSheet UI components
 */

class ContentView extends LinearLayout {
    static final int DEFAULT_WIDTH = ViewUtils.getScreenWidth() / 3;
    private Button mTitleView;
    private ActionSheet mActionSheet;

    // Custom styleable properties
    private int mTitleColor;
    private int mDefaultActionColor;
    private int mDestructiveActionColor;

    public ContentView(ActionSheet actionSheet) {
        super(actionSheet.getContext());
        mActionSheet = actionSheet;

        init(actionSheet.getContext());
    }

    private void init(Context context) {
        getAttrs(context);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        // Adds action sheet title
        mTitleView = new Button(context);
        FrameLayout.LayoutParams titleLp = new FrameLayout.LayoutParams(DEFAULT_WIDTH, FrameLayout.LayoutParams.WRAP_CONTENT);
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setClickable(false);
        int paddingHorizontal = (int) (getContext().getResources().getDimension(R.dimen.title_padding_vertical));
        mTitleView.setPadding(0, paddingHorizontal, 0, paddingHorizontal);
        mTitleView.setBackgroundColor(Color.WHITE);
        mTitleView.setTextColor(mTitleColor);
        addView(mTitleView, titleLp);
    }

    private void getAttrs(Context context) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(R.styleable.ContentView);

        mTitleColor = typedArray.getColor(R.styleable.ContentView_asTitleColor,
                ContextCompat.getColor(getContext(), R.color.black));
        mDefaultActionColor = typedArray.getColor(R.styleable.ContentView_asDefaultColor,
                ContextCompat.getColor(getContext(), R.color.blue));
        mDestructiveActionColor = typedArray.getColor(R.styleable.ContentView_asDestructiveColor,
                ContextCompat.getColor(getContext(), R.color.red));

    }

    public void setTitle(String title) {
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    public String getTitle() throws NullPointerException {
        if (mTitleView != null) {
            return mTitleView.getText().toString();
        } else {
            throw new NullPointerException();
        }
    }

    public void addActionView(final String title, ActionSheet.Style style, final OnActionListener listener) {
        // Adds its divider above
        addDividerView();

        Button actionButton = new Button(getContext());
        FrameLayout.LayoutParams buttonLp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        addView(actionButton, buttonLp);

        // Sets title
        actionButton.setText(title);

        // Sets text color based on action style
        if (style == ActionSheet.Style.DEFAULT) {
            actionButton.setTextColor(mDefaultActionColor);
        } else if (style == ActionSheet.Style.DESTRUCTIVE) {
            actionButton.setTextColor(mDestructiveActionColor);
        }

        // Sets its background
        actionButton.setBackgroundResource(R.drawable.bg_as_action_touch);

        //Sets button callback to handle to user's click
        actionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelected(mActionSheet, title);
            }
        });
    }

    /**
     * Draws a line between action buttons
     */
    private void addDividerView() {
        int dividerHeight = (int) (getContext().getResources().getDimension(R.dimen.divider_height));
        int marginHorizontal = (int) (getContext().getResources().getDimension(R.dimen.divider_margin_horizontal));

        View divider = new View(getContext());
        LayoutParams dividerBarLp = new LayoutParams(LayoutParams.MATCH_PARENT, dividerHeight);
        dividerBarLp.setMargins(marginHorizontal, 0, marginHorizontal, 0);

        addView(divider, dividerBarLp);
        divider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray));
    }

    private ImageView mArrowView;
    public void addArrow(Arrow style) {
        if (mArrowView != null){
            return;
        }
        mArrowView = new ImageView(getContext());
        switch (style) {
            case UP:
                break;
            case DOWN:
                mArrowView.setBackgroundResource(R.drawable.ic_down);
                break;
            case RIGHT:
                break;
            case LEFT:
                break;
        }
        LayoutParams arrowLp = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mArrowView, arrowLp);
    }

}
