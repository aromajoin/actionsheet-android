package com.aromajoin.actionsheetsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aromajoin.actionsheet.ActionSheet;
import com.aromajoin.actionsheet.OnActionListener;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.show);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        showActionSheet(view);
      }
    });
  }

  private void showActionSheet(View anchor) {
    ActionSheet actionSheet = new ActionSheet(this);
    actionSheet.setTitle("Example Title");
    actionSheet.setSourceView(anchor);
    actionSheet.addAction("Action 1", ActionSheet.Style.DEFAULT, new OnActionListener() {
      @Override public void onSelected(ActionSheet actionSheet, String title) {
        performAction(title);
        actionSheet.dismiss();
      }
    });
    actionSheet.addAction("Action 2", ActionSheet.Style.DEFAULT, new OnActionListener() {
      @Override public void onSelected(ActionSheet actionSheet, String title) {
        performAction(title);
        actionSheet.dismiss();
      }
    });

    actionSheet.addAction("Action 3", ActionSheet.Style.DESTRUCTIVE, new OnActionListener() {
      @Override public void onSelected(ActionSheet actionSheet, String title) {
        performAction(title);
        actionSheet.dismiss();
      }
    });

    actionSheet.show();
  }

  private void performAction(String title) {
    Snackbar.make(MainActivity.this.findViewById(android.R.id.content), title,
        Snackbar.LENGTH_SHORT).show();
  }
}
