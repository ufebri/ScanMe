package com.raytalktech.scanme.utils;


import com.raytalktech.scanme.config.AppConfig;
import com.raytalktech.scanme.config.Constant;
import com.raytalktech.scanme.data.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class ResponseDataHelper {

    public static List<BaseResponse.GeneralData> getListIntroduction() {
        List<BaseResponse.GeneralData> listResultData = new ArrayList<>();
        listResultData.add(new BaseResponse.GeneralData("How to use", "", Constant.VIEW_TYPE_TEXT_TITLE));
        listResultData.add(new BaseResponse.GeneralData("", String.format("%s \n %s \n %s \n %s \n %s \n %s \n",
                "1. Please check your Internet, make sure is connected to Mobile Network or WIFI",
                "2. Remember to Activate the Camera Permission to start Scan of ticket",
                "3. To start scan of ticket, click Scan Now button.",
                "4. Make sure the QR Code is clearly when scanning",
                "5. Scan is Success when is show message is Success Check In and Already Check In",
                "6. If any problem show up, call the administrator"),
                Constant.VIEW_TYPE_TEXT_NORMAL));
        return listResultData;
    }

    public static BaseResponse.ResultLogin getGeneralErrorResponse(String errorMessage) {
        BaseResponse.ResultLogin resultLogin = new BaseResponse.ResultLogin();
        resultLogin.setMessage(AppConfig.GeneralResponseMessage.RC_03);
        resultLogin.setStatus(errorMessage);
        return resultLogin;
    }
}
