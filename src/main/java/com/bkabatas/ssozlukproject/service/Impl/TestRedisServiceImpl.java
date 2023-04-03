package com.bkabatas.ssozlukproject.service.Impl;

import com.bkabatas.ssozlukproject.model.TestRedis;
import com.bkabatas.ssozlukproject.repository.TestRedisRepository;
import com.bkabatas.ssozlukproject.service.TestRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestRedisServiceImpl implements TestRedisService {

    @Autowired
    private TestRedisRepository testRedisRepository;
    @Override
    public TestRedis createRedis(TestRedis testRedis) {
        return  testRedisRepository.save(testRedis);
    }

    @Override
    public List<TestRedis> getAllRedis() {
        return testRedisRepository.findAll();
    }


    @Override
    public TestRedis getRedisById(Long redisId) {
        return testRedisRepository.findById(redisId).orElse( null);
    }

    @Override
    public String deleteRedisById(Long redisId) {
        if (!testRedisRepository.existsById(redisId)) {
            return "Report with id not found" +redisId+".";
        }
        testRedisRepository.deleteById(redisId);
        return "Report with id " +redisId+ " has been deleted success.";
    }


}
