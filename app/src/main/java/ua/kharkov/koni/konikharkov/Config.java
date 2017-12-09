package ua.kharkov.koni.konikharkov;

class Config {
    //JSON URL
    static final String MARKA_URL = "http://www.koni.kharkov.ua/android/getMarka.php";
    static final String MODEL_URL = "http://www.koni.kharkov.ua/android/getModel.php";
    static final String CAR_URL = "http://www.koni.kharkov.ua/android/getCars.php";
    static final String AMORT_URL = "http://www.koni.kharkov.ua/android/getAmort.php";
    static final String SEARCH_URL = "http://www.koni.kharkov.ua/android/getSearch.php?id=";

    //Tags used in the JSON String
    static final String MARKA_ID = "marka_id";
    static final String MARKA_NAME = "marka_name";

    static final String MODEL_ID = "model_id";
    static final String MODEL_NAME = "model_name";

    static final String CAR_ID = "car_id";
    static final String CAR_NAME = "car_name";

}