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
    private float temp;
    private float scale;

    public ImageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        childY = (float) 0.0;
        scale = (float) 0;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {

        Log.i("SCALE X", child.getScaleX() + "");
        Log.i("SCALE Y", child.getScaleY() + "");

        if (-200 < childY && childY < -150) {
            if (childY < temp) {
                Log.i("Kurang", "Kurang");
                kurangScale();
            }
        } else if (-151 < childY && childY < -100) {
            if (childY > temp) {
                Log.i("Tambah", "Tambah");
                tambahScale();
            }
        }
        if (childY == (float) 0.0) {
            Log.i("True", "True");
            child.setScaleX((float) 1.0);
            child.setScaleY((float) 1.0);
        } else {
            if (scale > (float) 0.3) {
                child.setScaleX(scale);
                child.setScaleY(scale);
            } else {
                child.setScaleX((float) 0.0);
                child.setScaleY((float) 0.0);
            }
        }
        temp = childY;
        setOffsetValue(parent);
        return false;
    }

    private float tambahScale() {
        if (scale < (float) 1.0) {
            scale += (float) 0.1;
        } else {
            scale = (float) 1.0;
        }
        return scale;
    }

    private float kurangScale() {
        if (scale > (float) 0.0) {
            scale -= (float) 0.1;
        } else {
            scale = (float) 0.0;
        }
        return scale;
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
