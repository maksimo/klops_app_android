package ru.klops.klops.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.custom.singletons.ProCn;

/**
 * TextView with Pro-Cn fonts
 */
public class TextViewProCn extends TextView {
    public TextViewProCn(Context context) {
        super(context);
        setTypeface(ProCn.getINSTANCE(context).getTypeface());
    }

    public TextViewProCn(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProCn.getINSTANCE(context).getTypeface());
    }

    public TextViewProCn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProCn.getINSTANCE(context).getTypeface());
    }
}
