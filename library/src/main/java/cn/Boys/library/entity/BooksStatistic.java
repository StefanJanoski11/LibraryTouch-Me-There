package cn.Boys.library.entity;

public class BooksStatistic {
    private Long currentDayLend;
    private Long currentWeekLend;
    private Long currentMonthLend;
    private Long currentDayReturn;
    private Long currentWeekReturn;
    private Long currentMonthReturn;

    public BooksStatistic() {
    }

    public BooksStatistic(Long currentDayLend, Long currentWeekLend, Long currentMonthLend, Long currentDayReturn, Long currentWeekReturn, Long currentMonthReturn) {
        this.currentDayLend = currentDayLend;
        this.currentWeekLend = currentWeekLend;
        this.currentMonthLend = currentMonthLend;
        this.currentDayReturn = currentDayReturn;
        this.currentWeekReturn = currentWeekReturn;
        this.currentMonthReturn = currentMonthReturn;
    }

    public Long getCurrentDayLend() {
        return currentDayLend;
    }

    public void setCurrentDayLend(Long currentDayLend) {
        this.currentDayLend = currentDayLend;
    }

    public Long getCurrentWeekLend() {
        return currentWeekLend;
    }

    public void setCurrentWeekLend(Long currentWeekLend) {
        this.currentWeekLend = currentWeekLend;
    }

    public Long getCurrentMonthLend() {
        return currentMonthLend;
    }

    public void setCurrentMonthLend(Long currentMonthLend) {
        this.currentMonthLend = currentMonthLend;
    }

    public Long getCurrentDayReturn() {
        return currentDayReturn;
    }

    public void setCurrentDayReturn(Long currentDayReturn) {
        this.currentDayReturn = currentDayReturn;
    }

    public Long getCurrentWeekReturn() {
        return currentWeekReturn;
    }

    public void setCurrentWeekReturn(Long currentWeekReturn) {
        this.currentWeekReturn = currentWeekReturn;
    }

    public Long getCurrentMonthReturn() {
        return currentMonthReturn;
    }

    public void setCurrentMonthReturn(Long currentMonthReturn) {
        this.currentMonthReturn = currentMonthReturn;
    }

    @Override
    public String toString() {
        return "BooksStatistic{" +
                "currentDayLend=" + currentDayLend +
                ", currentWeekLend=" + currentWeekLend +
                ", currentMonthLend=" + currentMonthLend +
                ", currentDayReturn=" + currentDayReturn +
                ", currentWeekReturn=" + currentWeekReturn +
                ", currentMonthReturn=" + currentMonthReturn +
                '}';
    }
}
