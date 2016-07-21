package com.example.freatnor.toolbardemo;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChildActivity extends AppCompatActivity {
    
    private Toolbar mToolbar;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        mLayout = (RelativeLayout) findViewById(R.id.child_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_child_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onNavigateUp();
                return true;
            case R.id.action_cart:
                mLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
                return true;
            case R.id.action_tellah:
                Toast.makeText(ChildActivity.this, "YOU SPOONY BARD!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_whatever:
                Toast.makeText(ChildActivity.this, "...A speech? Forget it. Cut the mic!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_bees:
                mLayout.setBackground(getResources().getDrawable(R.drawable.bee_honeycomb_03, this.getTheme()));
        }

        return super.onOptionsItemSelected(item);
    }


}
