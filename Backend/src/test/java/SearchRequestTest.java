import customAPI.SearchRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchRequestTest {

    // Checks toStringNonNullValues method with some examples
    @Test
    void checkToStringNonNullValues() {
        SearchRequest searchRequest = new SearchRequest();

        // Checks if the method returns an empty string when all the fields are empty
        assertEquals(searchRequest.toStringNonNullValues(), "");

        searchRequest.setMetadataSearch("test");
        // Checks if the method returns the correct string when the metadataSearch field is not empty and the others are empty
        assertTrue(searchRequest.toStringNonNullValues().contains("metadataSearch='test'"));
        assertFalse(searchRequest.toStringNonNullValues().contains("titleSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("authorSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("publisherSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("categorySearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("isbnSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("lccnSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("oclcSearch="));

        searchRequest.setTitleSearch("test");
        assertTrue(searchRequest.toStringNonNullValues().contains("metadataSearch='test'"));
        assertTrue(searchRequest.toStringNonNullValues().contains("titleSearch='test'"));
        assertFalse(searchRequest.toStringNonNullValues().contains("authorSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("publisherSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("categorySearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("isbnSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("lccnSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("oclcSearch="));

        searchRequest.setAuthorSearch("test");
        assertTrue(searchRequest.toStringNonNullValues().contains("metadataSearch='test'"));
        assertTrue(searchRequest.toStringNonNullValues().contains("titleSearch='test'"));
        assertTrue(searchRequest.toStringNonNullValues().contains("authorSearch='test'"));
        assertFalse(searchRequest.toStringNonNullValues().contains("publisherSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("categorySearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("isbnSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("lccnSearch="));
        assertFalse(searchRequest.toStringNonNullValues().contains("oclcSearch="));

        searchRequest.setAuthorSearch("");
        // Checks if the method returns the correct string when the authorSearch field is cleaned out and the others are not empty
        assertFalse(searchRequest.toStringNonNullValues().contains("authorSearch="));
    }
}