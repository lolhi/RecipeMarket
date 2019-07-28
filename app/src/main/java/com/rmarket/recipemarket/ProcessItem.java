package com.rmarket.recipemarket;

public class ProcessItem implements Comparable<ProcessItem> {
    private int processNum;
    private String processDc;
    private String processStepImg;
    private String processTip;

    public ProcessItem(int processNum, String processDc, String processStepImg, String processTip) {
        this.processNum = processNum;
        this.processDc = processDc;
        this.processStepImg = processStepImg;
        this.processTip = processTip;
    }

    public int getProcessNum() {
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
    public int compareTo(ProcessItem item) {
        if(this.processNum < item.getProcessNum())
            return -1;
        else if(this.processNum > item.getProcessNum())
            return 1;
        else
            return 0;
    }
}
