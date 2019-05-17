package com.jimmysun.ultrarecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Utils
 *
 * @author SunQiang
 * @since 2019-05-16
 */
public class Utils {
    public static int dip2px(Context context, float dipValue) {
        if (context != null) {
            try {
                return (int) (dipValue * getScreenDensity(context) + 0.5f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static float getScreenDensity(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
