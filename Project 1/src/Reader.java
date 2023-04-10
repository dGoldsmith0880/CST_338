import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Name: Drake Goldsmith
 * Date: 2/12/23
 * Description: Class that holds info about a reader and
 * keeps track of the books they check out
 */

public class Reader {

    public final static int CARD_NUMBER_ = 0;
    public final static int NAME_ = 1;
    public final static int PHONE = 2;
    public final static int BOOK_COUNT_ = 3;
    public final static int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private int bookCount;
    private List<Book> books;

    public Reader(int cardNumber, String name, String phoneNumber) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phoneNumber;
        this.books = new ArrayList<Book>();
        this.bookCount = 0;
    }

    public Code addBook(Book book) {
        if (this.hasBook(book)) {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        } else {
            this.books.add(book);
            bookCount++;
            return Code.SUCCESS;
        }
    }

    public Code removeBook(Book book) {
        Iterator<Book> bookIt = books.iterator();
        while (bookIt.hasNext()) {
            Book index = bookIt.next();
            if (index.equals(book)) {
                bookIt.remove();
                bookCount--;
                return Code.SUCCESS;
            }
        }
        return Code.READER_DOESNT_HAVE_BOOK_ERROR;
    }

    //    Returns true if the user has the book in their list
    public boolean hasBook(Book book) {
        for (Book index : books) {
            if (index.equals(book)) {
                return true;
            }
        }
        return false;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        StringBuilder bookString = new StringBuilder(this.name + " (#" + cardNumber + ") " + "has checked out {");
        for (int i = 0; i < books.size(); i++) {
            if (i > 0) {
                bookString.append(", ");
            }
            bookString.append(books.get(i).toString());
        }
        bookString.append("}");
        return bookString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (cardNumber != reader.cardNumber) return false;
        if (!Objects.equals(name, reader.name)) return false;
        return Objects.equals(phone, reader.phone);
    }

    @Override
    public int hashCode() {
        int result = cardNumber;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
