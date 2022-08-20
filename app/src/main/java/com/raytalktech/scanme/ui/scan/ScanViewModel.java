package com.raytalktech.scanme.ui.scan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.raytalktech.scanme.data.AppRepository;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.data.source.remote.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ScanViewModel extends ViewModel {

    private final AppRepository repository;
    private String codeResult, token;

    public String getCodeResult() {
        return codeResult;
    }

    public void setCodeResult(String codeResult) {
        this.codeResult = codeResult;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ScanViewModel(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<BaseResponse.ResultCheckIn>> getResultCheckIn() {
        LiveData<ApiResponse<BaseResponse.ResultCheckIn>> data;
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("code", getCodeResult())
                .build();

        data = repository.checkInResult(requestBody, getToken());

        return data;
    }
}
