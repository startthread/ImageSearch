package com.codepath.startthread.imagesearch.helpers;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UiUtils {

	public static void showSoftKeyboard(Context context, View view){
	    if (view.requestFocus()){
	        InputMethodManager imm =(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
	    }
	}
	
	public static void hideSoftKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

}
