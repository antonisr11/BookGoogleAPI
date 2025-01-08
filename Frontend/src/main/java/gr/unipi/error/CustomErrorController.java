package gr.unipi.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class CustomErrorController implements ErrorController {

    public static String goToErrorScreen(int statusCode, String errorMessage, Model model) {
        // This method is called when there was an error, and we want to show it to the user
        model.addAttribute("code", statusCode);
        model.addAttribute("message", errorMessage);
        return "error/errorScreen.html";
    }

    public static String goToErrorScreen(IOException e, Model model) {
        // This method is called when there is an error from the CustomGoogleBookAPI

        if (e.getMessage().equals("www.googleapis.com")){
            return goToErrorScreen(500, "Google Books API fails to respond. Are you sure you have internet access?", model);
        }
        if (e.getMessage().startsWith("Server returned HTTP response code: 400 for URL:")){
            return goToErrorScreen(400, "Wrong search request. Are you sure the fields you entered are correct?", model);
        }

        return goToErrorScreen(500, e.getMessage(), model);
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // This method is called when there is an error (it is called by Spring)
        int statusCode = 500;
        String errorMessage = "Internal Server Error";
        try {
            // Get the error code and the error message
            statusCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
            if (statusCode == 404) {
                errorMessage = "Page not found";
            } else {
                errorMessage = ((Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)).getCause().getCause().getMessage();
            }
        } catch (Exception ignored) {
            // If there is an error, status code will be 500 and error message will be "Internal Server Error"
        }
        // Go to the error screen
        return goToErrorScreen(statusCode, errorMessage, model);
    }

}
