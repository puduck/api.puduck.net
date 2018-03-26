package net.puduck.api.post;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class postResoistoryTest {

    @Autowired
    PostRepository repo;

    @After
    public void cleanup() {
        repo.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        repo.save(PostVO.builder()
                .postTitle("tt")
                .postContent("CC")
                .postHit(1)
                .postRegDate(LocalDateTime.now())
                .postModDate(LocalDateTime.now())
                .build());

        List<PostVO> postsList = repo.findAll();
    }
}
