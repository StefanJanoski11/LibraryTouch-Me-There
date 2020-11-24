package cn.Boys.library.enums;

public enum ResultEnum {
    OK(200,"请求成功"),
    CREATED(201,"已创建,成功请求并创建了新的资源"),
    NOT_FOUND(404,"无法根据客户端的请求找到资源"),
    NOT_ACCEPTABLE(406,"无法根据客户端请求的内容特性完成请求"),
    SERVER_ERROR(500,"内部错误，无法完成请求");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultEnum ofState(Integer code){
        ResultEnum[] resultEnums = ResultEnum.values();
        for (ResultEnum currEnum:resultEnums) {
            if (currEnum.code.equals(code)){
                return currEnum;
            }
        }
        return null;
    }

    ResultEnum() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
