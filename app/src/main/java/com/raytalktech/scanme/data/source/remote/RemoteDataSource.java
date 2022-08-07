package com.raytalktech.scanme.data.source.remote;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.network.ApiConfig;
import com.raytalktech.scanme.utils.ResponseDataHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;
    private final String TAG = getClass().getSimpleName();

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<BaseResponse.ResultData>> getResultData(String url) {
        MutableLiveData<ApiResponse<BaseResponse.ResultData>> resultData = new MutableLiveData<>();
        Call<BaseResponse> client = ApiConfig.getApiService().checkResultScan(url);
        client.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null)
                            resultData.setValue(ApiResponse.success(response.body().getResultData()));
                        else
                            resultData.setValue(ApiResponse.empty("03", ResponseDataHelper.getGeneralErrorResponse()));
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
                        resultData.setValue(ApiResponse.empty("03", ResponseDataHelper.getGeneralErrorResponse()));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                resultData.setValue(ApiResponse.error("03", ResponseDataHelper.getGeneralErrorResponse()));
            }
        });
        return resultData;
    }
}
