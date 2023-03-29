package com.bkabatas.ssozlukproject.repository;
import com.bkabatas.ssozlukproject.model.TestRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestRedisRepository extends JpaRepository<TestRedis,Long> {


}
