package com.dong.yiping.view.datepicker;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dong.yiping.R;
import com.dong.yiping.utils.DisplayUtils;

/**
 * Created by wang on 2015/8/18.
 */
public class TimeDialog extends Dialog{

    private Context mContext;

    private Button leftButton,rightButton;
    private TextView titleText;
    private CustomTimeListener customTimeListener;
    public interface CustomTimeListener{
        public void setTime(String time);
    }

    public TimeDialog(Context context, CustomTimeListener listener){
        super(context);
        this.mContext = context;
        this.customTimeListener = listener;
        initView();
    }

    private void initView(){

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View timepickerview = LayoutInflater.from(mContext).inflate(R.layout.timepicker, null);
        setContentView(timepickerview);
        //ScreenInfo screenInfo = new ScreenInfo(mContext);
        final WheelMain wheelMain = new WheelMain(timepickerview);
        wheelMain.screenheight = DisplayUtils.getScreenH(mContext);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        wheelMain.initDateTimePicker(year, month, day);

        Button btn_ok = (Button) timepickerview.findViewById(R.id.btn_timepicker_ok);
        Button btn_cancel = (Button) timepickerview.findViewById(R.id.btn_timepicker_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(FormatUtils.datetimeToTimestamp(wheelMain.getTime()) > new Date().getTime()){
                    ToastUtils.show(mContext,"时间不合理...");
                }else{
                    customTimeListener.setTime(wheelMain.getTime());
                }*/
            	customTimeListener.setTime(wheelMain.getTime());
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
