package com.likelion.innerjoin.common.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

import com.azure.storage.blob.BlobClient;

@Service
public class BlobService {

    @Value("${azure.blob.connection-string}")
    private String connectionString;
    @Value("${azure.blob.container-name}")
    private String containerName;

    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString).buildClient();
        return serviceClient.getBlobContainerClient(containerName);
    }

    public String storeFile(String filename, InputStream content, long length) {
        // 파일 이름에 UUID 추가하여 고유한 이름 생성
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        BlobClient client = containerClient().getBlobClient(uniqueFilename);
        client.upload(content, length);
        return client.getBlobUrl();  // URL 반환
    }

    public boolean deleteFile(String filename) {
        BlobClient client = containerClient().getBlobClient(filename);
        if (client.exists()) {
            client.delete();
            return true;
        }
        return false;
    }
}
