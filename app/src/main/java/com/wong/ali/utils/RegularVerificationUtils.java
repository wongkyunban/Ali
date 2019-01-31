package com.wong.ali.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WongKyunban
 * description
 * created at 2019-01-31 上午10:11
 * @version 1.0
 */
public class RegularVerificationUtils {

    //make sure that is ip address or not
    public static boolean isIpAddr(String str) {
        /*正则表达式*/
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";//限定输入格式


        Pattern p = Pattern
                .compile(ip);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //make sure that is domain name  or not

    public static boolean isDomainName(String str){
        Pattern p = Pattern.compile("[a-zA-Z0-9][-a-zA-Z0-9]{1,62}(.[a-zA-Z0-9][-a-zA-Z0-9]{1,62})+.?");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean startWithIP(String param){
        String str;
        int last = param.indexOf("/");
        if(last > 0) {
            str = param.substring(0, last);
        }else{
            str = param;
        }
       return isIpAddr(str);
    }

    public static boolean startWithDomainName(String param){

        String str;
        int last = param.indexOf("/");
        if(last > 0) {
            str = param.substring(0, last);
        }else{
            str = param;
        }

       return isDomainName(str);
    }



}
