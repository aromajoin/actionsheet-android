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
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Quang Nguyen on 3/16/17.
 *
 * Contains ActionSheet UI components
 */

class ContentView extends LinearLayout {
  private TextView mTitleView;
  private LinearLayout mActionContainer;
  private ActionSheet mActionSheet;

  // Custom styleable properties
  private float mWidth;
  private int mTitleColor;
  private int mDefaultActionColor;
  private int mDestructiveActionColor;
  private float mTitleTextSize;
  private float mActionTextSize;

  public ContentView(ActionSheet actionSheet) {
    super(actionSheet.getContext(), null, R.style.DefaultTheme);
    mActionSheet = actionSheet;

    init(actionSheet.getContext());
  }

  private void init(Context context) {
    getAttrs(context);

    setOrientation(VERTICAL);
    setGravity(Gravity.CENTER);
    addTitle(context);
    addActionContainer(context);
  }

  private void getAttrs(Context context) {
    TypedArray typedArray = context.getTheme().obtainStyledAttributes(R.styleable.ContentView);

    mWidth = typedArray.getDimension(R.styleable.ContentView_asWidth,
        getResources().getDimension(R.dimen.actionsheet_width));
    mTitleColor = typedArray.getColor(R.styleable.ContentView_asTitleColor,
        ContextCompat.getColor(getContext(), R.color.black));
    mDefaultActionColor = typedArray.getColor(R.styleable.ContentView_asDefaultColor,
        ContextCompat.getColor(getContext(), R.color.blue));
    mDestructiveActionColor = typedArray.getColor(R.styleable.ContentView_asDestructiveColor,
        ContextCompat.getColor(getContext(), R.color.red));
    mTitleTextSize = typedArray.getDimension(R.styleable.ContentView_asTitleTextSize,
        getResources().getDimension(R.dimen.title_text_size));
    mActionTextSize = typedArray.getDimension(R.styleable.ContentView_asActionTextSize,
        getResources().getDimension(R.dimen.action_text_size));
  }

  private void addTitle(Context context) {
    mTitleView = new TextView(context);
    FrameLayout.LayoutParams titleLp =
        new FrameLayout.LayoutParams((int) mWidth, FrameLayout.LayoutParams.WRAP_CONTENT);
    mTitleView.setGravity(Gravity.CENTER);
    mTitleView.setClickable(false);
    int paddingVertical =
        (int) (getContext().getResources().getDimension(R.dimen.title_padding_vertical));
    mTitleView.setPadding(0, paddingVertical, 0, paddingVertical);
    mTitleView.setBackgroundColor(Color.WHITE);
    mTitleView.setTextColor(mTitleColor);
    mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
    addView(mTitleView, titleLp);
  }

  private void addActionContainer(Context context) {
    mActionContainer = new LinearLayout(context);
    LinearLayout.LayoutParams actionContainerLp = new LinearLayout.LayoutParams((int) mWidth,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    mActionContainer.setBackgroundColor(Color.WHITE);
    mActionContainer.setOrientation(VERTICAL);
    addView(mActionContainer, actionContainerLp);
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

  public void addActionView(final String title, ActionSheet.Style style,
      final OnActionListener listener) {
    // Adds its divider above
    addDividerView();

    Button actionButton = new Button(
        new android.view.ContextThemeWrapper(getContext(), R.style.DefaultTheme_Action), null,
        R.style.DefaultTheme_Action);

    FrameLayout.LayoutParams buttonLp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT);
    mActionContainer.addView(actionButton, buttonLp);

    actionButton.setText(title);
    actionButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActionTextSize);

    // Sets text color based on action style
    if (style == ActionSheet.Style.DEFAULT) {
      actionButton.setTextColor(mDefaultActionColor);
    } else if (style == ActionSheet.Style.DESTRUCTIVE) {
      actionButton.setTextColor(mDestructiveActionColor);
    }

    //Sets button callback to handle to user's click
    actionButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        listener.onSelected(mActionSheet, title);
      }
    });
  }

  /**
   * Draws a line between action buttons
   */
  private void addDividerView() {
    int dividerHeight = (int) (getContext().getResources().getDimension(R.dimen.divider_height));

    View divider = new View(getContext());
    LayoutParams dividerBarLp = new LayoutParams(LayoutParams.MATCH_PARENT, dividerHeight);

    mActionContainer.addView(divider, dividerBarLp);
    divider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray));
  }

  private ImageView mArrowView;

  public void addArrow(Arrow style) {
    if (mArrowView != null) {
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
    LayoutParams arrowLp =
        new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    addView(mArrowView, arrowLp);
  }
}
