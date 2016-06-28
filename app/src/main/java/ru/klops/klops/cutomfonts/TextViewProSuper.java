package ru.klops.klops.cutomfonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.cutomfonts.singletons.ProSuper;

/**
 * TextView with Pro-Super fonts
 */
public class TextViewProSuper extends TextView {
    public TextViewProSuper(Context context) {
        super(context);
        setTypeface(ProSuper.getINSTANCE(context).getTypeface());
    }

    public TextViewProSuper(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProSuper.getINSTANCE(context).getTypeface());
    }

    public TextViewProSuper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProSuper.getINSTANCE(context).getTypeface());
    }

}
