package com.PohonTautan.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class y {

    public Blob convertMultipartFileToBlob(MultipartFile multipartFile) throws IOException, SQLException {
        return new javax.sql.rowset.serial.SerialBlob(multipartFile.getBytes());
    }
}

