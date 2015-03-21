package com.rsbauer.roundelremote.webservices.models.response;

/**
 * Created by astro on 2/8/15.
 */
public class ExecutionStatus {

    private String serviceType;
    private String status;
    private String eventId;

    /**
     * @return The serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType The serviceType
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId The eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}