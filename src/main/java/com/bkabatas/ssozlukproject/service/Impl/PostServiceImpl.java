package com.bkabatas.ssozlukproject.service.Impl;
import com.bkabatas.ssozlukproject.dto.*;
import com.bkabatas.ssozlukproject.model.Post;
import com.bkabatas.ssozlukproject.model.PostType;
import com.bkabatas.ssozlukproject.model.User;
import com.bkabatas.ssozlukproject.repository.PostRepository;
import com.bkabatas.ssozlukproject.request.PostCreateRequest;
import com.bkabatas.ssozlukproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostTypeService postTypeService;
   @Autowired
   private UserService userService;
   @Autowired
   private LikeService likeService;
   @Autowired
   private ReportService reportService;





    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse( null);
    }

    @Override
    public String deletePostById(Long postId) {
        if (!postRepository.existsById(postId)) {
            return "Post with id not found" +postId+".";
        }
        postRepository.deleteById(postId);
        return "Post with id " +postId+ " has been deleted success.";
    }

    @Override
    public List<PostDto> getAllUserPost(Optional<Long> userId) {
        List<Post> post;
        if(userId.isPresent()){
            post=postRepository.findByUserId(userId.get());
        }else
            post = postRepository.findAll();
        return post.stream().map(p -> {
            List<LikeDto> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            List<ReportDto> reports = reportService.getAllPostReports(Optional.of(p.getId()));

            return new PostDto(p, likes,reports);}).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostAnswersByPostId(Optional<Long> connectedPostId) {
        List<Post> posts;
        if(connectedPostId.isPresent()){
            posts=postRepository.findByConnectedPostId(connectedPostId.get());
        }else
            posts= postRepository.findAll();
        return posts.stream().map(p -> {
            List<LikeDto> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            List<ReportDto> reports = reportService.getAllPostReports(Optional.of(p.getId()));

            return new PostDto(p, likes,reports);}).collect(Collectors.toList());
    }

    @Override
    public PostDto getOnePostByIdWithLikes(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<LikeDto> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null),Optional.of(postId));
        List<ReportDto> reports = reportService.getAllPostReports(Optional.of(postId));

        return new PostDto(post,likes,reports);
    }

    @Override
    public List<PostDto> getAllPostTypePost(Optional<Long> postTypeId) {
        List<Post> post;
        if(postTypeId.isPresent()){
            post=postRepository.findByPostTypeId(postTypeId.get());
        }else
            post = postRepository.findAll();
        return post.stream().map(p -> {
            List<LikeDto> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            List<ReportDto> reports = reportService.getAllPostReports(Optional.of(p.getId()));

            return new PostDto(p, likes,reports);}).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> post;

            post = postRepository.findAll();
        return post.stream().map(p -> {
            List<LikeDto> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            List<ReportDto> reports = reportService.getAllPostReports(Optional.of(p.getId()));

            return new PostDto(p, likes,reports);}).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getTodayPost() {
        List<Post> post;

        post = postRepository.queryPostByCreateDate();
        return post.stream().map(p -> {
            List<LikeDto> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            List<ReportDto> reports = reportService.getAllPostReports(Optional.of(p.getId()));

            return new PostDto(p, likes,reports);}).collect(Collectors.toList());
    }

    @Override
    public List<PostTypeByCountDto> getPostTypeByCount() {

        List<PostTypeByCountDto> postDto = postRepository.queryPostTypeTitleCount();

        return postDto;

    }

    @Override
    public List<UserTitleCountDto> getUserTitleCount() {
        List<UserTitleCountDto> postDto = postRepository.queryUserTitleCountDto();

        return postDto;
    }


    @Override
    public Post createPost(PostCreateRequest newPost) {
        PostType postType = postTypeService.getPostTypeById(newPost.getPostTypeId());
        User user = userService.getUserById(newPost.getUserId());
        if(postType== null && user == null){
            return null;
        }

        Post toCreate = new Post();
        toCreate.setId(newPost.getId());
        toCreate.setPostType(postType);
        toCreate.setUser(user);
        if(newPost.getConnectedPostId()!=null){
            toCreate.setConnectedPost(getPostById(newPost.getConnectedPostId()));
        }
        toCreate.setPostText(newPost.getPostText());
        toCreate.setPostTitle(newPost.getPostTitle());
        toCreate.setCreateDate(new Date());
        return postRepository.save(toCreate);
    }

}
