package com.wong.ali.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import static java.lang.reflect.Modifier.PRIVATE;

/**
 * @author WongKyunban
 * description
 * created at 2019-01-29 上午10:08
 * @version 1.0
 */
public class MyButterKnife {

    public static void init(Activity activity) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        View source = activity.getWindow().getDecorView();

        for (Field field : fields) {
            boolean isExits = field.isAnnotationPresent(OnMyBindView.class);

            if (isExits) {
                bindView(activity,field, source);
            }


        }
    }

    private static void bindView(Object object,Field field, View source) {

        if(field == null){
            return;
        }

        if ((field.getModifiers() & PRIVATE) != 0) {
            throw new IllegalArgumentException(object.getClass().getName() + " must not be private.");
        }

        OnMyBindView onMyBindView = field.getAnnotation(OnMyBindView.class);
        int idx = onMyBindView.value();
        View view = source.findViewById(idx);
        try {
            field.setAccessible(true);
            field.set(object,view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
