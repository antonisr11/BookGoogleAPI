package gr.unipi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // This annotation is used to run the tests in a specific order
class ProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc; // This is used to perform HTTP requests

    @Test
    @Order(1)
    void contextLoads() {
        // This test is just to check if the context loads
        assertTrue(true);
    }

    @Test
    @Order(2)
    public void mainMenuEndpointExistence() throws Exception {
        // This test checks if the main menu endpoints exist and return the correct HTML
        mockMvc.perform(get("/showHistory")).andExpect(status().isOk()).andExpect(content().string(containsString("<title>Show History</title>")));
        mockMvc.perform(get("/searchByFields")).andExpect(status().isOk()).andExpect(content().string(containsString("<title>Search By Fields</title>")));
        mockMvc.perform(get("/searchByUserID")).andExpect(status().isOk()).andExpect(content().string(containsString("<title>Search by User ID</title>")));
    }

    @Test
    @Order(3)
    public void searchByID_102701940585560677579_commit() throws Exception {
        // This test checks if the search by ID endpoint exists and returns the correct HTML when the user ID is 102701940585560677579
        mockMvc.perform(get("/searchByUserID/commit?userId=102701940585560677579")).andExpect(status().isOk()).andExpect(content().string(containsString("<title>Show User's Bookshelves</title>")));
    }

    @Test
    @Order(4)
    public void isHistoryRecordAdded() throws Exception {
        // This test checks if the history record of the 3rd test is added to the list
        mockMvc.perform(get("/showHistory")).andExpect(status().isOk()).andExpect(content().string(containsString("Searched for bookshelves from user with ID: &#39;102701940585560677579&#39;")));
    }

    @Test
    @Order(5)
    public void showUserBookshelvesMore_102701940585560677579_checkOnClicks() throws Exception {
        // This test checks if the bookshelves of the user with ID 102701940585560677579 accessible
        MvcResult result = mockMvc.perform(get("/searchByUserID/commit?userId=102701940585560677579")).andExpect(status().isOk()).andReturn();

        // Get the HTML of the page
        String html = result.getResponse().getContentAsString();

        // Parse the HTML to isolate the card-body elements
        Document doc = Jsoup.parse(html);
        for (Element element : doc.getElementsByClass("card-body")) {
            // Get the onclick attribute of the card-body element
            String onclickLink = element.attr("onclick").replace("location.href='http://localhost:8080/", "").replace("';", "");

            // If the onclick attribute is empty, then skip this element (we are only interested in the card-body elements that haven't an onclick attribute)
            if (onclickLink.equals("")) {
                continue;
            }

            // Check if page will be accessible if user clicks in onclick attribute
            mockMvc.perform(get("/" + onclickLink)).andExpect(status().isOk()).andExpect(content().string(not(containsString("<title>Error Screen</title>"))));
        }
    }

}
