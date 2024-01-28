package com.youtube.api.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

public class Auth {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final Collection<String> SCOPES = Arrays.asList(
        "https://www.googleapis.com/auth/youtube.readonly"
    );

    private static GoogleClientSecrets getSecrets() {
        InputStream in = Auth.class.getResourceAsStream("/client_secrets.json");
        try {
            return GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(in)
            );
        } catch (IOException e) {
            System.out.println("Error when trying to load google client secrets...");
            throw new RuntimeException(e);
        }
    }

    private static GoogleAuthorizationCodeFlow buildGoogleFlow(
        GoogleClientSecrets googleClientSecrets
    ) {
        return new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT,
            JSON_FACTORY,
            googleClientSecrets,
            SCOPES
        ).setAccessType("offline").build();
    }

    public static TokenResponse exchangeCode(String code) throws IOException {
        GoogleClientSecrets googleClientSecrets = getSecrets();
        GoogleAuthorizationCodeFlow codeFlow = buildGoogleFlow(googleClientSecrets);

        return codeFlow.newTokenRequest(code)
            .setRedirectUri(googleClientSecrets.getDetails().getRedirectUris().get(0))
            .execute();
    }

    public static Credential getCredentialsFromTokenResponse(
        TokenResponse tokenResponse
    ) throws IOException {
        GoogleClientSecrets clientSecrets = getSecrets();
        GoogleAuthorizationCodeFlow flow = buildGoogleFlow(clientSecrets);

        return flow.createAndStoreCredential(tokenResponse, "user");
    }

    public static String getAuthorizationUrl() {
        GoogleClientSecrets googleClientSecrets = getSecrets();
        GoogleAuthorizationCodeFlow codeFlow = buildGoogleFlow(googleClientSecrets);

        return codeFlow.newAuthorizationUrl()
            .setRedirectUri(googleClientSecrets.getDetails().getRedirectUris()
                .get(0)).build();
    }

}
