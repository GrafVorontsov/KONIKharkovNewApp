package ua.kharkov.koni.konikharkov.config;

public class Config {
    //JSON URL
    public static final String MARKA_URL = "http://www.koni.kharkov.ua/android/getMarka.php";
    public static final String MODEL_URL = "http://www.koni.kharkov.ua/android/getModel.php";
    public static final String CAR_URL = "http://www.koni.kharkov.ua/android/getCars.php";
    //public static final String AMORT_URL_WITH_LOGIN = "http://www.koni.kharkov.ua/android/getAmortWithLogin.php";
    public static final String AMORT_URL = "http://www.koni.kharkov.ua/android/getAmortNew.php";
    //public static final String SEARCH_URL_WITH_LOGIN = "http://www.koni.kharkov.ua/android/getSearchWithLogin.php?id=";
    public static final String SEARCH_URL = "http://www.koni.kharkov.ua/android/getSearchNew.php?id=";

    //Tags used in the JSON String
    public static final String MARKA_ID = "marka_id";
    public static final String MARKA_NAME = "marka_name";

    public static final String MODEL_ID = "model_id";
    public static final String MODEL_NAME = "model_name";

    public static final String CAR_ID = "car_id";
    public static final String CAR_NAME = "car_name";

    // Server user login url
    public static String URL_LOGIN = "http://www.koni.kharkov.ua/android/android_login_api/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://www.koni.kharkov.ua/android/android_login_api/register.php";
    public static String URL_CHECK = "http://www.koni.kharkov.ua/android/android_login_api/check.php";
    public static String PRICE_URL = "http://www.koni.kharkov.ua/android/price.php";
}