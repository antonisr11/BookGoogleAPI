import customAPI.CustomCallsGoogleBookAPI;
import customAPI.SearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CustomCallsGoogleBookAPITest {

    private static SearchRequest searchRequest = new SearchRequest();

    // Clears some common data before each test
    @BeforeEach
    // Before each test
    void clearMaxBooksByRequest() {
        CustomCallsGoogleBookAPI.setMaxBooksByRequest(5);
        searchRequest = new SearchRequest();
        searchRequest.setMetadataSearch("1984");
    }

    // Checks if the maxBooksByRequest is set correctly
    @Test
    void getMaxBooksByRequestRestrictions() {
        assertThrows(IllegalArgumentException.class, () -> CustomCallsGoogleBookAPI.setMaxBooksByRequest(-1));
        assertThrows(IllegalArgumentException.class, () -> CustomCallsGoogleBookAPI.setMaxBooksByRequest(0));
        assertThrows(IllegalArgumentException.class, () -> CustomCallsGoogleBookAPI.setMaxBooksByRequest(41));
        assertDoesNotThrow(() -> CustomCallsGoogleBookAPI.setMaxBooksByRequest(1));
        assertDoesNotThrow(() -> CustomCallsGoogleBookAPI.setMaxBooksByRequest(40));

        int random = (int) (Math.random() * 40) + 1;
        CustomCallsGoogleBookAPI.setMaxBooksByRequest(random);
        assertEquals(random, CustomCallsGoogleBookAPI.getMaxBooksByRequest());
    }

    // Checks if the requestBooks method returns at least one book
    @Test
    void requestBooksOk() throws IOException {
        CustomCallsGoogleBookAPI.setMaxBooksByRequest(1);
        assertThrows(IllegalArgumentException.class, () -> CustomCallsGoogleBookAPI.requestBooks(searchRequest, 0));
        assertFalse(CustomCallsGoogleBookAPI.requestBooks(searchRequest, 1).getItems().isEmpty());
    }

    // Checks if the requestBook method returns a book
    @Test
    void requestBookOk() throws IOException {
        CustomCallsGoogleBookAPI.setMaxBooksByRequest(1);
        String volumeId = CustomCallsGoogleBookAPI.requestBooks(searchRequest, 1).getItems().get(0).getId();

        assertFalse(CustomCallsGoogleBookAPI.requestBook(volumeId).getVolumeInfo().getTitle().isEmpty());
    }
}