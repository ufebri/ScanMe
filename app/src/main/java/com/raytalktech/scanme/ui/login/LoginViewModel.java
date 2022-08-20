package com.raytalktech.scanme.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raytalktech.scanme.data.AppRepository;
import com.raytalktech.scanme.data.BaseResponse;
import com.raytalktech.scanme.data.source.remote.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginViewModel extends ViewModel {

    private final AppRepository repository;
    private String email, password;

    public void setRequestLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginViewModel(AppRepository repository) {
        this.repository = repository;
    }

    private LiveData<ApiResponse<BaseResponse.ResultLogin>> data;

    public LiveData<ApiResponse<BaseResponse.ResultLogin>> getResultLogin() {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();

        data = repository.loginResult(requestBody);
        return data;
    }
}
