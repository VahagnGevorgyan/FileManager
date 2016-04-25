package com.filemanager.ui.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.filemanager.application.AppGlobals;
import com.filemanager.utils.Typefaces;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class CustomTextView extends TextView {

	private Context context;
	private String fontName = null;
	
	public CustomTextView(Context context) {
		super(context);
		this.context = context;
		initilaize();
	}
	
	public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            this.fontName = attrs.getAttributeValue(AppGlobals.CUSTOM_ATTR_SCHEMAS, "font");
            initilaize();
        }
    }
	
	private void initilaize() {
        if (null == this.fontName) {
            this.fontName = AppGlobals.DEFAULT_FONT;
        }
        if(!isInEditMode()){
        	Typeface font = Typefaces.get(this.context, this.fontName);
        	setTypeface(font);
        }
    }

	@Override
	public void setTypeface(Typeface tf) {
		super.setTypeface(tf);
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
	}
	
	

}
