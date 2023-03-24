package com.bkabatas.ssozlukproject.service.Impl;
import com.bkabatas.ssozlukproject.dto.LikeDto;
import com.bkabatas.ssozlukproject.dto.PostDto;
import com.bkabatas.ssozlukproject.dto.ReportDto;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.repository.ReportRepository;
import com.bkabatas.ssozlukproject.request.ReportCreateRequest;
import com.bkabatas.ssozlukproject.service.LikeService;
import com.bkabatas.ssozlukproject.service.PostService;
import com.bkabatas.ssozlukproject.service.ReportService;
import com.bkabatas.ssozlukproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private LikeService likeService;

    @Override
    public Report createReport(ReportCreateRequest newReport) {
        User user = userService.getUserById(newReport.getUserId());
        Post post = postService.getPostById(newReport.getPostId());
        if(user == null && post == null)
            return null;
        Report toCreate= new Report();
        toCreate.setId(newReport.getId());
        toCreate.setCreateDate(new Date());
        toCreate.setUser(user);
        toCreate.setPost(post);
        toCreate.setReportText(newReport.getReportText());
        return reportRepository.save(toCreate);
    }

    @Override
    public List<Report> getAllReport() {
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    @Override
    public String deleteReportById(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            return "Report with id not found" +reportId+".";
        }
        reportRepository.deleteById(reportId);
        return "Report with id " +reportId+ " has been deleted success.";
    }

    @Override
    public List<ReportDto> getAllPostReports(Optional<Long> postId) {
        List<Report> reports;
        if(postId.isPresent()){
                reports=reportRepository.findByPostId(postId.get());
        }else
            reports = reportRepository.findAll();
        return reports.stream().map(p -> {
            return new ReportDto(p);}).collect(Collectors.toList());
    }
}
