package net.puduck.api.comic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComicVO implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long comicIdx;

    @Column(length = 200, nullable = false)
    private String comicName;

    @Column(columnDefinition = "DATATIME", nullable = false)
    private LocalDateTime comicRegDate;

    @Column(columnDefinition = "DATATIME", nullable = false)
    private LocalDateTime comicModDate;

    @Builder
    public ComicVO(String comicName, LocalDateTime comicRegDate, LocalDateTime comicModDate) {
        this.comicName = comicName;
        this.comicRegDate = comicRegDate;
        this.comicModDate = comicModDate;
    }
}
