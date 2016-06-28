package ru.klops.klops.cutomfonts;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.cutomfonts.singletons.ProXBd;
/**
 * TextView with Pro-Xbd fonts
 */
public class TextViewProXBd extends TextView {
    public TextViewProXBd(Context context) {
        super(context);
        setTypeface(ProXBd.getINSTANCE(context).getTypeface());
    }

    public TextViewProXBd(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProXBd.getINSTANCE(context).getTypeface());
    }

    public TextViewProXBd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProXBd.getINSTANCE(context).getTypeface());
    }

}
