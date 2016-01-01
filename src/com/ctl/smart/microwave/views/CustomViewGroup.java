package com.ctl.smart.microwave.views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomViewGroup extends LinearLayout{

    // =============================================================================
    // Child views
    // =============================================================================

    private TextView textView;

    private ImageView imageView;


    // =============================================================================
    // Constructor
    // =============================================================================

    public CustomViewGroup(Context context) {
        super(context);

        this.setOrientation(VERTICAL);

        this.textView = new TextView(context);
        this.imageView = new ImageView(context);

        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.textView.setLayoutParams(layoutParams);
        this.imageView.setLayoutParams(layoutParams);

        this.textView.setGravity(Gravity.CENTER);

        this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.imageView.setAdjustViewBounds(true);


        this.addView(this.textView);
        this.addView(this.imageView);
    }

    // =============================================================================
    // Getters
    // =============================================================================

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
