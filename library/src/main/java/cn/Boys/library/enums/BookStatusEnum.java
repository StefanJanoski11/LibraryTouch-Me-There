package cn.Boys.library.enums;

public enum BookStatusEnum {
    HAVE(1,"已经还书籍"),
    NONE(0,"未还书籍");

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
