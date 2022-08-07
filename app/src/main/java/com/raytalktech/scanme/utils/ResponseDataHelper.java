package com.raytalktech.scanme.utils;


import com.raytalktech.scanme.config.AppConfig;
import com.raytalktech.scanme.data.BaseResponse;

public class ResponseDataHelper {

    public static BaseResponse.ResultData resultData = new BaseResponse.ResultData();

    public static BaseResponse.ResultData getResultDataScanSuccess() {
        return resultData = new BaseResponse.ResultData("Succees", "https://inilinknya.com", "00");
    }

    public static BaseResponse.ResultData getResultDataScanFailed() {
        return resultData = new BaseResponse.ResultData("Failed", AppConfig.GeneralResponseMessage.RC_01, "01");
    }

    public static BaseResponse.ResultData getShowResultLinkSuccessCheckIn() {
        return resultData = new BaseResponse.ResultData("Success", AppConfig.GeneralResponseMessage.RC_04, "04");
    }

    public static BaseResponse.ResultData getShowResultLinkHasBeenCheckIn() {
        return resultData = new BaseResponse.ResultData("Success", AppConfig.GeneralResponseMessage.RC_02, "02");
    }

    public static BaseResponse.ResultData getGeneralErrorResponse() {
        return resultData = new BaseResponse.ResultData("Failed", AppConfig.GeneralResponseMessage.RC_03, "03");
    }
}
