package com.bkabatas.ssozlukproject.repository;
import com.bkabatas.ssozlukproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    User findByUserMail(String userMail);
}
