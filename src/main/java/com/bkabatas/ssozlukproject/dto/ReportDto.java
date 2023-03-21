package com.bkabatas.ssozlukproject.dto;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.model.User;
import lombok.Data;

@Data
public class ReportDto {
    private Long id ;
    private Long userId;
    private Long postId;
    private String reportText;

    public  ReportDto(Report entity){
        this.id= entity.getId();
        this.reportText=entity.getReportText();
        this.userId=entity.getUser().getId();
        this.postId=entity.getPost().getId();
    }
}
