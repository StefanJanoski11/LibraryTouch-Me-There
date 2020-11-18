package cn.Boys.library.entity;

import cn.Boys.library.enums.ResultEnum;

public class Result {
//    public final static int OK=200;/*请求成功*/
//    public final static int CREATED=201;/*已创建。成功请求并创建了新的资源*/
//    public final static int NOT_FOUND=404;/*无法根据客户端的请求找到资源*/
//    public final static int NOT_ACCEPTABLE=406;/*无法根据客户端请求的内容特性完成请求*/
//    public final static int SERVER_ERROR=500;/*服务器内部错误，无法完成请求*/

    private Object object;
    private Integer code;
    private String msg;

    public Result(Object object, ResultEnum resultEnum) {
        this.object = object;
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
