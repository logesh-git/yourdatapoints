package com.logesh.app.api.datapoints.exceptions;



public class DataPointAlreadyExistExceptionResponse {

    private String DataPointAlreadyExist;

    public DataPointAlreadyExistExceptionResponse(String dataPointAlreadyExist) {
        DataPointAlreadyExist = dataPointAlreadyExist;
    }

    public String getDataPointAlreadyExist() {
        return DataPointAlreadyExist;
    }

    public void setDataPointAlreadyExist(String dataPointAlreadyExist) {
        DataPointAlreadyExist = dataPointAlreadyExist;
    }
}
