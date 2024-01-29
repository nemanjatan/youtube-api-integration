package com.youtube.api.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

public class Auth {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final Collection<String> SCOPES = Arrays.asList(
        "https://www.googleapis.com/auth/youtube.readonly",
        "https://www.googleapis.com/auth/youtube.upload"
    );

    /**
     * Retrieves Google client secrets from a predefined resource file.
     *
     * @return GoogleClientSecrets containing the OAuth2 client configuration.
     * @throws RuntimeException if there's an error loading the client secrets.
     */
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

    /**
     * Builds and configures an instance of GoogleAuthorizationCodeFlow.
     *
     * @param googleClientSecrets The GoogleClientSecrets to be used in the flow.
     * @return An instance of GoogleAuthorizationCodeFlow for OAuth2 flow.
     */
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

    /**
     * Exchanges an authorization code for a token response.
     *
     * @param code The authorization code to exchange for a token.
     * @return TokenResponse resulting from the code exchange.
     * @throws IOException if the token exchange fails.
     */
    public static TokenResponse exchangeCode(String code) throws IOException {
        GoogleClientSecrets googleClientSecrets = getSecrets();
        GoogleAuthorizationCodeFlow codeFlow = buildGoogleFlow(googleClientSecrets);

        return codeFlow.newTokenRequest(code)
            .setRedirectUri(googleClientSecrets.getDetails().getRedirectUris().get(0))
            .execute();
    }

    /**
     * Obtains OAuth2 credentials from a given token response.
     *
     * @param tokenResponse The token response to create credentials from.
     * @return Credential generated from the token response.
     * @throws IOException if there's an error creating the credentials.
     */
    public static Credential getCredentialsFromTokenResponse(
        TokenResponse tokenResponse
    ) throws IOException {
        GoogleClientSecrets clientSecrets = getSecrets();
        GoogleAuthorizationCodeFlow flow = buildGoogleFlow(clientSecrets);

        return flow.createAndStoreCredential(tokenResponse, "user");
    }

    /**
     * Constructs the authorization URL for initiating the OAuth2 flow.
     *
     * @return String URL to direct users to for authorization.
     */
    public static String getAuthorizationUrl() {
        GoogleClientSecrets googleClientSecrets = getSecrets();
        GoogleAuthorizationCodeFlow codeFlow = buildGoogleFlow(googleClientSecrets);

        return codeFlow.newAuthorizationUrl()
            .setRedirectUri(googleClientSecrets.getDetails().getRedirectUris()
                .get(0)).build();
    }

    /**
     * Stores OAuth2 credentials in a user's HttpSession.
     *
     * @param credential The OAuth2 Credential to store.
     * @param session    The HttpSession where the credentials will be stored.
     */
    public static void saveCredentialsInSession(Credential credential, HttpSession session) {
        session.setAttribute("credentials", credential);
    }

    /**
     * Retrieves OAuth2 credentials from a user's HttpSession.
     *
     * @param session The HttpSession from which to retrieve credentials.
     * @return Credential The stored OAuth2 Credential, or null if not found.
     */
    public static Credential getCredentialsFromSession(HttpSession session) {
        return (Credential) session.getAttribute("credentials");
    }

}
