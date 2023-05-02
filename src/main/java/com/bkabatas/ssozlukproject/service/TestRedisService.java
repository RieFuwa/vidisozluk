package com.bkabatas.ssozlukproject.service;

import com.bkabatas.ssozlukproject.model.TestRedis;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestRedisService {
    TestRedis createRedis(TestRedis testRedis);

    List<TestRedis> getAllRedis();

    TestRedis getRedisById(Long redisId);

    String deleteRedisById(Long redisId);
}
