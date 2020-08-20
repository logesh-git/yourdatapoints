package com.logesh.app.api.datapoints.exceptions;

public class DataPointNotFoundExceptionResponse {

    private String DataPointNotFound;

    public DataPointNotFoundExceptionResponse(String dataPointNotFound) {
        super();
        DataPointNotFound = dataPointNotFound;
    }

    public String getDataPointNotFound() {
        return DataPointNotFound;
    }

    public void setDataPointNotFound(String dataPointNotFound) {
        DataPointNotFound = dataPointNotFound;
    }
}

