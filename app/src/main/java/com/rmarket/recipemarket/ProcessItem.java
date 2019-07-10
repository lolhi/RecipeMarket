package com.rmarket.recipemarket;

public class ProcessItem implements Comparable {
    private String processNum;
    private String processDc;
    private String processStepImg;
    private String processTip;

    public ProcessItem(String processNum, String processDc, String processStepImg, String processTip) {
        this.processNum = processNum;
        this.processDc = processDc;
        this.processStepImg = processStepImg;
        this.processTip = processTip;
    }

    public String getProcessNum() {
        return processNum;
    }

    public String getProcessDc() {
        return processDc;
    }

    public String getProcessStepImg() {
        return processStepImg;
    }

    public String getProcessTip() {
        return processTip;
    }

    @Override
    public int compareTo(Object o) {
        return this.processNum.compareTo(((ProcessItem)o).processNum);
    }
}
