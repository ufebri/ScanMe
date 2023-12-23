package com.raytalktech.scanerme.data.source.remote;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raytalktech.scanerme.config.AppConfig;
import com.raytalktech.scanerme.data.BaseResponse;
import com.raytalktech.scanerme.network.ApiConfig;
import com.raytalktech.scanerme.utils.ResponseDataHelper;

import okhttp3.RequestBody;
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

    public LiveData<ApiResponse<BaseResponse.ResultLogin>> getLoginData(RequestBody requestBody) {
        MutableLiveData<ApiResponse<BaseResponse.ResultLogin>> resultLogin = new MutableLiveData<>();
        Call<BaseResponse.ResultLogin> client = ApiConfig.getApiService().loginResult(requestBody);
        client.enqueue(new Callback<BaseResponse.ResultLogin>() {
            @Override
            public void onResponse(Call<BaseResponse.ResultLogin> call, Response<BaseResponse.ResultLogin> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null)
                            if (response.body().getStatus() == null)
                                resultLogin.setValue(ApiResponse.success(response.body()));
                            else {
                                resultLogin.setValue(ApiResponse.error(response.body().getMessage(), response.body()));
                            }
                        else
                            resultLogin.setValue(ApiResponse.empty(AppConfig.GeneralResponseMessage.RC_03, response.body()));
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
                        resultLogin.setValue(ApiResponse.error(e.getMessage(), response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.ResultLogin> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                resultLogin.setValue(ApiResponse.error(t.getMessage(), ResponseDataHelper.getGeneralErrorResponse(t.getMessage())));
            }
        });
        return resultLogin;
    }

    public LiveData<ApiResponse<BaseResponse.ResultCheckIn>> getCheckInData(RequestBody requestBody, String token) {
        MutableLiveData<ApiResponse<BaseResponse.ResultCheckIn>> resultCheckIn = new MutableLiveData<>();
        Call<BaseResponse.ResultCheckIn> client = ApiConfig.getApiService().checkInResult(requestBody, "Bearer " + token);
        client.enqueue(new Callback<BaseResponse.ResultCheckIn>() {
            @Override
            public void onResponse(Call<BaseResponse.ResultCheckIn> call, Response<BaseResponse.ResultCheckIn> response) {
                try {
                    if (response.isSuccessful()) {
                        resultCheckIn.setValue(ApiResponse.success(response.body()));
                        Log.d(TAG, "onResponse: " + response.body());
                    } else {
                        //TODO: Tambahin kondisi kosong disini
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
                    //TODO: Tambahin kondisi gagal
                }
            }

            @Override
            public void onFailure(Call<BaseResponse.ResultCheckIn> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                //TODO tambahin kondisi gagal
            }
        });
        return resultCheckIn;
    }
}
