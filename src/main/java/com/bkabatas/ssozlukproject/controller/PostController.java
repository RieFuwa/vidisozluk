package com.bkabatas.ssozlukproject.controller;
import com.bkabatas.ssozlukproject.dto.PostDto;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.request.PostCreateRequest;
import com.bkabatas.ssozlukproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest newPost) {
        Post post = postService.createPost(newPost);
        if(post != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAllUserPost{userId}")
    public List<PostDto> getAllUserPost(@RequestParam Optional<Long> userId){
        return postService.getAllUserPost(userId);
    }

    @GetMapping("/postAnswers{connectedPostId}")
        public List<PostDto> getPostAnswersByPostId(@RequestParam Optional<Long> connectedPostId){
        return postService.getPostAnswersByPostId(connectedPostId);
    }
    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable("postId")Long postId){
        return postService.getOnePostByIdWithLikes(postId);
    }

    @DeleteMapping("/{postId}") //USER ID SINE GORE SILME
    public String deleteUserById(@PathVariable("postId") Long postId){
        return postService.deletePostById(postId);
    }
}
