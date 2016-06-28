package ru.klops.klops.cutomfonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.cutomfonts.singletons.ProBoldCn;

/**
 * TextView with Pro-BoldCn fonts
 */
public class TextViewProBoldCn extends TextView {
    public TextViewProBoldCn(Context context) {
        super(context);
        setTypeface(ProBoldCn.getINSTANCE(context).getTypeface());
    }

    public TextViewProBoldCn(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProBoldCn.getINSTANCE(context).getTypeface());
    }

    public TextViewProBoldCn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProBoldCn.getINSTANCE(context).getTypeface());
    }
}
