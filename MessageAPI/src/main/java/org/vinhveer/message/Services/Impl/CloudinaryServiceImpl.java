package org.vinhveer.message.Services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vinhveer.message.Services.CloudinaryService;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dcglxonte",
                "api_key", "525857237342112",
                "api_secret", "U6EZCjI7EV7-VPJeTomxFsdRuZ8"));
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new RuntimeException("Could not upload file", e);
        }
    }
}
