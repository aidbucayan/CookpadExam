
package com.bucayan.adrian.cookpadexam.Model;

/**
 * @author Adrian Bucayan on 12/16/16.
 */
public class Videos {

    private LowResolution lowResolution;
    private StandardResolution standardResolution;
    private LowBandwidth lowBandwidth;

    public LowResolution getLowResolution() {
        return lowResolution;
    }

    public void setLowResolution(LowResolution lowResolution) {
        this.lowResolution = lowResolution;
    }

    public StandardResolution getStandardResolution() {
        return standardResolution;
    }

    public void setStandardResolution(StandardResolution standardResolution) {
        this.standardResolution = standardResolution;
    }

    public LowBandwidth getLowBandwidth() {
        return lowBandwidth;
    }

    public void setLowBandwidth(LowBandwidth lowBandwidth) {
        this.lowBandwidth = lowBandwidth;
    }

}
