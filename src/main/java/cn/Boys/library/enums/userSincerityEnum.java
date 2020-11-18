package cn.Boys.library.enums;

public enum userSincerityEnum {
    LOWEST(-2,"借书不还的无耻之徒！"),
    LOW(-1,"存在借书不还记录"),
    ORDINARY(0,"无不良记录");


    private Integer state;
    private String describe;

    userSincerityEnum(Integer state, String describe) {
        this.state = state;
        this.describe = describe;
    }

    public Integer getState() {
        return state;
    }

    public String getDescribe() {
        return describe;
    }

    public static userSincerityEnum stateOf(Integer index){
        for (userSincerityEnum sincerity: values()) {
            if(sincerity.state == index){
                return sincerity;
            }
        }
        return null;
    }
}
