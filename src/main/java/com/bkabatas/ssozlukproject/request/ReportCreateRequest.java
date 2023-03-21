package com.bkabatas.ssozlukproject.request;
import lombok.Data;
@Data
public class ReportCreateRequest {
    private Long id ;
    private Long userId;
    private Long postId;
    private String reportText;

}
