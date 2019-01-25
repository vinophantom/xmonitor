package com.vino.xmonitor.msg;

/**
 * @author phantom
 */
public class WSMessage {

    /**
     * 0为正常，-1为异常
     */
    private int status;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据内容
     */
    private Object data;
    /**
     * 请求数据名
     */
    private String requestDataName;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRequestDataName() {
        return requestDataName;
    }

    public void setRequestDataName(String requestDataName) {
        this.requestDataName = requestDataName;
    }

}
