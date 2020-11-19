package cn.Boys.library.enums;

public enum BookStatusEnum {
    NONE(-1,"无货"),
    HAVE(1,"可以借阅书籍"),
    NORETURN(0,"已经被借书籍");

    //状态码
    private Integer status;
    //状态信息
    private String Info;


    BookStatusEnum(Integer status, String info) {
        this.status = status;
        Info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public String getInfo() {
        return Info;
    }

    public static BookStatusEnum statusOf(Integer index){
        for (BookStatusEnum obj:values()) {
            if(obj.status == index){
                return obj;
            }
        }
        return null;
    }

}
