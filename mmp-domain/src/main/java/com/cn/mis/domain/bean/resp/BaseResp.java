package com.cn.mis.domain.bean.resp;

import java.io.Serializable;

/**
 * Created by yuejia on 2017/3/1.
 */
public class BaseResp implements Serializable {

    private static final long serialVersionUID = 2121693943906363712L;
    protected boolean success;
    protected int statusCode;
    protected String erro;
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getErro() {
        return erro;
    }
    public void setErro(String erro) {
        this.erro = erro;
    }
    @Override
    public String toString() {
        return "BaseResp [success=" + success + ", statusCode=" + statusCode + ", erro=" + erro + "]";
    }

}
