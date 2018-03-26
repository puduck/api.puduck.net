package net.puduck.api.post;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class PostController {

    private PostRepository postRepository;

    @GetMapping(value = "/post/reg")
    public PostVO setPost(String title, String content) {
        PostVO save = postRepository.save(PostVO.builder()
                .postTitle(title)
                .postContent(content)
                .postHit(1)
                .postRegDate(LocalDateTime.now())
                .postModDate(LocalDateTime.now())
                .build());

        return save;
    }

    @GetMapping(value = "/post")
    public Page<PostVO> getPostListbyPaging(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    
    @GetMapping(value = "/post/view/{postIdx}")
    public PostVO getPostView(@PathVariable String postIdx) {
    	PostVO post = postRepository.findById(Long.valueOf(postIdx)).get();
    	post.setPostHit(post.getPostHit() + 1);
    	postRepository.save(post);
    	
        return post;
    }
}
