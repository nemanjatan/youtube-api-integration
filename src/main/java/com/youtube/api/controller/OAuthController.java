package com.youtube.api.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.youtube.api.auth.Auth;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class OAuthController {

    @GetMapping("/authorize")
    public RedirectView authorize() {
        String viewUrl = Auth.getAuthorizationUrl();
        return new RedirectView(viewUrl);
    }

    @GetMapping("/Callback")
    public String callback(@RequestParam("code") String code, HttpSession session) throws IOException {
        TokenResponse tokenResponse = Auth.exchangeCode(code);
        Credential credential = Auth.getCredentialsFromTokenResponse(tokenResponse);

        // >>> Section: Java authentication with YouTube API
        /* return "Callback endpoint has been executed!"; */


        // >>> Section: Fetching and Displaying Videos
        /*
         * String videoList = YouTubeService.getVideoList(credential);
         * return "Video analytics: \n\n" + videoList;
         */

        // >>> Section: Integrating Video Upload Functionality
        Auth.saveCredentialsInSession(credential, session);

        return generateVideoUploadForm();
    }

    private String generateVideoUploadForm() {
        try {
            // Read the HTML content from the file
            ClassPathResource resource = new ClassPathResource("templates/video_upload.html");
            byte[] htmlBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String htmlForm = new String(htmlBytes, StandardCharsets.UTF_8);

            return htmlForm;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while reading the HTML file.";
        }
    }
}
