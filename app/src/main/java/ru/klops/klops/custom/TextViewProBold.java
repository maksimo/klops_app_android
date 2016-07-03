package ru.klops.klops.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.custom.singletons.ProBold;

/**
 * TextView with Pro-Bold fonts
 */
public class TextViewProBold extends TextView {
    public TextViewProBold(Context context) {
        super(context);
        setTypeface(ProBold.getINSTANCE(context).getTypeface());
    }

    public TextViewProBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProBold.getINSTANCE(context).getTypeface());
    }

    public TextViewProBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProBold.getINSTANCE(context).getTypeface());
    }
}
