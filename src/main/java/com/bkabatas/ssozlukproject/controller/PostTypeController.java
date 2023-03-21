package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.PostTypeDto;
import com.bkabatas.ssozlukproject.model.PostType;
import com.bkabatas.ssozlukproject.service.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/postType")
public class PostTypeController {

    @Autowired
    private PostTypeService postTypeService;

    @PostMapping("/add")
    public ResponseEntity<Void> createPostType(@RequestBody PostType newPostType) {
        PostType postType = postTypeService.createPostType(newPostType);
        if(postType != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/getAll")
    public List<PostTypeDto> getAllPostType(){
        return postTypeService.getAllPostType().stream().map(u -> new PostTypeDto(u)).toList();
    }
    @GetMapping("/{postTypeId}")
    public PostTypeDto getPostTypeById(@PathVariable("postTypeId")Long postTypeId){
        PostType postType = postTypeService.getPostTypeById(postTypeId);
        return new PostTypeDto(postType);
    }
}
