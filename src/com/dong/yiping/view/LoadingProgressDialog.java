package com.dong.yiping.view;

import com.dong.yiping.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Window;
import android.widget.TextView;


public class LoadingProgressDialog extends Dialog {

    private Context mContext;
    private String message;

	public LoadingProgressDialog(Context context, String strMessage) {
        super(context);

        this.mContext = context;
        this.message = strMessage;

        initView();
	}

    private void initView(){

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new BitmapDrawable());

        setContentView(R.layout.progressdialog_item);
        setCanceledOnTouchOutside(false);

        TextView tvMsg = (TextView) this.findViewById(R.id.dialog_text);
        if (tvMsg != null) {
            tvMsg.setText(message);
        }
    }

}
