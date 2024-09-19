package org.dx.accountbalancemanager.common.exception;

public enum CommonEnum {
    INTERNAL_SERVER_ERROR(500, "内部服务器错误"),
    BAD_REQUEST(400, "请求参数错误");

    private int resultCode;
    private String resultMsg;

    CommonEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
