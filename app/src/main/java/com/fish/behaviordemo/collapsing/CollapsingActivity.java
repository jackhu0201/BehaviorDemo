package com.fish.behaviordemo.collapsing;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fish.behaviordemo.R;
import com.fish.behaviordemo.util.Constant;
import com.fish.behaviordemo.util.ToastUtil;


public class CollapsingActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    COLLASP_MODE currentMode;
    Toolbar toolbar;
    boolean toolbarPinned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsing_activity);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);

        for (int i = 0; i < 30; i++) {
            TextView tv = new TextView(this);
            String s=Constant.getName(i);
            if(s==null){
                s=""+i;
            }
            tv.setText(s);
            tv.setTextSize(40);
            ll.addView(tv);
        }
        currentMode = COLLASP_MODE.SCROLL_DEFAULT;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collapsing, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (currentMode) {
            case SCROLL_DEFAULT:
                menu.findItem(R.id.scroll).setChecked(true);
                break;
            case SCROLL_EXIT_COLLAPSE:
                menu.findItem(R.id.scroll_exit).setChecked(true);
                break;

        }

        if (toolbarPinned) {
            menu.findItem(R.id.toolbar_pin).setTitle("toolbar_pin_cancel");
        } else {
            menu.findItem(R.id.toolbar_pin).setTitle("toolbar_pin");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.scroll_exit) {
            currentMode = COLLASP_MODE.SCROLL_EXIT_COLLAPSE;
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            collapsingToolbarLayout.setLayoutParams(params);
            ToastUtil.showToast(this, "scroll_exit_collapse");
            return true;

        } else if (id == R.id.scroll) {
            currentMode = COLLASP_MODE.SCROLL_DEFAULT;
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
            collapsingToolbarLayout.setLayoutParams(params);
            ToastUtil.showToast(this, "scroll");
            return true;
        } else if (id == R.id.toolbar_pin) {
            toolbarPinned = !toolbarPinned;

            CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            if (toolbarPinned) {
                params.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);
            } else {
                params.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF);
            }

            toolbar.setLayoutParams(params);
            toolbar.requestLayout();
            ToastUtil.showToast(this, "toolbarPinned " + toolbarPinned);
        }

        return super.onOptionsItemSelected(item);
    }

    static enum COLLASP_MODE {SCROLL_DEFAULT, SCROLL_EXIT_COLLAPSE}
}
