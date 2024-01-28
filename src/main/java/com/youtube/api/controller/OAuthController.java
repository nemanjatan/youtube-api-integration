package com.youtube.api.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.youtube.api.auth.Auth;
import com.youtube.api.service.YouTubeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
public class OAuthController {

    @GetMapping("/authorize")
    public RedirectView authorize() {
        String viewUrl = Auth.getAuthorizationUrl();
        return new RedirectView(viewUrl);
    }

    @GetMapping("/Callback")
    public String callback(@RequestParam("code") String code) throws IOException {
        TokenResponse tokenResponse = Auth.exchangeCode(code);
        Credential credential = Auth.getCredentialsFromTokenResponse(tokenResponse);

        String description = YouTubeService.getYouTubeChannelDescription(credential);

        return "This is my channel description: " + description;
    }

}
