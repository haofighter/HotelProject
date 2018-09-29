package com.sun.hotelproject.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a'su's on 2018/5/18.
 */

public class pulicKey {

    public static List<Map<String,Object>> getKey(){
        List<Map<String,Object>>list =new ArrayList<>();
        Map<String,Object> map =new HashMap<>();
        map.put("id","1");
        map.put("key","279f71cb32f447cfa81a4c062e49d082");
       // map.put("key","ktdtuszdm5gq6jd0t17qhyqme6gvv1mt");
        list.add(map);
        Map<String,Object> map1 =new HashMap<>();
        map1.put("id","2");
        map.put("key","4263126a8da04bfa909248a46a495faf");
        //map1.put("key","4qdc0j0ocwfl1clumho68f4vy32zls03");
        list.add(map1);
        Map<String,Object> map2 =new HashMap<>();
        map2.put("id","3");
        map.put("key","cefe54c505fe42bc95a9e10f2cedbd82");
       // map2.put("key","bpo18eszbggw48e0r69833x302d2oa9d");
        list.add(map2);
        return list;
    }

    public static final String appid = "2212345678901234567890";

    //***********测试公钥***********/
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApRF1MkhzbXUt5rm8G8nI\n" +
            "3TQY1kZ3c1T7ov/g4KuwbS+eHTGGWlRa+jFfjIp+1oe0f6u4ZF/1S78WgrH2KLKU\n" +
            "OeaRPXi31zBClU2vx13wf+xXmNbYbjieozHd2NqDar5XWJEccjOp9/QlTxbqXacl\n" +
            "Kw1huAQu8CargUARHIelTQcCQd7kBtMye7iE4Vhtd58iWdnS2IueprfKSOrbIEGI\n" +
            "62pH1dhZ8zrB/9eC0jEbHOS5vaG+3e8EC+8K0tOydErqosOPpyf6wMH+F5NkZVjE\n" +
            "+hUrpjnfxQ49mW1mPGq5jaRwuQsF0IFVWB3SJ/UBGphBwaQAqCCGIAt+J6xd1Ofd\n" +
            "cwIDAQAB";

    //***********测试私钥***********/
    public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQClEXUySHNtdS3m\n" +
            "ubwbycjdNBjWRndzVPui/+Dgq7BtL54dMYZaVFr6MV+Min7Wh7R/q7hkX/VLvxaC\n" +
            "sfYospQ55pE9eLfXMEKVTa/HXfB/7FeY1thuOJ6jMd3Y2oNqvldYkRxyM6n39CVP\n" +
            "FupdpyUrDWG4BC7wJquBQBEch6VNBwJB3uQG0zJ7uIThWG13nyJZ2dLYi56mt8pI\n" +
            "6tsgQYjrakfV2FnzOsH/14LSMRsc5Lm9ob7d7wQL7wrS07J0Suqiw4+nJ/rAwf4X\n" +
            "k2RlWMT6FSumOd/FDj2ZbWY8armNpHC5CwXQgVVYHdIn9QEamEHBpACoIIYgC34n\n" +
            "rF3U591zAgMBAAECggEBAJ3KBWSumyA7zkfqriVGC2tgXImfzJmD2BNlU9qQZFLL\n" +
            "HPK9H7gDQQA+6B5ZWYvLJ0CPrvDMRZhFrsf6Q9t9cXGztIy1c88RA+Ti4S/L3ZT7\n" +
            "IHUqkrMyDnrjtTSRFOSZ1LcF59JHDqfGS0/CEQC7QS1ZWlrkLNZFsNYZnwHNrLbm\n" +
            "fjOdrj9ttYBgRWXevfoHkMFGTNZisO+HUdxmSMt1ztAhssCJw5nKT920b8g9H7GO\n" +
            "9kg+BiAZ1HClwbNAoOpl8AeYvl63coSelvtlEfrOeL9L1ZBGoM1QtG6cAlrJlyOn\n" +
            "fdlkNrqecrqPWeOyZzWNYzZVOiNHjBoY+o3PIkgUf8ECgYEA0OjLTv3xJvGQnqtu\n" +
            "gjfMclq+3e/bUjYBzeR3JpsikD5m6Ivkce3Z7qlGisfsHhpQf4t3N0yo47tQz/5l\n" +
            "YIHsoRk/U1DBsFw8YWAH8OMQ3tq9P+NZMKD5YyWwPAzeK6iExYZaBSPdc/qZMpfB\n" +
            "FtWB5VX5gigNbZLWWYEb4SxOHiECgYEAykbKVpo3MHnnvFdEKt8I37bDhALv/eg7\n" +
            "jVE7q/sBumMjKSSss1AACFvl1UIS1yampkBisP6po8z6dUsyRzICpOcODrYJxQuc\n" +
            "HheUKGOq+akQi2hnfFGaDNozAQ6b39vaazKNXmvAbSorDUlmnHo7g/MItDOYU7QZ\n" +
            "/IgGJV5NgRMCgYBYwqAnJsQAPOCqWcIxN435BE9WMqPn0SSBG1H4qC9ORAUrRszA\n" +
            "D0PqICflrT3IKHwNRO9ZIowbfim+xaLDF1C3vedm+cfhq77HzKpzEbbISP3hmDH7\n" +
            "9FkhZBD14q2+tPH8jrHOv0dHELFoiouyrPJsw1OolmXmKV5DVxTGXjGuoQKBgQCo\n" +
            "m0Fl1r5SJgtILuWfZrIa6Fko3Nn3J3EcZq36uQvDtYdFcFCUVwbv8MovbRgPRyR6\n" +
            "SAj7yArXgrDyvG94cPNLSV2qXYrfOMI1Qyo4UHvgB1DHI4u/ULW8KNWC+A5uFvCF\n" +
            "kmnMXrzTQecI+DoUDL83SYnjy24Zt3JB0ZBt7aFlCQKBgAwUypFSnOdDoy76YCpL\n" +
            "MRfUQo/VqJcQZhFWJOrbJdFv9GP6kNAHg3hRAJ6Uxw9kfq1NUGpQsxRdaCbJKxCc\n" +
            "d81f8n31/OZFIbzEsS/546XGDFGud4+FysxO6qPN7Y52xapHcABkPGCYcf+WhND0\n" +
            "0VjlLbV1+lKmpSvg6+4QIr6T";
}
