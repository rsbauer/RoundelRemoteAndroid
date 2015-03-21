package com.rsbauer.roundelremote.webservices.models.response;

/**
 * Created by astro on 2/8/15.
 */
public class Execution {
    private ExecutionStatus executionStatus;

    /**
     *
     * @return
     * The executionStatus
     */
    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }
    public void setExecutionStatus(ExecutionStatus status) { this.executionStatus = status; }
}
