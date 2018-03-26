package net.puduck.common.file;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@RequestMapping("/api/file")
@RestController
@AllArgsConstructor
public class FileController {

    private FileRepository fileRepository;
    private final String dataPath = System.getProperty("user.dir") + "/upload";

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadAttachment(@RequestPart MultipartFile sourceFile) throws IOException {
        String fileName = sourceFile.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        String fileContentType = sourceFile.getContentType();
        Long fileSize = sourceFile.getSize();
        File destinationFile;

        String filePath = String.format("%s/%s.%s", dataPath, RandomStringUtils.randomAlphanumeric(32), fileExtension);

        do {
            destinationFile = new File(filePath);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        sourceFile.transferTo(destinationFile);

        FileVO fileVO = fileRepository.save(FileVO.builder()
                .fileName(fileName)
                .fileExt(fileExtension)
                .fileContentType(fileContentType)
                .filePath(filePath)
                .fileSize(fileSize)
                .fileRegDate(LocalDateTime.now())
                .fileModDate(LocalDateTime.now())
                .build());

        return new ResponseEntity<>(fileVO, HttpStatus.OK);
    }

    @GetMapping(value = "/download/{FILE_IDX}")
    public void downloadFile(HttpServletResponse response, @PathVariable(value = "FILE_IDX") long fileIdx) throws IOException {
        FileVO fileVO = fileRepository.findById(fileIdx).get();

        File file = new File(fileVO.getFilePath());

        FileInputStream fis = null;
        String fileName = fileVO.getFileName();
        String fileContentType = fileVO.getFileContentType();
        OutputStream os = null;
        try {

            response.setContentType(fileContentType);
            response.setContentLength((int) file.length());
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");

            fis = new FileInputStream(file);
            byte[] buff = new byte[fis.available()];
            fis.read(buff);

            os = response.getOutputStream();
            os.write(buff);

            os.flush();
        } catch (Exception e) {

        } finally {
            if (fis != null) {
                fis.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}

