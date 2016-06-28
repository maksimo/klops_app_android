package ru.klops.klops.cutomfonts.singletons;

import android.content.Context;
import android.graphics.Typeface;

import ru.klops.klops.cutomfonts.FontCache;

public class ProMd {
    private static ProRegular INSTANCE;
    private static Typeface typeface;

    public static ProRegular getINSTANCE(Context context) {
        synchronized (ProRegular.class){
            if (INSTANCE == null){
                INSTANCE = new ProRegular();
                typeface = FontCache.get("fonts/akzidenzgroteskpro-md.ttf", context);
            }
        }
        return INSTANCE;
    }

    public Typeface getTypeface() {
        return typeface;
    }
}
