package com.raytalktech.scanme.data;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {

    public static class GeneralData {
        private String title;
        private String message;
        private int viewType;

        public GeneralData() {
        }

        public GeneralData(String title, String message, int viewType) {
            this.title = title;
            this.message = message;
            this.viewType = viewType;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public int getViewType() {
            return viewType;
        }
    }

    public static class ResultLogin {

        @SerializedName("message")
        String message;

        @SerializedName("access_token")
        String access_token;

        @SerializedName("token_type")
        String token_type;

        @Nullable
        @SerializedName("status")
        String status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        @Nullable
        public String getStatus() {
            return status;
        }

        public void setStatus(@Nullable String status) {
            this.status = status;
        }
    }

    public static class ResultCheckIn {
        @SerializedName("message")
        private String message;

        @SerializedName("status")
        private String status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
