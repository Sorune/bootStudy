package com.sorune.bootstudy.controller;

import com.sorune.bootstudy.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${com.sorune.upload.path}")
    private String uploadPath;

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");
            log.info(srcFileName);
            File file = new File(uploadPath+File.separator + srcFileName);
            log.info(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers,HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {
        List<UploadResultDTO> uploadResultDTOList = new ArrayList<>();

        for (MultipartFile file : uploadFiles) {
            if(!file.getContentType().startsWith("image")) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalFilename = file.getOriginalFilename();
            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\")+1);

            log.info("fileName : "+fileName);

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath+ File.separator+folderPath+File.separator+uuid+"_"+fileName;

            Path savePath = Paths.get(saveName);

            try {
                file.transferTo(savePath);

                String thumbnailSaveName = uploadPath + File.separator+folderPath+File.separator+"s_"+uuid+"_"+fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,100,100);

                uploadResultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(uploadResultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath,folderPath);
        if(!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath+File.separator+srcFileName);
            boolean result = file.delete();

            File thumbnailFile = new File(file.getParentFile(),"s_"+file.getName());
            result = thumbnailFile.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
