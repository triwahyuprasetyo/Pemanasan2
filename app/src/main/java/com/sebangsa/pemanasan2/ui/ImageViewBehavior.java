package com.sebangsa.pemanasan2.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by sebangsa on 9/9/16.
 */

public class ImageViewBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private float childY;

    public ImageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        childY = (float) 0.0;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        Log.i("SCALE X", child.getScaleX() + "");
        Log.i("SCALE Y", child.getScaleY() + "");
        setScaleImage(child);
        setOffsetValue(parent);
        return false;
    }

    private void setScaleImage(ImageView child) {
        if (-200 <= childY && childY <= -100) {
            float scale = (Math.abs(childY) - (float) 100) / (float) 100;
            child.setScaleX((float) 1.0 - scale);
            child.setScaleY((float) 1.0 - scale);
        } else if (childY < -200) {
            child.setScaleX((float) 0.0);
            child.setScaleY((float) 0.0);
        } else if (childY > -100) {
            child.setScaleX((float) 1.0);
            child.setScaleY((float) 1.0);
        }
    }

    private void setOffsetValue(CoordinatorLayout coordinatorLayout) {
        for (int i = 0; i < coordinatorLayout.getChildCount(); i++) {
            View child = coordinatorLayout.getChildAt(i);
            if (child instanceof AppBarLayout) {
                childY = child.getY();
                Log.i("child.getY() : ", childY + "");
                break;
            }
        }
    }
}
