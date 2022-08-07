package com.raytalktech.scanme.data;

import java.util.List;

public class BaseResponse {

    private ResultData resultData;
    private List<ResultData> listResultData;

    public ResultData getResultData() {
        return resultData;
    }

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }

    public static class ResultData {
        private String title;
        private String message;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ResultData() {
        }

        public ResultData(String title, String message, String status) {
            this.title = title;
            this.message = message;
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
