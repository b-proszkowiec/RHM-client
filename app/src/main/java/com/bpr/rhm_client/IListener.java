package com.bpr.rhm_client;

public interface IListener {

    /**
     * Event to handle response from the server side for the measurement update request.
     *
     * @param result contains measurement result or error message.
     */
    void onResponseReceived(String[] result);
}
