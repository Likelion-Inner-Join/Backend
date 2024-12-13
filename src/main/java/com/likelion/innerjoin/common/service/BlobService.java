package com.likelion.innerjoin.common.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

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
        BlobClient client = containerClient().getBlobClient(filename);
        client.upload(content, length);
        return "파일이 성공적으로 업로드되었습니다!";
    }
}
