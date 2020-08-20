package com.logesh.app.api.datapoints.ui.controller;

import com.logesh.app.api.datapoints.data.DataPointsEntity;
import com.logesh.app.api.datapoints.service.DataPointsService;
import com.logesh.app.api.datapoints.service.RequestValidationService;

import com.logesh.app.api.datapoints.ui.model.DataPointResponseModel;
import org.apache.tomcat.util.buf.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/api/datapoints")
@CrossOrigin
public class DataPointsController {

    @Autowired
    private DataPointsService dataPointsService;

    @Autowired
    private RequestValidationService requestValidationService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("")
    public ResponseEntity<?> createNewDataPoint(@Valid @RequestBody DataPointsEntity dataPointsEntity, BindingResult result) {

        ResponseEntity<?> errors = requestValidationService.mapRequestValidation(result);
        if (errors != null) return errors;

        DataPointsEntity createdDataPoint = dataPointsService.createDataPoint(dataPointsEntity);

        DataPointResponseModel resultValue=modelMapper.map(createdDataPoint,DataPointResponseModel.class);

        return new ResponseEntity<DataPointResponseModel>(resultValue, HttpStatus.CREATED);
    }

    @GetMapping("/{pointSeq}")
    public ResponseEntity<?> getDataPointByPointSeq(@Valid @PathVariable String pointSeq){

        DataPointsEntity resultDataPoint = dataPointsService.getDataPointByPointSeq(pointSeq);

        DataPointResponseModel resultValue=modelMapper.map(resultDataPoint,DataPointResponseModel.class);

        return new ResponseEntity<DataPointResponseModel>(resultValue, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<DataPointResponseModel> getAllDataPoints(){

        return StreamSupport.stream(dataPointsService.findAllDataPoint().spliterator(), false)
                .map(dataPoint -> modelMapper.map(dataPoint, DataPointResponseModel.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{pointSeq}")
    public ResponseEntity<?> deleteDataPointByPointSeq(@PathVariable String pointSeq){
        dataPointsService.deleteDataPointByPointSeq(pointSeq);
        return new ResponseEntity<String>("Data Point "+pointSeq.toUpperCase()+" was deleted Succesfully",HttpStatus.OK);
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> download(String param) throws IOException {

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        File file= new File("DataPoint-"+ Integer.toString(localDate.getYear())+".txt");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        List<List<String>> fileData= dataPointsService.fileContent();
        FileOutputStream outputStream = new FileOutputStream(file);
        StringBuffer sb= new StringBuffer();
        for(List<String> topic : fileData){
           topic.forEach(t->  sb.append(t).append(System.getProperty("line.separator")));
           sb.append(System.getProperty("line.separator"));
        }
        byte[] strToBytes = String.valueOf(sb).getBytes();
        outputStream.write(strToBytes);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
