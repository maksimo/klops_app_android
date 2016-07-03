package ru.klops.klops.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.custom.singletons.ProMd;

/**
 * TextView with Pro-Md fonts
 */
public class TextViewProMd extends TextView {
    public TextViewProMd(Context context) {
        super(context);
        setTypeface(ProMd.getINSTANCE(context).getTypeface());
    }

    public TextViewProMd(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProMd.getINSTANCE(context).getTypeface());
    }

    public TextViewProMd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProMd.getINSTANCE(context).getTypeface());
    }

}
