package gr.unipi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import customAPI.CustomCallsGoogleBookAPI;
import customAPI.POJO.books.Books;
import customAPI.POJO.userBookshelves.UserBookshelves;
import customAPI.POJO.userBookshelvesMore.UserBookshelvesMore;
import customAPI.POJO.userBookshelvesMoreVolumes.UserBookshelvesMoreVolumes;
import customAPI.SearchRequest;
import gr.unipi.Helper.HistoryRecord;
import gr.unipi.error.CustomErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static gr.unipi.ProjectApplication.addHistoryRecord;
import static gr.unipi.ProjectApplication.historyRecordList;

@Controller
@RequestMapping("/") // This means URLs start with / (after Application path)
public class MainScreenController {

    @GetMapping({"searchByFields", ""})
    public String searchByFields(Model model) {
        // This method is called when the user wants to search by fields
        model.addAttribute("searchRequest", new SearchRequest());
        return "mainMenu/searchByFields.html";
    }

    @GetMapping("searchByFields/commit")
    public String searchByFields_commit(@ModelAttribute("searchRequest") SearchRequest searchRequest, @ModelAttribute("wantToGoPage") int wantToGoPage, Model model) {
        // This method is called when the user clicks submit in the search by fields page
        try {
            Books books = CustomCallsGoogleBookAPI.requestBooks(searchRequest, wantToGoPage); // Request the books from the API
            model.addAttribute("currentPage", wantToGoPage); // Add the current page to the model
            model.addAttribute("searchRequest", searchRequest); // Add the search request to the model (it will be used when user wants to change the page)
            if (books.getItems() == null) {
                // If there are no books, add an empty list to the model (so that the page will not crash when it tries to call itemList.size())
                model.addAttribute("itemList", new ArrayList<customAPI.POJO.books.Item>());
            } else {
                model.addAttribute("itemList", books.getItems());
            }
        } catch (IOException e) {
            // If there is an error, go to the error screen
            return CustomErrorController.goToErrorScreen(e, model);
        }
        // Add the history record
        addHistoryRecord(new HistoryRecord("Searched book by fields: " + searchRequest.toStringNonNullValues()));
        return "showBooks.html";
    }

    @GetMapping("searchByUserID")
    public String searchByUserID() {
        // This method is called when the user wants to search by user ID
        return "mainMenu/searchByUserID.html";
    }

    @GetMapping("searchByUserID/commit")
    public String searchByUserID_commit(@RequestParam("userId") String userId, Model model) {
        // This method is called when the user clicks submit in the search by user ID page
        try {
            // Request the user's bookshelves from the API
            UserBookshelves userBookshelves = CustomCallsGoogleBookAPI.requestUserBookshelves(userId);
            if (userBookshelves.getItems() == null) {
                // If there are no bookshelves, add an empty list to the model
                model.addAttribute("itemList", new ArrayList<customAPI.POJO.userBookshelves.Item>());
            } else {
                model.addAttribute("itemList", userBookshelves.getItems());
            }
            // Add the user ID to the model (it will be used when user clicks on a bookshelf)
            model.addAttribute("userId", userId);
        } catch (IOException e) {
            // If there is an error, go to the error screen
            return CustomErrorController.goToErrorScreen(e, model);
        }
        // Add the history record
        addHistoryRecord(new HistoryRecord("Searched for bookshelves from user with ID: '" + userId + "'"));
        return "showUserBookshelves.html";
    }

    @GetMapping("showUserBookshelvesMore/{userId}/{bookshelfId}")
    public String showUserBookshelvesMore(@PathVariable String userId, @PathVariable String bookshelfId, Model model) {
        // This method is called when the user clicks on a bookshelf in the user bookshelves page
        try {
            // Request the bookshelf's information from the API
            UserBookshelvesMore userBookshelves = CustomCallsGoogleBookAPI.requestUserBookshelvesMore(userId, bookshelfId);

            // Add the bookshelf's information to the model or empty if there isn't any
            model.addAttribute("item", Objects.requireNonNullElseGet(userBookshelves, UserBookshelvesMore::new));

            // Add the user ID and bookshelf ID to the model (it will be used when user clicks on a volume in the bookshelf)
            model.addAttribute("userId", userId);
        } catch (IOException e) {
            // If there is an error, go to the error screen
            return CustomErrorController.goToErrorScreen(e, model);
        }
        // Add the history record
        addHistoryRecord(new HistoryRecord("Requested information for bookshelf with ID '" + bookshelfId + "' from user with ID '" + userId + "'"));
        return "showUserBookshelvesMore.html";
    }

    @GetMapping("showUserBookshelvesMoreVolumes/{userId}/{bookshelfId}")
    public String showUserBookshelvesMoreVolumes(@PathVariable String userId, @PathVariable String bookshelfId, Model model) {
        // This method is called when the user clicks on a volume in the showUserBookshelvesMore page
        try {
            // Request the volume's information from the API
            UserBookshelvesMoreVolumes userBookshelvesMoreVolumes = CustomCallsGoogleBookAPI.requestUserBookshelvesMoreVolumes(userId, bookshelfId);
            if (userBookshelvesMoreVolumes.getItems() == null) {
                // If there are no volumes, add an empty list to the model
                model.addAttribute("itemList", new ArrayList<customAPI.POJO.userBookshelvesMoreVolumes.Item>());
            } else {
                model.addAttribute("itemList", userBookshelvesMoreVolumes.getItems());
            }
        } catch (IOException e) {
            // If there is an error, go to the error screen
            return CustomErrorController.goToErrorScreen(e, model);
        }
        // Add the history record
        addHistoryRecord(new HistoryRecord("Requested volumes from bookshelf with ID '" + bookshelfId + "' from user with ID '" + userId + "'"));
        return "showUserBookshelvesMoreVolumes.html";
    }

    @GetMapping("showHistory")
    public String showHistory(Model model) {
        // This method is called when the user wants to see the history
        model.addAttribute("historyRecordList", historyRecordList);
        return "mainMenu/history.html";
    }

    @GetMapping("showBook/{bookId}")
    public String showBook(@PathVariable String bookId, Model model) {
        // This method is called when the user wants to see the information of a book
        try {
            // Request the book's information from the API
            model.addAttribute("bookVolumeJSON", (new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(CustomCallsGoogleBookAPI.requestBook(bookId)));
        } catch (IOException e) {
            // If there is an error, go to the error screen
            return CustomErrorController.goToErrorScreen(e, model);
        }
        // Add the history record
        addHistoryRecord(new HistoryRecord("Requested information for book with ID '" + bookId + "'"));
        return "showBook.html";
    }

}
