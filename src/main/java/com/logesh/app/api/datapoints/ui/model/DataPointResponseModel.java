package com.logesh.app.api.datapoints.ui.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DataPointResponseModel {

    private String type;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date eventDate;
    private String pointSeq;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getPointSeq() {
        return pointSeq;
    }

    public void setPointSeq(String pointSeq) {
        this.pointSeq = pointSeq;
    }
}
