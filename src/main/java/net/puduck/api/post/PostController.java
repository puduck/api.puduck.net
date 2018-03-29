package net.puduck.api.post;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RequestMapping("/api/post")
@RestController
@AllArgsConstructor
public class PostController {

    private PostRepository postRepository;

    @GetMapping(value = "")
    public Page<PostVO> ListPostbyPaging(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping(value = "/view/{postIdx}")
    public PostVO viewPost(@PathVariable String postIdx) {
        PostVO post = postRepository.findById(Long.valueOf(postIdx)).get();
        post.setPostHit(post.getPostHit() + 1);
        postRepository.save(post);

        return post;
    }

    @GetMapping(value = "/reg")
    public PostVO setPost(String title, String content, String author) {
        PostVO save = postRepository.save(PostVO.builder()
                .postTitle(title)
                .postContent(content)
                .postAuthor(author)
                .postHit(1)
                .postRegDate(LocalDateTime.now())
                .postModDate(LocalDateTime.now())
                .build());
        return save;
    }

    @GetMapping(value = "/delete/{postIdx}")
    public PostVO deletePost(@PathVariable String postIdx) {
        PostVO post = postRepository.findById(Long.valueOf(postIdx)).get();
        postRepository.delete(post);

        return post;
    }
}
