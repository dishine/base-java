package com.shinedi.javabase.common.error;

public enum EmBusinessError implements CommonError {

    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"err.known.error.param"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),

    //用户还未登陆，不能下单
    USER_NOT_LOGIN(20002,"用户还未登陆，不能下单!"),

    //20000开头为用户信息相关错误定义
    WORKER_NOT_EXIST(20003,"该worker不存在"),

    //状态不存在
    WORKER_STATUS_NOT_EXIST(20004,"该worker状态不存在"),

    //团队配置
    WORKER_TEAM_BUSINESS_NOT_EXPRESS(20005,"团队配置车型不存在"),

    //自定义未知错误
    KNOWN_ERROR(10002,"err.known.error"),    //自定义未知错误

    KNOWN_ERROR_NULL(10005,"err.known.error.null"),

    //调用cortex失败
    ERROR_CORTEX(1003,"调用cortex失败")
    ;


    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
