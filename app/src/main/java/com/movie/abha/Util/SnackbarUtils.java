package com.movie.abha.Util;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;


public final class SnackbarUtils {

    public static void snackBarBottom(View view, String message){

        Snackbar snackbar = Snackbar
                .make(view,message, Snackbar.LENGTH_LONG);

        snackbar.show();

    }

    public static void snackBarTop(View view, String message){

        Snackbar snackbar = Snackbar
                .make(view,message, Snackbar.LENGTH_LONG);

        View view1 = snackbar.getView();

        try {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
            params.gravity = Gravity.TOP;
            view1.setLayoutParams(params);
        }catch (Exception e){

            try {
                DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) view1.getLayoutParams();
                params.gravity = Gravity.TOP;
                view1.setLayoutParams(params);
            }catch (Exception e1){

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view1.getLayoutParams();
                params.gravity = Gravity.TOP;
                view1.setLayoutParams(params);

            }

        }


        snackbar.show();

    }

}
