package com.likelion.innerjoin.common.controller;

import com.likelion.innerjoin.common.service.BlobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/images")
public class BlobController {

    @Autowired
    private BlobService myBlobService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return "파일이 비어 있습니다. 파일을 선택해주세요.";
        }

        // Azure Blob Storage에 파일 저장
        myBlobService.storeFile(file.getOriginalFilename(), file.getInputStream(), file.getSize());

        return file.getOriginalFilename() + " 파일이 성공적으로 업로드되었습니다!";
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam("filename") String filename) {
        boolean isDeleted = myBlobService.deleteFile(filename);

        if (isDeleted) {
            return filename + " 파일이 성공적으로 삭제되었습니다.";
        } else {
            return filename + " 파일을 삭제할 수 없습니다. 파일이 존재하지 않을 수 있습니다.";
        }
    }
}
