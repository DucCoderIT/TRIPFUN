package trungduc.tripfun.Models;


public class Constants {
//    public static String ipconfig ="http://tripfun.000webhostapp.com/";
    public static String ipconfig ="http://192.168.1.126:80/source/TRIFUNDB/";
    public static String url_all_tripdetails_by_ori_des = ipconfig + "get_all_tripdetails_by_orides.php";
    public static String url_all_tripdetails_by_userid = ipconfig + "get_all_tripdetails_by_userid.php";
    public static String url_user_control = ipconfig + "user_control.php";
    public static String url_trip_control = ipconfig +"trip_control.php";
    public static String url_get_user = ipconfig + "get_userdetails.php";
    public static String url_join_trip = ipconfig + "join_trip.php";
    public static String url_check_join_trip = ipconfig + "check_join_trip.php";

    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_ERROR = "error";
    //SET TAG FOR TRIP +++=============================
    public static final String TAG_TRIPDETAILS = "tripdetails";
    public static final String TAG_TRIPDETAIL = "tripdetail";
    public static final String TAG_TRIPID = "tripID";
    public static final String TAG_TRIPUSERID = "ownerID";
    public static final String TAG_ORIGIN = "origin";
    public static final String TAG_DESTINATION = "destination";
    public static final String TAG_DATE = "date";
    public static final String TAG_TIME = "time";
    public static final String TAG_TYPEVEHICLE = "typevehicle";
    public static final String TAG_POSITION = "position";
    public static final String TAG_EMPTYSEAT = "emptyseat";
    public static final String TAG_FULLSEAT = "fullseat";
    public static final String TAG_SEATPRICE = "seatprice";
    public static final String TAG_SERVICE = "service";
    public static final String TAG_LUGGAGE = "luggage";
    public static final String TAG_SUGGEST = "suggest";
    public static final String TAG_PLAN = "plan";
    public static final String TAG_WGENDER = "wgender";
    public static final String TAG_TRIP_USERGENDER = "userGender";
    public static final String TAG_USER_EVALUATION = "userEvalua";
    //============================================
    public static final String TAG_USERDETAILS = "userdetails";
    public static final String TAG_USERID = "userID";
    public static final String TAG_USERNAME = "name";
    public static final String TAG_USERBIRTH = "birth";
    public static final String TAG_USERPHONENUMBER = "phonenumber";
    public static final String TAG_USERGENDER = "gender";
    public static final String TAG_USER_USERNAME = "username";
    public static final String TAG_USER_PASSWORD = "password";
    public static final String TAG_USEREMAIL = "email";
    public static final String TAG_USERSTATUS = "status";
    public static final String TAG_EVALUATION = "evaluation";



    public static final String TAG_DESCRIPTION = "description";
}
