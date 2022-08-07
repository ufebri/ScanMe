package com.raytalktech.scanme.ui.scan;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raytalktech.scanme.config.AppConfig;
import com.raytalktech.scanme.data.AppRepository;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.data.source.remote.ApiResponse;
import com.raytalktech.scanme.utils.ResponseDataHelper;

public class ScanViewModel extends ViewModel {

    private final AppRepository repository;
    private LiveData<ApiResponse<BaseResponse.ResultData>> mData;
    private final String TAG = getClass().getSimpleName();
    private MutableLiveData<ApiResponse<BaseResponse.ResultData>> data = new MutableLiveData<>();
    private int QR_RESPONSE_RESULT;
    private BaseResponse.ResultData resultData = new BaseResponse.ResultData();

    public static class QR_RESULT {
        public static final int SUCCESS = 0;
        public static final int WRONG_QR = 1;
        public static final int ALREADY_CHECK_IN = 2;
    }

    public ScanViewModel(AppRepository repository) {
        this.repository = repository;
    }

    public void checkURLValidation(String url) {
        boolean isURLValid = AppConfig.BASE_URL.contains(url);
        try {
            if (isURLValid) {
                QR_RESPONSE_RESULT = QR_RESULT.SUCCESS;
                //setResultData(repository.qrResultData(url)); //TODO: ini perlu di update
                getResultData();
            } else {
                //data.setValue(ApiResponse.success(ResponseDataHelper.getResultDataScanFailed()));
                //TODO ini di update
                //setResultData(data);
                QR_RESPONSE_RESULT = QR_RESULT.WRONG_QR;
                getResultData();
            }
        } catch (Exception e) {
            Log.d(TAG, "getResultQR: " + e.getLocalizedMessage());
            QR_RESPONSE_RESULT = QR_RESULT.WRONG_QR;
            getResultData();
            // data.setValue(ApiResponse.success(ResponseDataHelper.getResultDataScanFailed()));
            //TODO ini di update
            // setResultData(data);
        }
    }

    public void setResultData(LiveData<ApiResponse<BaseResponse.ResultData>> mData) {
        this.mData = mData;
    }

    public LiveData<ApiResponse<BaseResponse.ResultData>> getResultData() {
        switch (QR_RESPONSE_RESULT) {
            case QR_RESULT.SUCCESS:
                data.setValue(ApiResponse.success(ResponseDataHelper.getShowResultLinkSuccessCheckIn()));
                mData = data;
                break;
            case QR_RESULT.ALREADY_CHECK_IN:
                data.setValue(ApiResponse.success(ResponseDataHelper.getShowResultLinkHasBeenCheckIn()));
                mData = data;
                break;
            case QR_RESULT.WRONG_QR:
                data.setValue(ApiResponse.success(ResponseDataHelper.getResultDataScanFailed()));
                mData = data;
                break;
            default:
                data.setValue(ApiResponse.success(ResponseDataHelper.getGeneralErrorResponse()));
                mData = data;
                break;
        }
        return mData;
    }

    public BaseResponse.ResultData getData() {
        if (mData.getValue() != null)
            resultData = mData.getValue().body;
        else
            resultData = ResponseDataHelper.getGeneralErrorResponse();
        return resultData;
    }
}
