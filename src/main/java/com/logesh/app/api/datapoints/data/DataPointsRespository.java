package com.logesh.app.api.datapoints.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataPointsRespository extends CrudRepository <DataPointsEntity,Long> {

    DataPointsEntity findByPointSeq(String pointSeq);

    @Override
    Iterable<DataPointsEntity> findAll();


}
