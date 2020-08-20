package com.logesh.app.api.datapoints.service;

import com.logesh.app.api.datapoints.data.DataPointsEntity;
import com.logesh.app.api.datapoints.data.DataPointsRespository;
import com.logesh.app.api.datapoints.exceptions.DataPointAlreadyExistException;
import com.logesh.app.api.datapoints.exceptions.DataPointNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class DataPointsService {


    DataPointsRespository dataPointsRespository;

    @Autowired
    public DataPointsService(DataPointsRespository dataPointsRespository) {
        this.dataPointsRespository = dataPointsRespository;

    }

    public DataPointsEntity createDataPoint(DataPointsEntity newDataPoint) {

        if (newDataPoint.getPointSeq() == null || newDataPoint.getPointSeq().length()<1) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMddhhmmss");
            String strDate = formatter.format(new Date());

            newDataPoint.setPointSeq(newDataPoint.getType() + "-" + strDate);

            if(newDataPoint.getEventDate()==null){
                newDataPoint.setEventDate(new Date());
            }
        } else {

            DataPointsEntity existingDataPoint = getDataPointByPointSeq(newDataPoint.getPointSeq());
            if (existingDataPoint != null)
                newDataPoint.setId(existingDataPoint.getId());
        }

        try {


            DataPointsEntity createdDataPoint = dataPointsRespository.save(newDataPoint);

            return createdDataPoint;
        } catch (Exception ex) {
            throw new DataPointAlreadyExistException(" Data Point with id :" + newDataPoint.getPointSeq() + " already exists");
        }

    }

    public DataPointsEntity getDataPointByPointSeq(String pointSeq) {

        DataPointsEntity resultDataPoint = dataPointsRespository.findByPointSeq(pointSeq);

        if (resultDataPoint == null) {
            throw new DataPointNotFoundException("Data Point with Id :" + pointSeq + " not found");
        }
        return resultDataPoint;
    }


    public Iterable<DataPointsEntity> findAllDataPoint() {

        return dataPointsRespository.findAll();
    }


    public void deleteDataPointByPointSeq(String pointSeq) {

        DataPointsEntity dataPointsEntity = getDataPointByPointSeq(pointSeq);
        dataPointsRespository.delete(dataPointsEntity);
    }

    public List<List<String>> fileContent(){

        List<String> pc =new ArrayList<>();
        pc.add("1. Project Contribution :");
        List<String> plc=new ArrayList<>();
        plc.add("2. Practice Level Contribution :");
        List<String> badges=new ArrayList<>();
       badges.add("3. Badges :");
        List<String> cert=new ArrayList<>();
        cert.add("4. Certifications :");
        List<String> other=new ArrayList<>();
        other.add("5. Other Achievements/Comments :");


        Iterable<DataPointsEntity> allData=findAllDataPoint();
        allData.forEach(dataPointsEntity -> {
            if(dataPointsEntity.getType().equals("PC"))
                pc.add(dataPointsEntity.getDescription());
            if(dataPointsEntity.getType().equals("PLC"))
                plc.add(dataPointsEntity.getDescription());
            if(dataPointsEntity.getType().equals("B"))
                badges.add(dataPointsEntity.getDescription());
            if(dataPointsEntity.getType().equals("C"))
                cert.add(dataPointsEntity.getDescription());
            if(dataPointsEntity.getType().equals("O"))
                other.add(dataPointsEntity.getDescription());
        });

        List<List<String>> fileData=new ArrayList<>();
        fileData.add(pc);
        fileData.add(plc);
        fileData.add(badges);
        fileData.add(cert);
        fileData.add(other);

        return fileData;
    }
}
