package com.bkabatas.ssozlukproject.repository;
import com.bkabatas.ssozlukproject.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PostTypeRepository extends JpaRepository<PostType,Long> {
}
