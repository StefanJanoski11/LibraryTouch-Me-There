package cn.Boys.library.enums;

public enum UserSincerityEnum {
    LOWEST(-2,"借书不还的无耻之徒！"),
    LOW(-1,"存在借书不还记录"),
    ORDINARY(0,"无不良记录");


    private Integer state;
    private String describe;

    UserSincerityEnum(Integer state, String describe) {
        this.state = state;
        this.describe = describe;
    }

    public Integer getState() {
        return state;
    }

    public String getDescribe() {
        return describe;
    }

    public static UserSincerityEnum stateOf(Integer index){
        for (UserSincerityEnum sincerity: values()) {
            if(sincerity.state == index){
                return sincerity;
            }
        }
        return null;
    }
}
