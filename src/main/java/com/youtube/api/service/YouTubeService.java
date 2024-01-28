package com.youtube.api.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;

import java.io.IOException;

public class YouTubeService {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static String getYouTubeChannelDescription(
        Credential credential
    ) throws IOException {
        YouTube youTube = new YouTube.Builder(
            HTTP_TRANSPORT,
            JSON_FACTORY,
            credential
        ).setApplicationName("YouTube API Application").build();

        YouTube.Channels.List channelsList = youTube.channels().list("snippet");
        channelsList.setMine(Boolean.TRUE);

        ChannelListResponse channelListResponse = channelsList.execute();

        if (channelListResponse.getItems().size() > 0) {
            return channelListResponse.getItems().get(0)
                .getSnippet().getDescription();
        } else {
            return "Unknown channel";
        }
    }
}
