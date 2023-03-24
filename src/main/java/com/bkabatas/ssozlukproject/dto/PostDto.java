package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.PostType;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.model.User;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private User user;
    private PostType postType;
    private Long connectedPostId;
    private String postTitle;
    private String postText;
    private Date createDate;
    private List<LikeDto> likeList;
    private List<ReportDto> reportlist;

    public PostDto(Post entity, List<LikeDto> likeList, List<ReportDto> reportlist){

        this.id=entity.getId();
        this.user=entity.getUser();
        this.postType=entity.getPostType();
        if(entity.getConnectedPost()!=null){
            this.connectedPostId=entity.getConnectedPost().getId();
        }
        this.postTitle=entity.getPostTitle();
        this.postText=entity.getPostText();
        this.createDate=entity.getCreateDate();
        this.likeList=likeList;
        this.reportlist=reportlist;

    }

}
