package com.bkabatas.ssozlukproject.service;
import com.bkabatas.ssozlukproject.dto.ReportDto;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.request.ReportCreateRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface ReportService {
    Report createReport(ReportCreateRequest newReport);

    List<Report> getAllReport();

    Report getReportById(Long reportId);

    String deleteReportById(Long reportId);

    List<ReportDto> getAllPostReports(Optional<Long> postId);
}
