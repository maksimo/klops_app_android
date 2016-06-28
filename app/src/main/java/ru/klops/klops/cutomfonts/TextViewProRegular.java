package ru.klops.klops.cutomfonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.cutomfonts.singletons.ProRegular;

/**
 * TextView with Pro-Bold fonts
 */
public class TextViewProRegular extends TextView {
    public TextViewProRegular(Context context) {
        super(context);
        setTypeface(ProRegular.getINSTANCE(context).getTypeface());
    }

    public TextViewProRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProRegular.getINSTANCE(context).getTypeface());
    }

    public TextViewProRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProRegular.getINSTANCE(context).getTypeface());
    }

}
