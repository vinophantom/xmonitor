package com.vino.xmonitor.result;

/**
 * @author phantom
 */
public class DataResult<T> {

    private CodeMsg code;
    private String dataName;
    private T data;

    public DataResult(String dataName, T data) {
        this.dataName = dataName;
        this.data = data;
    }

    /**
     *  成功时候的调用
     * */
    public static  <T> DataResult<T> success(String dataName, T data){
        DataResult<T> res = new DataResult<T>(dataName, data);
        res.setCode(CodeMsg.SUCCESS);
        return res;
    }

    /**
     *  失败时候的调用
     * */
    public static  <T> DataResult<T> error(CodeMsg codeMsg){
        return new DataResult<T>(codeMsg);
    }

    private DataResult(T data) {
        this.data = data;
    }

    private DataResult(CodeMsg code, String dataName) {
        this.code = code;
        this.dataName = dataName;
    }

    private DataResult(CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg;
            this.dataName = codeMsg.getMsg();
        }
    }


    public CodeMsg getCode() {
        return code;
    }
    public void setCode(CodeMsg code) {
        this.code = code;
    }
    public String getDataName() {
        return dataName;
    }
    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}

