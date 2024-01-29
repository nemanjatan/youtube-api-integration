# YouTube API Integration for Java

This repository contains Java code for integrating with the YouTube API. It facilitates OAuth authentication, video retrieval, and video upload functionalities, using Google's API client libraries for Java.

## Overview

The code is organized into several packages and controllers:

- `com.youtube.api.auth`: Handles OAuth authentication with the YouTube API.
- `com.youtube.api.service`: Provides services for video retrieval and upload.
- `com.youtube.api.ui`: Contains a simple UI class for OAuth testing.
- `com.youtube.api.controller`: Includes two controllers for handling video upload and OAuth authorization.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or later.
- Google API client libraries for Java.
- Client secrets JSON file from the Google API Console.

### Setup

1. Clone the repository.
2. Ensure that the Google API client libraries are included in your project's dependencies.
3. Place your client secrets JSON file in the resource folder.

### Usage

#### OAuth Authentication

The `com.youtube.api.auth` package contains classes for handling OAuth authentication:

- Use `Auth` class to perform authentication and token handling.

The `com.youtube.api.controller.OAuthController` controller provides endpoints for OAuth authentication:

- `/authorize`: Initiates the OAuth authentication process and redirects to Google for authorization.
- `/Callback`: Handles the OAuth callback, exchanges the authorization code for a token, and saves the credentials in the session.

#### YouTube Services

The `com.youtube.api.service` package provides methods to interact with YouTube:

- Use `YouTubeService` class to retrieve a list of videos and upload videos.

The `com.youtube.api.controller.VideoController` controller handles video upload:

- `/video/upload`: Accepts video file, title, and description for upload. It also requires OAuth authentication.

#### User Interface

The `com.youtube.api.ui` package contains a simple UI class for OAuth testing:

- Run `OAuthTestUI` to open a window with a button for initiating OAuth authentication.

## Additional Resources

For a comprehensive tutorial on integrating YouTube APIs with Java, please refer to the following article on my website:

- [Integrating YouTube APIs with Java - A Comprehensive Tutorial](https://nemanjatanaskovic.com/integrating-youtube-apis-with-java-a-comprehensive-tutorial/)

This tutorial covers in-depth information and examples related to this project's functionality.

## Contributing

Contributions to this project are welcome. Please ensure you follow the coding standards and write necessary tests for new features.

## Acknowledgments

- [Google API Client Libraries for Java](https://developers.google.com/api-client-library/java)
- [YouTube API documentation](https://developers.google.com/youtube/v3)

## Contact

For any queries or contributions, please contact [Nemanja Tanaskovic](https://nemanjatanaskovic.com/contact)
