package com.youtube.api.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.youtube.api.auth.Auth;
import com.youtube.api.service.YouTubeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@RequestMapping("video")
@RestController
public class VideoController {

    @PostMapping("upload")
    public String uploadVideo(
        @RequestParam("videoTitle") String title,
        @RequestParam("videoDescription") String description,
        @RequestPart("videoFile") MultipartFile videoFile,
        HttpSession session
    ) {
        try {
            // Save the uploaded file to a temporary location
            Path tempFilePath = Files.createTempFile("upload-", ".tmp");
            videoFile.transferTo(tempFilePath);

            // Retrieve stored credentials from session
            Credential credential = Auth.getCredentialsFromSession(session);
            if (credential == null) {
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User not authenticated"
                );
            }

            // Call YouTubeService to upload the video
            String response = YouTubeService.uploadVideo(
                credential,
                tempFilePath.toFile(),
                title,
                description
            );

            // Cleanup: Delete the temporary file
            Files.delete(tempFilePath);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred during video upload: " + e.getMessage();
        }
    }

}
