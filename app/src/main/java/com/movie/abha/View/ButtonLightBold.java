package com.movie.abha.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.movie.user.R;


/**
* Created by ebabu on 30/4/15.
*/
public class ButtonLightBold extends Button {
private Typeface tf = null;
private String customFont;
public ButtonLightBold(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setCustomFontTextView(context, attrs);
}

public ButtonLightBold(Context context, AttributeSet attrs) {
    super(context, attrs);
    setCustomFontTextView(context, attrs);
}

public ButtonLightBold(Context context) {
    super(context);

}
public boolean setCustomFontTextView(Context ctx, String asset) {
    try {
        tf = Typeface.createFromAsset(ctx.getAssets(), "myriad.ttf");
    } catch (Exception e) {
        return false;
    }
    setTypeface(tf);
    return true;
}

private void setCustomFontTextView(Context ctx, AttributeSet attrs) {
    final TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.EditTextLightBold);
    customFont = a.getString(R.styleable.EditTextLightBold_edittextfont);
    setCustomFontTextView(ctx, customFont);
    a.recycle();
}

}