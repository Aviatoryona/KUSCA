package com.aviator.kusca.net;

/**
 * Created by Aviator on 11/26/2017.
 */
@SuppressWarnings("ALL")
public interface Constants {
    String port="55";
//    String MEMBER_PIC_URL="http://192.168.101.1:9190/kusca-realajax/memberpics/";
//    String OTHER_PIC_URL="http://192.168.101.1:9190/kusca-realajax/pictures/";
//    String API_URL="http://192.168.101.1:9190/kusca-realajax/init.php";

//    TETHERING
    String API_URL="http://192.168.42."+port+":9190/kusca-realajax/init.php/?";
    String OTHER_PIC_URL="http://192.168.42."+port+":9190/kusca-realajax/pictures/";
     String MEMBER_PIC_URL="http://192.168.42."+port+":9190/kusca-realajax/memberpics/";
}
