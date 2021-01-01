package com.zwl.job.entity;

public class Result {

    private Boolean flag;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public Result(Boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
