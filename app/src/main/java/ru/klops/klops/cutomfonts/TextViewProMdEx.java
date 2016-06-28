package ru.klops.klops.cutomfonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.klops.klops.cutomfonts.singletons.ProMdEx;

/**
 * TextView with Pro-MdEx fonts
 */
public class TextViewProMdEx extends TextView {
    public TextViewProMdEx(Context context) {
        super(context);
        setTypeface(ProMdEx.getINSTANCE(context).getTypeface());
    }

    public TextViewProMdEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(ProMdEx.getINSTANCE(context).getTypeface());
    }

    public TextViewProMdEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(ProMdEx.getINSTANCE(context).getTypeface());
    }

}
