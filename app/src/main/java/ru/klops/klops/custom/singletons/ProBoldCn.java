package ru.klops.klops.custom.singletons;

import android.content.Context;
import android.graphics.Typeface;

import ru.klops.klops.custom.FontCache;

public class ProBoldCn {
    private static ProRegular INSTANCE;
    private static Typeface typeface;

    public static ProRegular getINSTANCE(Context context) {
        synchronized (ProRegular.class){
            if (INSTANCE == null){
                INSTANCE = new ProRegular();
                typeface = FontCache.get("fonts/akzidenzgroteskpro-boldcn.ttf", context);
            }
        }
        return INSTANCE;
    }

    public Typeface getTypeface() {
        return typeface;
    }
}
