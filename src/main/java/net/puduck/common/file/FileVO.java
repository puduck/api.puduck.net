package net.puduck.common.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileVO implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long fileIdx;

    @Column(length = 200, nullable = false)
    private String fileName;

    private Long fileSize;

    @Column(length = 10, nullable = false)
    private String fileExt;

    @Column(length = 20, nullable = false)
    private String fileContentType;

    @Column(length = 200, nullable = false)
    private String filePath;

    @Column(columnDefinition = "DATATIME", nullable = false)
    private LocalDateTime fileRegDate;

    @Column(columnDefinition = "DATATIME", nullable = false)
    private LocalDateTime fileModDate;

    @Builder
    public FileVO(String fileName, Long fileSize, String fileExt, String fileContentType, String filePath, LocalDateTime fileRegDate, LocalDateTime fileModDate) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileExt = fileExt;
        this.fileContentType = fileContentType;
        this.filePath = filePath;
        this.fileRegDate = fileRegDate;
        this.fileModDate = fileModDate;
    }
}
