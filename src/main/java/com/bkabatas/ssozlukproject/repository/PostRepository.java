package com.bkabatas.ssozlukproject.repository;
import com.bkabatas.ssozlukproject.dto.PostTypeByCountDto;
import com.bkabatas.ssozlukproject.dto.UserTitleCountDto;
import com.bkabatas.ssozlukproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findByConnectedPostId(Long postId);

    List<Post> findByPostTypeId(Long postId);

    @Query(" SELECT u FROM Post u where u.createDate > current_date -1 ")
    List<Post> queryPostByCreateDate();

    @Query(" SELECT distinct new com.bkabatas.ssozlukproject.dto.PostTypeByCountDto(PT.postTypeName,P.postType.id,count(P.postType.id)) " +
            "FROM Post P " +
            "LEFT JOIN PostType PT ON PT.id = P.postType.id " +
            "GROUP BY PT.postTypeName, P.postType.id "+
            "ORDER BY COUNT(P.postType.id) DESC ") List<PostTypeByCountDto> queryPostTypeTitleCount();


    @Query( " SELECT DISTINCT new com.bkabatas.ssozlukproject.dto.UserTitleCountDto( U.userName, U.userMail, COUNT(P.user.id)) "+
            "FROM Post P " +
            "LEFT JOIN User U ON U.id = P.user.id " +
            "GROUP BY U.userName, " +
            "U.userMail, " +
            "P.user.id " +
            "ORDER BY COUNT(P.user.id) DESC") List<UserTitleCountDto> queryUserTitleCountDto();

}

