package customAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import customAPI.POJO.book.Book;
import customAPI.POJO.books.Books;
import customAPI.POJO.userBookshelves.UserBookshelves;
import customAPI.POJO.userBookshelvesMore.UserBookshelvesMore;
import customAPI.POJO.userBookshelvesMoreVolumes.UserBookshelvesMoreVolumes;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CustomCallsGoogleBookAPI {

    // The maximum number of results returned by a single request (must be between 1 and 40, inclusive)
    private static int maxResults = 5;

    // Method makes the request to the Google Books API and returns the result as a Books object
    private static Books commitBooksRequest(String urlAfter_v1) throws IOException {
        return (new ObjectMapper()).readValue(new URL("https://www.googleapis.com/books/v1/" + urlAfter_v1), Books.class);
    }

    // Method makes the request to the Google Books API and returns the result as a Book object
    private static Book commitBookRequest(String urlAfter_v1) throws IOException {
        return (new ObjectMapper()).readValue(new URL("https://www.googleapis.com/books/v1/" + urlAfter_v1), Book.class);
    }

    // Method makes the request to the Google Books API and returns the result as a UserBookshelves object
    private static UserBookshelves commitUserBookshelvesRequest(String urlAfter_v1) throws IOException {
        return (new ObjectMapper()).readValue(new URL("https://www.googleapis.com/books/v1/" + urlAfter_v1), UserBookshelves.class);
    }

    // Method makes the request to the Google Books API and returns the result as a UserBookshelvesMore object
    private static UserBookshelvesMore commitUserBookshelvesMoreRequest(String urlAfter_v1) throws IOException {
        return (new ObjectMapper()).readValue(new URL("https://www.googleapis.com/books/v1/" + urlAfter_v1), UserBookshelvesMore.class);
    }

    // Method makes the request to the Google Books API and returns the result as a UserBookshelvesMoreVolumes object
    private static UserBookshelvesMoreVolumes commitUserBookshelvesMoreVolumesRequest(String urlAfter_v1) throws IOException {
        return (new ObjectMapper()).readValue(new URL("https://www.googleapis.com/books/v1/" + urlAfter_v1), UserBookshelvesMoreVolumes.class);
    }

    // Method takes a SearchRequest object and returns a Books object based on the search request. The page parameter is the page number of the results to return (must be greater than 0)
    public static Books requestBooks(SearchRequest searchRequest, int page) throws IOException {
        if (page < 1) {
            // Page number must be greater than 0
            throw new IllegalArgumentException("Page start from 1");
        }

        // Creating the URL (after https://www.googleapis.com/books/v1/) to make the request to the Google Books API
        // URL encoding is used to ensure that the URL is valid (e.g. spaces are replaced with %20)
        String stringUrl = "volumes?q=" + URLEncoder.encode(searchRequest.getMetadataSearch(), StandardCharsets.UTF_8);
        if (!Objects.equals(searchRequest.getTitleSearch(), "")) {
            stringUrl += "&intitle:" + URLEncoder.encode(searchRequest.getTitleSearch(), StandardCharsets.UTF_8);
        }

        if (!Objects.equals(searchRequest.getAuthorSearch(), "")) {
            stringUrl += "&inauthor:" + URLEncoder.encode(searchRequest.getAuthorSearch(), StandardCharsets.UTF_8);
        }

        if (!Objects.equals(searchRequest.getPublisherSearch(), "")) {
            stringUrl += "&inpublisher:" + URLEncoder.encode(searchRequest.getPublisherSearch(), StandardCharsets.UTF_8);
        }

        if (!Objects.equals(searchRequest.getCategorySearch(), "")) {
            stringUrl += "&subject:" + URLEncoder.encode(searchRequest.getCategorySearch(), StandardCharsets.UTF_8);
        }

        if (!Objects.equals(searchRequest.getIsbnSearch(), "")) {
            stringUrl += "&isbn:" + URLEncoder.encode(searchRequest.getIsbnSearch(), StandardCharsets.UTF_8);
        }

        if (!Objects.equals(searchRequest.getLccnSearch(), "")) {
            stringUrl += "&lccn:" + URLEncoder.encode(searchRequest.getLccnSearch(), StandardCharsets.UTF_8);
        }

        if (!Objects.equals(searchRequest.getOclcSearch(), "")) {
            stringUrl += "&oclc:" + URLEncoder.encode(searchRequest.getOclcSearch(), StandardCharsets.UTF_8);
        }

        // The startIndex parameter is the index of the first result to return (must be greater than or equal to 0)
        // The maxResults parameter is the maximum number of results to return (must be between 1 and 40, inclusive)
        stringUrl += "&startIndex=" + (page - 1) * maxResults + "&maxResults=" + maxResults;

        // Making the request to the Google Books API and returning the result as a Books object
        return commitBooksRequest(stringUrl);
    }

    // Method takes a volumeId and returns the Book object with this volumeId
    public static Book requestBook(String volumeId) throws IOException {
        return commitBookRequest("volumes/" + volumeId);
    }

    // Method takes a userId and returns the UserBookshelves object with this userId
    public static UserBookshelves requestUserBookshelves(String userId) throws IOException {
        return commitUserBookshelvesRequest("users/" + userId + "/bookshelves");
    }

    // Method takes a userId and a bookshelfId and returns the UserBookshelvesMore object with this userId and bookshelfId
    public static UserBookshelvesMore requestUserBookshelvesMore(String userId, String bookshelfId) throws IOException {
        return commitUserBookshelvesMoreRequest("users/" + userId + "/bookshelves/" + bookshelfId);
    }

    // Method takes a userId and a bookshelfId and returns the UserBookshelvesMoreVolumes object with this userId and bookshelfId
    public static UserBookshelvesMoreVolumes requestUserBookshelvesMoreVolumes(String userId, String bookshelfId) throws IOException {
        return commitUserBookshelvesMoreVolumesRequest("users/" + userId + "/bookshelves/" + bookshelfId + "/volumes");
    }

    // Getter for the maxResults parameter
    public static int getMaxBooksByRequest() {
        return maxResults;
    }

    // Setter for the maxResults parameter (must be between 1 and 40, inclusive)
    public static void setMaxBooksByRequest(int maxResults) {
        if (maxResults < 1 || maxResults > 40) {
            throw new IllegalArgumentException("maxResults must be between 1 and 40");
        }
        CustomCallsGoogleBookAPI.maxResults = maxResults;
    }
}