## Description

In this project, we can get information about books via the Google Book API. The project is divided into two parts:

* CustomGoogleBookAPI (responsible for Google Book API calls, deserializing JSON responses, and returning POJOs)
* BookGoogleAPI (responsible for UI and connection with CustomGoogleBookAPI via Maven local repository)

## How to Install

You can either import the project in a Java IDE and run it or run the BookGoogleAPI-1.0.0.jar (with the command "java -jar CustomGoogleBookAPI-1.0.jar").
When the application starts, it opens a window in your default browser showing the main screen.

![image](https://user-images.githubusercontent.com/76475823/219655835-dcff242c-48b7-4d25-ac35-0a3b53f6699b.png)

### Main Screen

On this screen, users can make their requests (the "metadata search" is the only mandatory field on this page). When the user is done filling out those fields, at the bottom of this page, there is a submit button called "Search".

![image](https://user-images.githubusercontent.com/76475823/219659129-d0626f62-920f-4159-9964-e7ae625e3dc1.png)

On each page, if Book Google API is clicked, the browser redirects the user to the main page.

### Show Books

When "Search" is clicked, a screen with books is shown.

![image](https://user-images.githubusercontent.com/76475823/219659507-eb4747c2-1868-4dfc-a793-70fb2874bbda.png)

For each book, the user can see some information, such as the title, author(s), publisher (if there is any), published date, description, and page count. For more information about each book, users have to click on it.

The user can navigate through pages with the next button (only in the first page back button is disabled).

### Show Book

When a book is clicked, the user goes to a screen with all the information the Google API provides for this book.

![image](https://user-images.githubusercontent.com/76475823/219661532-f056e8c2-48f0-4942-aade-5d3b843d9bc8.png)


### Search by User ID

A user can search all the bookshelfs of another user by their ID.

![image](https://user-images.githubusercontent.com/76475823/219661992-0549e90b-c17e-4fc8-9c61-b8429527da50.png)

### Show the user's bookshelves

When "Search" is clicked, the user is redirected to this screen.

![image](https://user-images.githubusercontent.com/76475823/219662884-2cfb5440-1d31-435b-ab91-a619dc28a100.png)

The user can click on those cards in order to show information about them.

### Show User's Bookshelves Info

![image](https://user-images.githubusercontent.com/76475823/219663209-58a18feb-29b4-40dc-ab92-904a7e71054f.png)

The user can click on the card in order to see the contents of the book.

### Show Books

![image](https://user-images.githubusercontent.com/76475823/219663541-58f04d7a-7377-45c6-917e-54a4807c8f9a.png)

By clicking on any book, it will redirect the user to a screen where they can access all the information Google API provides for this book.

## History

![image](https://user-images.githubusercontent.com/76475823/220044222-544593fc-3e0f-4297-ac86-a84ecb25e40c.png)

The user can also show their history from the history tab in the main menu.

## Error Handling

There is custom error handling integrated into this project. Now we will see some examples:

* The user clicks on Search by User ID, an ID that does not contain only numbers.

![image](https://user-images.githubusercontent.com/76475823/219664157-fb102387-556a-49d2-9bed-81fd94495e9c.png)

* The user tries to make a request without internet access.

![image](https://user-images.githubusercontent.com/76475823/219664332-7476a5b6-cbae-4361-88ed-ac569bf9ad83.png)

* The user tries to enter a page that does not exist.

![image](https://user-images.githubusercontent.com/76475823/220044396-0737394f-9ef6-4e3b-91d3-c63ffb21b1ae.png)