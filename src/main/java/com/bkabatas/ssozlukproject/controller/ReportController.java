package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.PostDto;
import com.bkabatas.ssozlukproject.dto.ReportDto;
import com.bkabatas.ssozlukproject.model.Report;
import com.bkabatas.ssozlukproject.request.ReportCreateRequest;
import com.bkabatas.ssozlukproject.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/add")
    public ResponseEntity<Void> createReport(@RequestBody ReportCreateRequest newReport) {
        Report report = reportService.createReport(newReport);
        if(report != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getAll")
    public List<ReportDto> getAllReport(){
        return reportService.getAllReport().stream().map(u -> new ReportDto(u)).toList();
    }
    @GetMapping("/getAllPostReports{postId}")
    public List<ReportDto> getAllPostReports(@RequestParam Optional<Long> postId){
        return reportService.getAllPostReports(postId);
    }
    @GetMapping("/{reportId}")
    public ReportDto getReportById(@PathVariable("reportId")Long reportId){
        Report report = reportService.getReportById(reportId);
        return new ReportDto(report);
    }
    @DeleteMapping("/{reportId}") //USER ID SINE GORE SILME
    public String deleteReportById(@PathVariable("reportId") Long reportId){
        return reportService.deleteReportById(reportId);
    }
}
