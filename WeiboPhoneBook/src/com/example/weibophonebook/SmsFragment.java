package com.example.weibophonebook;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
public class SmsFragment extends Fragment  
{  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
    	/*View v = inflater.inflate(R.layout.smsfragment, container, false);
    	View tv = v.findViewById(R.id.smsid); 
        ((TextView)tv).setText(getSmsInPhone());  
        Log.i("a",getSmsInPhone());
        return v;  */
    	   ScrollView scroller = new ScrollView(getActivity());
           TextView text = new TextView(getActivity());
           scroller.addView(text);
           text.setText(getSmsInPhone());
           return scroller;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
    public String getSmsInPhone() {  
        final String SMS_URI_ALL = "content://sms/";  
        final String SMS_URI_INBOX = "content://sms/inbox";  
        final String SMS_URI_SEND = "content://sms/sent";  
        final String SMS_URI_DRAFT = "content://sms/draft";  
        final String SMS_URI_OUTBOX = "content://sms/outbox";  
        final String SMS_URI_FAILED = "content://sms/failed";  
        final String SMS_URI_QUEUED = "content://sms/queued";  
  
        StringBuilder smsBuilder = new StringBuilder();  
  
        try {  
            Uri uri = Uri.parse(SMS_URI_ALL);  
            String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };  
            Cursor cur = getActivity().getContentResolver().query(uri, projection, null, null, "date desc");      // 获取手机内部短信  
  
            if (cur.moveToFirst()) {  
                int index_Address = cur.getColumnIndex("address");  
                int index_Person = cur.getColumnIndex("person");  
                int index_Body = cur.getColumnIndex("body");  
                int index_Date = cur.getColumnIndex("date");  
                int index_Type = cur.getColumnIndex("type");  
  
                do {  
                    String strAddress = cur.getString(index_Address);  
                    int intPerson = cur.getInt(index_Person);  
                    String strbody = cur.getString(index_Body);  
                    long longDate = cur.getLong(index_Date);  
                    int intType = cur.getInt(index_Type);  
  
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                    Date d = new Date(longDate);  
                    String strDate = dateFormat.format(d);  
  
                    String strType = "";  
                    if (intType == 1) {  
                        strType = "接收";  
                    } else if (intType == 2) {  
                        strType = "发送";  
                    } else {  
                        strType = "null";  
                    }  
  
                    smsBuilder.append("[ ");  
                    smsBuilder.append(strAddress + ", ");  
                    smsBuilder.append(intPerson + ", ");  
                    smsBuilder.append(strbody + ", ");  
                    smsBuilder.append(strDate + ", ");  
                    smsBuilder.append(strType);  
                    smsBuilder.append(" ]\n\n");  
                } while (cur.moveToNext());  
  
                if (!cur.isClosed()) {  
                    cur.close();  
                    cur = null;  
                }  
            } else {  
                smsBuilder.append("no result!");  
            } // end if  
  
            smsBuilder.append("getSmsInPhone has executed!");  
  
        } catch (SQLiteException ex) {  
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());  
        }  
  
        return smsBuilder.toString();  
    } 
}  