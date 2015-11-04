package edu.uwp.alga;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Kelly on 11/4/2015.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   public void init(){
       Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"font/bariol_regular.otf");
       setTypeface(tf,1);

   }
}
