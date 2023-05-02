package com.bkabatas.ssozlukproject.controller;

import com.bkabatas.ssozlukproject.dto.ReportDto;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.model.TestRedis;
import com.bkabatas.ssozlukproject.request.ReportCreateRequest;
import com.bkabatas.ssozlukproject.service.ReportService;
import com.bkabatas.ssozlukproject.service.TestRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redis")
public class TestRedisController {

    @Autowired
    private TestRedisService testRedisService;

    @PostMapping("/add")
    public ResponseEntity<Void> createRedis(@RequestBody TestRedis testRedis) {
        TestRedis testRedis1= testRedisService.createRedis(testRedis);
        if(testRedis1 != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getAll")
    public List<TestRedis> getAllRedis(){
        return testRedisService.getAllRedis();
    }
    @GetMapping("/{redisId}")
    public TestRedis getRedisById(@PathVariable("redisId")Long redisId){
        return testRedisService.getRedisById(redisId);
    }
    @DeleteMapping("/{redisId}") //USER ID SINE GORE SILME
    public String deleteRedisById(@PathVariable("redisId") Long redisId){
        return testRedisService.deleteRedisById(redisId);
    }
}
