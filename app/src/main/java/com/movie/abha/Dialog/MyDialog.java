package com.movie.abha.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.snackbar.Snackbar;

public class MyDialog {

    private static ProgressDialog progressDialog;

    public static void snackBar_Error_Bottom(View view, String message){

        Snackbar snackbar = Snackbar
                .make(view,message, Snackbar.LENGTH_LONG);

        snackbar.show();

    }

    public static void snackBar_Error_Top(View view, String message){

        Snackbar snackbar = Snackbar
                .make(view,message, Snackbar.LENGTH_LONG);

        View view1 = snackbar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view1.getLayoutParams();
        params.gravity = Gravity.TOP;
        view1.setLayoutParams(params);

        snackbar.show();

    }
    public static void snackBar_No_Internet(View view){

        Snackbar snackbar = Snackbar
                .make(view, "No internet connection!", Snackbar.LENGTH_LONG);

        snackbar.show();

        }
     public static void snackBar_SomethingWrong(View view){

            Snackbar snackbar = Snackbar
                    .make(view, "Something went wrong ,please try again!", Snackbar.LENGTH_LONG);

            snackbar.show();

        }

    private static AlertDialog dd;
    public static Dialog alertDialog(final Context context,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dd.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        dd = builder.create();
        dd.show();
        return dd;
    }


    public static void showProgressDialog(Context context,String message, Boolean cancelable){
         progressDialog = new ProgressDialog(context);
         progressDialog.setMessage(message);
         progressDialog.setCancelable(cancelable);
         progressDialog.show();
    }

    public static void hideProgressDialog(){
         if(progressDialog != null){
             progressDialog.dismiss();
         }
    }
}
