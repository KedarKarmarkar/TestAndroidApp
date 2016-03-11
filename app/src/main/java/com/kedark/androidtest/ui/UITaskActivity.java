package com.kedark.androidtest.ui;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kedark.androidtest.R;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class UITaskActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewSection4;
    Button btnRed,btnBlue,btnGreen;
    LinearLayout layoutSection5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitask);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        textViewSection4=(TextView)findViewById(R.id.textview_section_4);
        btnRed=(Button)findViewById(R.id.btn_red);
        btnBlue=(Button)findViewById(R.id.btn_blue);
        btnGreen=(Button)findViewById(R.id.btn_green);
        layoutSection5=(LinearLayout)findViewById(R.id.layout_section_5);
        final ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");
        ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.horizontal_list_item, items);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textViewSection4.setText(items.get(position));
            }
        });
        lvTest.setAdapter(aItems);
        btnRed.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        /*LayoutInflater inflater = (LayoutInflater)this.getLayoutInflater();

        View view = inflater.inflate(R.layout.comments, _innerLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.commentsHeader);
        view.setLayoutParams(params);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_uitask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_red:
                layoutSection5.setBackgroundColor(UITaskActivity.this.getResources().getColor(R.color.red));
                break;
            case R.id.btn_blue:
                layoutSection5.setBackgroundColor(UITaskActivity.this.getResources().getColor(R.color.blue));
                break;
            case R.id.btn_green:
                layoutSection5.setBackgroundColor(UITaskActivity.this.getResources().getColor(R.color.green));
                break;

        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return FirstFragment.newInstance("FirstFragment, Instance 1");
                case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
                case 2: return ThirdFragment.newInstance("ThirdFragment, Instance 1");
                case 3: return ThirdFragment.newInstance("FourthFragment, Instance 1");
                default: return ThirdFragment.newInstance("FirstFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
