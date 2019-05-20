package com.jimmysun.ultrarecyclerview.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jimmysun.ultrarecyclerview.BannerView;
import com.jimmysun.ultrarecyclerview.Utils;

public class HorizontalActivity extends AppCompatActivity {
    private static final String[] GRAVITIES = new String[]{"Start", "Center", "End"};

    private BannerView mBannerView;
    private CheckBox mPagerSnapCheckBox;
    private Button mAlignGravityButton;
    private EditText mAlignMarginEditText;
    private CheckBox mInfiniteCheckBox;
    private CheckBox mAutoScrollCheckBox;
    private EditText mAutoScrollDurationEditText;
    private EditText mAutoScrollSpeedEditText;

    private int mAlignGravity = Gravity.CENTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        mBannerView = findViewById(R.id.banner_view);
        mBannerView.setAdapter(new MyAdapter(RecyclerView.HORIZONTAL));
        mPagerSnapCheckBox = findViewById(R.id.cb_pager_snap);
        mAlignGravityButton = findViewById(R.id.btn_gravity);
        mAlignGravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Select Gravity")
                        .setItems(GRAVITIES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAlignGravityButton.setText(GRAVITIES[which]);
                                switch (which) {
                                    case 0:
                                        mAlignGravity = Gravity.START;
                                        break;
                                    case 1:
                                        mAlignGravity = Gravity.CENTER;
                                        break;
                                    case 2:
                                        mAlignGravity = Gravity.END;
                                        break;
                                    default:
                                        mAlignGravity = Gravity.NO_GRAVITY;
                                }
                            }
                        }).create().show();
            }
        });
        mAlignMarginEditText = findViewById(R.id.et_margin);
        mInfiniteCheckBox = findViewById(R.id.cb_infinite);
        mAutoScrollCheckBox = findViewById(R.id.cb_auto_scroll);
        mAutoScrollDurationEditText = findViewById(R.id.et_duration);
        mAutoScrollSpeedEditText = findViewById(R.id.et_speed);
        findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPagerSnapCheckBox.isChecked()) {
                    int margin = 0;
                    try {
                        margin = Utils.dip2px(v.getContext(),
                                Integer.parseInt(mAlignMarginEditText.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mBannerView.setPagerSnap(mAlignGravity, margin);
                } else {
                    mBannerView.setPagerSnap(Gravity.NO_GRAVITY);
                }
                mBannerView.setInfiniteLoop(mInfiniteCheckBox.isChecked());
                if (mAutoScrollCheckBox.isChecked()) {
                    int duration = -1;
                    int speed = -1;
                    try {
                        duration =
                                Integer.parseInt(mAutoScrollDurationEditText.getText().toString());
                        speed = Integer.parseInt(mAutoScrollSpeedEditText.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mBannerView.startAutoScroll(duration);
                    mBannerView.setAutoScrollSpeed(speed);
                } else {
                    mBannerView.stopAutoScroll();
                }
                mBannerView.refresh();
            }
        });
    }
}
