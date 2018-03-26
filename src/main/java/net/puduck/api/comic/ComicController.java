package net.puduck.api.comic;

import lombok.AllArgsConstructor;
import net.puduck.api.post.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/api/comic")
@RestController
@AllArgsConstructor
public class ComicController {

    private ComicRepository comicRepository;

    @GetMapping(value = "/")
    public Page<ComicVO> ListPostbyPaging(Pageable pageable) {
        return comicRepository.findAll(pageable);
    }
}
