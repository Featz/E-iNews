
package com.fregodeceves.einews.gnews;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GoogleNews {

    @Expose
    private ResponseData responseData;
    @Expose
    private Object responseDetails;
    @Expose
    private Integer responseStatus;

    /**
     * 
     * @return
     *     The responseData
     */
    public ResponseData getResponseData() {
        return responseData;
    }

    /**
     * 
     * @param responseData
     *     The responseData
     */
    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    /**
     * 
     * @return
     *     The responseDetails
     */
    public Object getResponseDetails() {
        return responseDetails;
    }

    /**
     * 
     * @param responseDetails
     *     The responseDetails
     */
    public void setResponseDetails(Object responseDetails) {
        this.responseDetails = responseDetails;
    }

    /**
     * 
     * @return
     *     The responseStatus
     */
    public Integer getResponseStatus() {
        return responseStatus;
    }

    /**
     * 
     * @param responseStatus
     *     The responseStatus
     */
    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
