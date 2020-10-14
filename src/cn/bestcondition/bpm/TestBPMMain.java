package cn.bestcondition.bpm;

import javax.swing.*;

public class TestBPMMain {

    private static final String MAIN_VIEW_TITLE = "测BPM";
    private static final String BUTTON_INIT_TEXT = "按钮";
    private static final String RESET_TEXT = "重置";
    private static final String COUNT_FORMAT_TEXT = "一共点击了%d次，";
    private static final String BPM_PRE_TEXT = "BPM : ";
    private static final String BPM_FORMAT_PARAMETER = "%.2f";

    private static final int MAIN_VIEW_WIDTH = 400;
    private static final int MAIN_VIEW_HEIGHT = 200;

    public static void main(String[] args) {
        JFrame mainView = new JFrame(MAIN_VIEW_TITLE);
        JButton button = new JButton(BUTTON_INIT_TEXT);
        mainView.setSize(MAIN_VIEW_WIDTH, MAIN_VIEW_HEIGHT);
        mainView.add(button);
        mainView.setVisible(true);

        CountBPM countBPM = new CountBPM();
        button.addActionListener(e -> changeView(countBPM, button));

    }

    private static void changeView(CountBPM countBPM, JButton button) {
        countBPM.click();
        if (!countBPM.isFirstClick()) {
            if (!countBPM.isValid()) {
                button.setText(RESET_TEXT);
            } else {
                button.setText(BPMShowFormat(countBPM.getCount(), countBPM.getBPM()));
            }
        }
    }

    private static String BPMShowFormat(int count, double BPM) {

        return String.format(COUNT_FORMAT_TEXT + BPM_PRE_TEXT + BPM_FORMAT_PARAMETER, count, BPM);
    }
}
