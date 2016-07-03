package ru.klops.klops.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.custom.singletons.ProLight;

/**
 * TextView with Pro-Light fonts
 */
public class TextViewProLight extends TextView {
    public TextViewProLight(Context context) {
        super(context);
        setTypeface(ProLight.getINSTANCE(context).getTypeface());
    }

    public TextViewProLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProLight.getINSTANCE(context).getTypeface());
    }

    public TextViewProLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProLight.getINSTANCE(context).getTypeface());
    }
}
