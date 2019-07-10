package com.rmarket.recipemarket;

public class ProcessItem {
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
}
