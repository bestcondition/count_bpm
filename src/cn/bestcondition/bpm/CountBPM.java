package cn.bestcondition.bpm;

import java.util.Date;


//BPM ： beat per minute(一分钟几拍)
public class CountBPM {

    //最大偏差率，如果超过这个偏差，认定为两次测试
    private static final double MAX_MISTAKE_PROBABILITY = 0.3;
    private static final int MILLISECOND_PER_MINUTE = 60000;

    private Date lastClickDate;
    private Date thisClickDate;
    private double averageMilliSecond;
    private int countOfTimeSpacing;

    private boolean valid;
    private boolean firstClick;


    public boolean isValid() {
        return valid;
    }

    public boolean isFirstClick() {
        return firstClick;
    }

    public double getBPM() {
        return timeSpacingInMillisecondToBPM(averageMilliSecond);
    }

    public int getCount() {
        return countOfTimeSpacing + 1;
    }

    public void click() {
        thisClickDate = new Date();
        if (lastClickDate == null) {
            firstClickDo();
        } else if (countOfTimeSpacing == 0 || thisClickIsValid()) {
            validClickDo();
        } else {
            reset();
        }

    }

    private double nowAverageMilliSecond() {
        return (countOfTimeSpacing * averageMilliSecond + nowTimeSpacing()) / (countOfTimeSpacing + 1);
    }

    private long nowTimeSpacing() {
        return (thisClickDate.getTime() - lastClickDate.getTime());
    }

    private void firstClickDo() {
        lastClickDate = thisClickDate;
        firstClick = true;
    }

    private void validClickDo() {
        firstClick = false;
        valid = true;
        averageMilliSecond = nowAverageMilliSecond();
        lastClickDate = thisClickDate;
        countOfTimeSpacing ++;
    }

    private void reset() {
        firstClick = false;
        valid = false;
        lastClickDate = null;
        countOfTimeSpacing = 0;
    }


    //计算偏差率
    private double countThisClickMistakeProbability() {
        //abs(nowTimeSpacing() - averageMilliSecond) / averageMilliSecond
        return Math.abs(nowTimeSpacing() / averageMilliSecond - 1 ) ;
    }

    private boolean thisClickIsValid() {
        return countThisClickMistakeProbability() <= MAX_MISTAKE_PROBABILITY;
    }


    //以毫秒为单位的时间间隔转换成BPM
    private double timeSpacingInMillisecondToBPM(double timeSpacingInMillisecond) {
        return MILLISECOND_PER_MINUTE / timeSpacingInMillisecond;
    }

}
