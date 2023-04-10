import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Title: Shelf.java
 * @Author: Drake Goldsmith
 * @Since: 4 March 2023
 * Description: A class that replicates a book shelf that is categorized by subject
 */

public class Shelf {
    public final static int SHELF_NUMBER_ = 0;
    public final static int SUBJECT_ = 1;
    
    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    Shelf(int number, String subject) {
        this.shelfNumber = number;
        this.subject = subject;
        books = new HashMap<>();
    }

//    Adds the parameter 'book' to the HashMap of books stored on the shelf
//    If the book already exists in the HashMap, the count should be incremented,
//    and a SUCCESS code should be returned. If the book does NOT exist on the
//    shelf, and the subject matches, add the book to the shelf, set the count
//    of the book to 1, and return a SUCCESS Code. If the book does NOT exist
//    on the shelf, and the subject DOES NOT match, return a
//    SHELF_SUBJECT_MISMATCH_ERROR Code.
//
//    If the book is successfully added, print a message that displays the
//    toString for the Book, concatenated with " added to shelf " concatenated
//    with the toString of the current Shelf.
    public Code addBook(Book book) {
        if (books.containsKey(book)) {
            int holder = books.get(book);
            holder++;
            books.put(book, holder);
            System.out.println(book.getTitle() + " added to shelf " + this.getSubject());
            return Code.SUCCESS;
        } else {
            String compare = book.getSubject();
            if (compare.equals(subject)) {
                books.put(book, 1);
                System.out.println(book.getTitle() + " added to shelf " + this.getSubject());
                return Code.SUCCESS;
            } else {
                return Code.SHELF_SUBJECT_MISMATCH_ERROR;
            }
        }
    }

//    This returns the count of the book parameter stored on the shelf.
//    If the book is not stored on the shelf it should return a -1
    public int getBookCount(Book name) {
        if (books.containsKey(name)) {
            return books.get(name);
        }
        return -1;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    /*Returns a String listing all of the books on the shelf. The listing of
    books should match the following  (Each of the following is a separate
            shelf):

            2 books on shelf: 2 : education
    Headfirst Java by Grady Booch ISBN:1337 2

            1 book on shelf: 3 : Adventure
    Count of Monte Cristo by Alexandre Dumas ISBN:5297 1

            3 books on shelf: 1 : sci-fi
    Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87 2
    Dune by Frank Herbert ISBN:34-w-34 1*/
    public String listBooks() {
        int book_num = 0;
        StringBuilder bookEntry = new StringBuilder();
        for (Map.Entry<Book, Integer> bookList : books.entrySet()) {
                book_num += bookList.getValue();
                bookEntry.append(bookList.getKey().toString() + " " + bookList.getValue() + "\n");
        }
        StringBuilder sb = new StringBuilder();
        if (book_num > 1) {
            sb.append("\t" + book_num + " books on shelf: " + shelfNumber + " : " + subject + "\n");
        } else {
            sb.append("\t" + book_num + " book on shelf: " + shelfNumber + " : " + subject + "\n");
        }
        sb.append(bookEntry.toString());
        return sb.toString();
    }

//    If the parameter book is not present in the books hashMap, return a
//    BOOK_NOT_IN_INVENTORY_ERROR Code and print a message like Hitchhikers
//    Guide To the Galaxy is not on shelf sci-fi
//
//    If the parameter book IS present in the books hashMap but has a count
//    of 0, return a BOOK_NOT_IN_INVENTORY_ERROR Code and print a message like
//    No copies of Hitchhikers Guide To the Galaxy remain on shelf sci-fi
//
//    If the parameter book is in the books hashMap and the count is greater
//    than 0 decrement the count of books in the hashMap, return a SUCCESS Code
//    and print a message like Hitchhikers Guide To the Galaxy successfully
//    removed from shelf sci-fi
    public Code removeBook(Book book) {
        if (books.containsKey(book)) {
            int num = books.get(book);
            if(num == 0) {
                System.out.println("No copies of " + book.getTitle() + " on shelf " + subject);
                return Code.BOOK_NOT_IN_INVENTORY_ERROR;
            } else {
                num--;
                books.put(book, num);
                System.out.println(book.toString() + " successfully removed from shelf " + subject);
                return Code.SUCCESS;
            }
        } else {
            System.out.println(book.getTitle() + " is not on shelf " + this.getSubject());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
    }

    public void setBooks(HashMap<Book, Integer> set) {
        this.books = set;
    }

    public void setShelfNumber(int num) {
        this.shelfNumber = num;
    }

    public void setSubject(String name) {
        this.subject = name;
    }

    /*Returns a string that looks like:
            2 : education

    Where 2 is the shelfNumber field and  education is the subject.*/
    @Override
    public String toString() {
        return shelfNumber + " : " + subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelf shelf = (Shelf) o;

        if (shelfNumber != shelf.shelfNumber) return false;
        return Objects.equals(subject, shelf.subject);
    }

    @Override
    public int hashCode() {
        int result = shelfNumber;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }
}
