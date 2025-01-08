package customAPI;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // Lombok annotations to generate getters and setters for all fields
public class SearchRequest {
    // This class is used to store the search parameters for the search request.

    private String metadataSearch;
    private String titleSearch;
    private String authorSearch;
    private String publisherSearch;
    private String categorySearch;
    private String isbnSearch;
    private String lccnSearch;
    private String oclcSearch;

    public SearchRequest() {
        this.metadataSearch = "";
        this.titleSearch = "";
        this.authorSearch = "";
        this.publisherSearch = "";
        this.categorySearch = "";
        this.isbnSearch = "";
        this.lccnSearch = "";
        this.oclcSearch = "";
    }

    // This method is used to return a string of the non-null/non-empty values of the search parameters.
    public String toStringNonNullValues() {
        StringBuilder toReturn = new StringBuilder();

        if (metadataSearch != null && !metadataSearch.equals("")) {
            toReturn.append("metadataSearch='").append(metadataSearch).append("',");
        }
        if (titleSearch != null && !titleSearch.equals("")) {
            toReturn.append("titleSearch='").append(titleSearch).append("',");
        }
        if (authorSearch != null && !authorSearch.equals("")) {
            toReturn.append("authorSearch='").append(authorSearch).append("',");
        }
        if (publisherSearch != null && !publisherSearch.equals("")) {
            toReturn.append("publisherSearch='").append(publisherSearch).append("',");
        }
        if (categorySearch != null && !categorySearch.equals("")) {
            toReturn.append("categorySearch='").append(categorySearch).append("',");
        }
        if (isbnSearch != null && !isbnSearch.equals("")) {
            toReturn.append("isbnSearch='").append(isbnSearch).append("',");
        }
        if (lccnSearch != null && !lccnSearch.equals("")) {
            toReturn.append("lccnSearch='").append(lccnSearch).append("',");
        }
        if (oclcSearch != null && !oclcSearch.equals("")) {
            toReturn.append("oclcSearch='").append(oclcSearch).append("',");
        }
        if (toReturn.length() > 0) {
            toReturn.deleteCharAt(toReturn.length() - 1);
        }
        return toReturn.toString();
    }
}
