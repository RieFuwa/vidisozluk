package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class ReportDto {
    private Long id ;
    private Long userId;
    private Long postId;
    private String reportText;
    private Date createDate;

    public  ReportDto(Report entity){
        this.id= entity.getId();
        this.reportText=entity.getReportText();
        this.userId=entity.getUser().getId();
        this.createDate=entity.getCreateDate();
        this.postId=entity.getPost().getId();
    }
}
