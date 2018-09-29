package com.net.md5;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetSign {
    public static String genPackageSign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.KEY_START);
        for (String key : params.keySet()) {
            sb.append(key);
            sb.append(params.get(key));
        }
        sb.append(Constant.KEY_END);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        return exChange(packageSign);
    }


    public static String exChange(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(Character.toLowerCase(c));
                } else if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    // 判断邮箱格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean isMobile(String mobiles) {

        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

}
