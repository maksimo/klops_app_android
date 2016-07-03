package ru.klops.klops.custom;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * This Cache class is a basic implementation of caching the fonts from assets
 * This method uses HashTable for storing the fonts , associated with the name
 * In case if , we need to load an existing font type, method get will check for existing in Hash
 * If typeface doesn't exist, new object will be stored in hash
 * Else if font exists, method will return existing font and load it for current TextView
 */
public class FontCache {
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    /**
     *
     * @param name of font from assets package
     * @param context current context
     * @return typeface for current TextView
     */
    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
