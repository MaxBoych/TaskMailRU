package com.example.lazyuser.config;

public class AppConfig {

    public enum LoadStageState {
        SUCCESS,
        FAIL,
        PROGRESS,
        NONE
    }

    public final static String DEFAULT_ERROR_VALUE = "";
    public final static String URL_FIRST_PART = "https://www.google.ru/search?q=";
    public final static String URL_SECOND_PART = "&tbm=isch&bih=916&biw=1365&safe=strict";

    public final static String DOWNLOADED_SUCCESS = "Image download SUCCESSFUL";
    public final static String RELATED_DOWNLOADED_SUCCESS = "Related images download SUCCESSFUL";
    public final static String DOWNLOADED_FAIL = "Image download FAIL";
    public final static String RELATED_DOWNLOADED_FAIL = "Related images download FAIL";

    public final static String APPLICATION_TAG = "LazyUser";

    public final static String WORD_KEY = "word";
    public final static String HTML_KEY = "html";

    public final static String IMAGE_CLASS_NAME = "isv-r PNCib MSM1fd BUooTd";
    public final static String IMAGE_SIZE_CLASS_NAME = "O1vY7";
    public final static String IMAGE_URL_CLASS_NAME = "VFACy kGQAp";
    public final static String IMAGE_SOURCE_CLASS_NAME = "rg_i Q4LuWd tx8vtf";
    public final static String IMAGE_URL_ATTR_NAME = "href";
    public final static String IMAGE_SOURCE_ATTR_NAME = "data-iurl";
    public final static String RELATED_IMAGE_SOURCE_ATTR_NAME = "src";
    public final static String RELATED_IMAGE_SOURCE_TAG_NAME = "img";

    public final static int REQUEST_CODE_SUCCESS = 0;
    public final static int REQUEST_CODE_FAIL = 1;

    public final static int DATEPICKER_FRAGMENT = 1;
}
