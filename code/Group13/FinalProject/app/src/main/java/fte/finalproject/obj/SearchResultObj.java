package fte.finalproject.obj;

import com.google.gson.annotations.SerializedName;

public class SearchResultObj {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String[] data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String[] getData() {
        return data;
    }
}
