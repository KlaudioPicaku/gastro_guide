package com.uniroma3.it.gastroguide.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileNameGenerator {
    public static String generateFileName(Object model, MultipartFile file) {
        String className = model.getClass().getSimpleName().toLowerCase();
        String extension = getFileExtension(file.getOriginalFilename());

        String uniqueId = UUID.randomUUID().toString().substring(0, 8);

        return className + "-" + uniqueId;
    }

    public static String getFileExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        return extensionIndex >= 0 ? fileName.substring(extensionIndex) : "";
    }
}
