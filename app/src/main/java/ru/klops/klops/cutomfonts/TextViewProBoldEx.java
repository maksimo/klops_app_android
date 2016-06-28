package ru.klops.klops.cutomfonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.cutomfonts.singletons.ProBoldEx;

/**
 * TextView with Pro-BoldEx fonts
 */

public class TextViewProBoldEx extends TextView {
    public TextViewProBoldEx(Context context) {
        super(context);
        setTypeface(ProBoldEx.getINSTANCE(context).getTypeface());
    }

    public TextViewProBoldEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProBoldEx.getINSTANCE(context).getTypeface());
    }

    public TextViewProBoldEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProBoldEx.getINSTANCE(context).getTypeface());
    }
}
