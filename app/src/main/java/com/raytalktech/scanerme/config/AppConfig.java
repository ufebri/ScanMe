package com.raytalktech.scanerme.config;

import com.raytalktech.scanerme.BuildConfig;

public class AppConfig {

    //if you wanted to change, please remember to added *.db filetype
    public static String DB_NAME = "ScanMe.db";

    //Change this URL on LocalConfig
    public static String BASE_URL = BuildConfig.RELEASE_URL;

    /**
     * To change Icon, you can use this generator
     * https://romannurik.github.io/AndroidAssetStudio/index.html
     */

    /**
     * General Response Message
     */
    public static class GeneralResponseMessage {
        public static String RC_01 = "QR Tidak Sesuai";
        public static String RC_02 = "Anda Telah Terdaftar";
        public static String RC_03 = "There's Something Wrong with App, Calling App Center";
        public static String RC_04 = "Terima Kasih, Anda Telah terdaftar";
        public static String RC_05 = "Camera Permission Denied, Please Activate on Settings Menu";
    }
}
