package edu.uwp.alga.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Kelly on 11/4/2015.
 */
public class LabelTextView extends TextView {
    public LabelTextView(Context context) {
        super(context);
        init();
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   public void init(){
       Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"font/bariol_regular.otf");
       setTypeface(tf,1);

   }
}
