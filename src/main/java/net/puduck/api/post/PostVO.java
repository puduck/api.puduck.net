package net.puduck.api.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVO implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long postIdx;

    @Column(length = 200, nullable = false)
    private String postTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String postContent;

    @Column(length = 100, nullable = false)
    private String postAuthor;

    private int postHit;

    @Column(columnDefinition = "DATATIME", nullable = false)
    private LocalDateTime postRegDate;

    @Column(columnDefinition = "DATATIME", nullable = false)
    private LocalDateTime postModDate;

    public PostVO(String postTitle, String postContent, int postHit, LocalDateTime postRegDate, LocalDateTime postModDate) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postHit = postHit;
        this.postRegDate = postRegDate;
        this.postModDate = postModDate;
    }
}
