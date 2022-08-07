package com.raytalktech.scanme.ui.login;

import androidx.lifecycle.ViewModel;

import com.raytalktech.scanme.data.AppRepository;

public class LoginViewModel extends ViewModel {

    private final AppRepository repository;

    public LoginViewModel(AppRepository repository) {
        this.repository = repository;
    }

}
