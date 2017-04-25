package edu.hm.shareit.resources;

/**
 * Created by oliver on 12.04.17.
 */
public enum MediaServiceResultDiscs {CODE404(404,"Not Found");

    private final int code;
    private final String status;

    MediaServiceResultDiscs(int code, String status){
        this.code = code;
        this.status = status;
    }

    public int getCode(){
        return code;
    }

    public String getStatus(){ return status; }
}
