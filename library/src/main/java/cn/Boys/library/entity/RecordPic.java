package cn.Boys.library.entity;


public class RecordPic {

    private Record record;

    private String books_pic;


    @Override
    public String toString() {
        return "RecordPic{" +
                "record=" + record +
                ", books_pic='" + books_pic + '\'' +
                '}';
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getBooks_pic() {
        return books_pic;
    }

    public void setBooks_pic(String books_pic) {
        this.books_pic = books_pic;
    }
}
