import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Title: Library.java
 * Name: Drake Goldsmith
 * Date: 3/24/23
 * Description: Library class object that uses the Book, Reader, Shelf, and Code classes
 */

public class Library {

    public static final int LENDING_LIMIT = 5;
    private String name;
    private static int libraryCard;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    Library(String name) {
        this.name = name;
        this.readers = new ArrayList<>();
        this.shelves = new HashMap<>();
        this.books = new HashMap<>();
    }

    /**
     * init(String filename)
     * This is the method that is responsible for parsing the Library00.csv file into a collection of objects.
     * The parameter filename is the name of the file to be parsed (in this case Library00.csv).
     * The init method will call other methods from this class to aid in the processing of the csv file.
     * <p>
     * The csv files will always have a structure like that shown below. The csv file will contain a list of
     * comma separated values that represent books, shelves, and readers respectively. The numbers 9,3,and 4 all
     * correspond to the number of records to follow. Each of these will be parsed by their respective init
     * methods ( initBooks, initShelves, and initReader respectively)
     * <p>
     * If the filename can't be found, return a Code.FILE_NOT_FOUND_ERROR.
     * <p>
     * Use the convertInt method to return the number of books to parse. Details of the covertInt method below.
     * <p>
     * If the number returned by parseInt is less than 0 (corresponding to an error code) return the error code
     * related to the number (Enums may be iterated). If there is no corresponding error return a Code.UNKNOWN_ERROR
     * It may be wise to make a method for this.
     * <p>
     * After the books have been parsed, call the listBooks() method.
     * <p>
     * Follow the same procedure for shelf and reader, listing the parsed shelves and the parsed readers after
     * each call to their respective 'init' methods. Returning any related error codes.
     */
    public Code init(String filename) {
        // Open the file
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);

            // Parse the number of books
            String recordCountString = scanner.nextLine();
            int bookCount = convertInt(recordCountString, Code.BOOK_COUNT_ERROR);
            if (bookCount < 0) {
                return Code.values()[-bookCount];
            }
            initBooks(bookCount, scanner);
            listBooks();

            // Parse the number of shelves
            recordCountString = scanner.nextLine();
            int shelfCount = convertInt(recordCountString, Code.SHELF_COUNT_ERROR);
            if (shelfCount < 0) {
                return Code.values()[-shelfCount];
            }
            initShelves(shelfCount, scanner);
            listShelves(true);

            // Parse the number of readers
            recordCountString = scanner.nextLine();
            int readerCount = convertInt(recordCountString, Code.READER_COUNT_ERROR);
            if (readerCount < 0) {
                return Code.values()[-readerCount];
            }
            initReader(readerCount, scanner);
            listReaders(true);

            scanner.close();
        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }
        return Code.SUCCESS;
    }


    /**
     * initBooks(int bookCount, Scanner scan)
     * This method takes an integer, the number of books to parse, and a Scanner.
     * The scanner represents the current position of the CSV file that is being parsed.
     * <p>
     * The int bookCount represents the number of books to parse.
     * If bookCount is less than 1, return a Code.LIBRARY_ERROR
     * (this should never happen but let's be defensive.)
     * <p>
     * Iterate through the records in the CSV file converting each line to a book. Use the constant fields
     * in the Book object to identify the indexes of the relevant data from the CSV file.
     * <p>
     * Use convertInt to parse the page number. If the page number is 0 or less, return a Code.PAGE_COUNT_ERROR
     * <p>
     * Use the converDate method described below to parse the date. If the date comes back as a null return
     * a Code.DATE_CONVERSION_ERROR
     * EDIT: if ConvertDate is written correctly this will likely never happen.  This is here because I am
     * very paranoid and have had a lot of issues with null objects.
     * <p>
     * Finally call the addBook() method to add the book to the list of books in the Library object.
     * <p>
     * Return a Code.SUCCESS
     */
    private Code initBooks(int bookCount, Scanner scan) {
        if (bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }
        System.out.println("parsing " + bookCount + " books");
        for (int i = 0; i < bookCount; i++) {
            String value = scan.nextLine();
            System.out.println("parsing book: " + value);
            if (value.length() > 0) {
                String[] words = value.split(",");
                String isbn = words[Book.ISBN_];
                String title = words[Book.TITLE_];
                String subject = words[Book.SUBJECT_];
                int pageCount = convertInt(words[Book.PAGE_COUNT_], Code.SUCCESS);
                if (pageCount < 1) {
                    return Code.PAGE_COUNT_ERROR;
                }
                String author = words[Book.AUTHOR_];
                LocalDate date = convertDate(words[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
                if (date == null) {
                    return Code.DATE_CONVERSION_ERROR;
                }
                this.addBook(new Book(isbn, title, subject, pageCount, author, date));
            }
        }
        System.out.println("SUCCESS");
        return Code.SUCCESS;
    }

    /**
     * initShelves(int shelfCount, Scanner scan)
     * This method works very similarly to initBooks. The int shelfCount is used to determine how many shelves
     * to parse out of the Scanner object.
     * <p>
     * If shelfCount is less than 1 return a Code.SHELF_COUNT_ERROR
     * <p>
     * If the number for the shelf does not parse correctly, return a Code.SHELF_NUMBER_PARSE_ERROR
     * <p>
     * Use the addShelf() method to add the shelves to the HashMap shelves in the Library object.
     * <p>
     * Finally check that the size of the shelves object matches the value for shelf count, if it does return
     * a Code.SUCCESS. If it does not print a message that reads
     * "Number of shelves doesn't match expected"
     * And return a Code.SHELF_NUMBER_PARSE_ERROR
     */
    private Code initShelves(int shelfCount, Scanner scan) {
        if (shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }
        System.out.println("parsing " + shelfCount + " shelves");
        for (int i = 0; i < shelfCount; i++) {
            String value = scan.nextLine();
            System.out.println("Parsing Shelf: " + value);
            if (value.length() > 0) {
                String[] words = value.split(",");
                int shelfNum = convertInt(words[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR);
                String shelfSubject = words[Shelf.SUBJECT_];
                this.addShelf(new Shelf(shelfNum, shelfSubject));
            }
        }
        if (shelfCount == this.shelves.size()) {
            return Code.SUCCESS;
        }
        System.out.println("Number of shelves doesn't match expected");
        return Code.SHELF_NUMBER_PARSE_ERROR;
    }

    /**
     * initReader(int readerCount, Scanner scan)
     * Works in the same way as initBooks and initShelves. The int is used to determine how many readers to
     * parse and the scanner contains the current Library00.csv file.
     * <p>
     * If the reader count is less than or equal to 0 return a Code.READER_COUNT_ERROR
     * <p>
     * Add each reader to the list of readers (use the List.add() addReader() method)
     * <p>
     * The thing that makes initReader tricky is that each reader may have a list of books. The number of
     * books to parse is indexed by Reader.BOOK_COUNT_ and the starting position of the first book is stored
     * in Reader.BOOK_START_
     * <p>
     * Find the books in the Library object using the getBookByISBN() method.
     * <p>
     * If a book does not exist in the library print out the string "ERROR" and continue to parse the remaining books.
     * <p>
     * Use the convertDate method to convert the due date to a LocalDate object.
     * <p>
     * Once the book is found, use the checkOutBook method to add the book to the reader.
     * <p>
     * When complete return a Code.SUCCESS
     */
    private Code initReader(int readerCount, Scanner scan) {
        if (readerCount < 1) {
            return Code.READER_COUNT_ERROR;
        }
        for (int i = 0; i < readerCount; i++) {
            String value = scan.nextLine();
            if (value.length() > 0) {
                String[] words = value.split(",");
                int cardNumber = convertInt(words[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR);
                String name = words[Reader.NAME_];
                String phone = words[Reader.PHONE];

                Reader reader1 = new Reader(cardNumber, name, phone);
                this.addReader(reader1);

                int bookCount = convertInt(words[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR);
                int start = Reader.BOOK_START_;

                for (int j = 0; j < bookCount; ++j) {
                    String isbnCheck = words[start++];
                    Book holder = this.getBookByISBN(isbnCheck);
                    if (holder == null) {
                        System.out.println("ERROR");
                    }
                    LocalDate date = convertDate(words[start++], Code.DATE_CONVERSION_ERROR);
                    checkOutBook(reader1, holder);
                    System.out.println("SUCCESS");
                }
            }
        }
        System.out.println("SUCCESS");
        return Code.SUCCESS;
    }

    /**
     * addbook(Book newBook)
     * If the HashMap of books in the Library object contains the book, increment the count and print out a String like:
     * "3 copies of [Book.title] in the stacks"
     * Where 3 is the number of copies and Best Served Cold is the title of the book that has been added.
     * <p>
     * If the book does NOT exist add the book to the HashMap of books setting the count to 1.
     * Print out a like like:
     * "[Book.title] added to the stacks."
     * Where Best Served Cold is the title of the book that has been added.
     * <p>
     * If there is a shelf with a matching subject, add the book to that shelf and return a Code.SUCCESS
     * <p>
     * Otherwise print out
     * "No shelf for [subject] books"
     * Where Adventure is the subject of the book and return a Code.SHELF_EXISTS_ERROR
     */
    public Code addBook(Book newBook) {
        if (books.containsKey(newBook)) {
            int holder = books.get(newBook);
            holder++;
            books.put(newBook, holder);
            System.out.println(holder + " copies of " + newBook.getTitle() + " in the stacks.");
        } else {
            books.put(newBook, 1);
            System.out.println(newBook.toString() + " added to the stacks.");
        }
        String compare = newBook.getSubject();
        for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
            if (compare.equals(shelfList.getKey())) {
                shelfList.getValue().addBook(newBook);
                return Code.SUCCESS;
            }
        }
        System.out.println("No shelf for " + compare + " books.");
        return Code.SHELF_EXISTS_ERROR;
    }

    /**
     * returnBook(Reader reader, Book book)
     * If the reader does not have the book in their List print a message like
     * "[Reader.name] doesn't have [book title] checked out"
     * And return a Code.READER_DOESNT_HAVE_BOOK_ERROR
     * <p>
     * Check that the book exists in the library, if not, return a Code.BOOK_NOT_IN_INVENTORY
     * .
     * Print out
     * "[Reader.getName] is returning [book]"
     * Use the Reader.removeBook(book) method to remove the book and store the Code that is returned.
     * <p>
     * If the code is a Code.SUCCESS then call the returnBook(book) method and return the resulting code.
     * <p>
     * If the code is NOT a success print
     * "Could not return [Book]"
     * <p>
     * And return the Code object
     */
    public Code returnBook(Reader read, Book book) {
        if (!read.hasBook(book)) {
            System.out.println(read.getName() + " doesn't have " + book.toString() + " checked out.");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        } else if (!books.containsKey(book)) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            System.out.println(read.getName() + " is returning " + book.toString());
            Code holder = read.removeBook(book);
            if (holder.equals(Code.SUCCESS)) {
                return returnBook(book);
            } else {
                System.out.println("Could not return " + book.toString());
                return holder;
            }
        }
    }

    /**
     * returnBook(Book book)
     * Check if there is a shelf with a matching subject in the HashMap of shelves, if not print out
     * "No shelf for [book]"
     * And return a Code.SHELF_EXISTS_ERROR
     * Otherwise call the shelf.addBook(book) method for the appropriate shelf from the HashMap stored
     * in the library object.
     */
    public Code returnBook(Book book) {
        String compare = book.getSubject();
        for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
            if (compare.equals(shelfList.getKey())) {
                shelfList.getValue().addBook(book);
                return Code.SUCCESS;
            }
        }
        System.out.println("No shelf for " + book.getTitle());
        return Code.SHELF_EXISTS_ERROR;
    }

    /**
     * addBookToShelf(Book book, Shelf shelf)
     * Try to return the book using the returnBook(book) method, if the returnBook method returns a
     * Code.SUCCESS then return a Code.SUCCESS and exit.
     * <p>
     * If the shelf that was supplied does not match the subject of the book supplied return a
     * Code.SHELF_SUBJEC_MISMATCH_ERROR
     * <p>
     * Use the shelf.addBook(book) method to add the book to the shelf. If it is successful print
     * "[book] added to shelf"
     * And return a Code.SUCCESS
     * <p>
     * Otherwise print
     * "Could not add [book] to shelf"
     * <p>
     * And return whatever code was returned from the shelf.addBook(book) method.
     */
    private Code addBookToShelf(Book book, Shelf shelf) {
        Code result = returnBook(book);
        if (result.equals(Code.SUCCESS)) {
            return result;
        } else if (!shelf.getSubject().equals(book.getSubject())) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        } else {
            result = shelf.addBook(book);
            if (result.equals(Code.SUCCESS)) {
                System.out.println(book.getTitle() + " added to shelf");
                return Code.SUCCESS;
            } else {
                System.out.println("Could not add " + book.getTitle() + " to shelf.");
                return result;
            }
        }
    }

    /**
     * listBooks()
     * List all the books at the library, even those not on shelves. The print out should look like:
     * <p>
     * 4 copies of Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87
     * 3 copies of Headfirst Java by Grady Booch ISBN:1337
     * 1 copies of Dune by Frank Herbert ISBN:34-w-34
     * 1 copies of Count of Monte Cristo by Alexandre Dumas ISBN:5297
     * <p>
     * Return the total count of all books at the library. For the list above it would return 9 (4+3+1+1)
     */
    public int listBooks() {
        int sum = 0;
        for (Map.Entry<Book, Integer> bookList : books.entrySet()) {
            Book bookHolder = bookList.getKey();
            Integer bookCount = bookList.getValue();
            sum += bookCount;
            System.out.println(bookCount + " copies of " + bookHolder.getTitle() + " by " + bookHolder.getAuthor()
                    + " ISBN: " + bookHolder.getIsbn());
        }
        System.out.println();
        return sum;
    }

    /**
     * checkoutBook(Reader reader, Book book)
     * <p>
     * Check for the reader, if no reader print out
     * "[name] doesn't have an account here"
     * and return a Code.READER_NOT_IN_LIBRARY_ERROR
     * <p>
     * Check the reader has not exceeded the lending limit, if they have print out
     * "[NAME] has reached the lending limit, ([LENDING_LIMIT_])"
     * And return a Code.BOOK_LIMIT_REACHED_ERROR
     * <p>
     * Check for the book in the HashMap books in the Library object, if it isn't there print out
     * "ERROR: could not find [BOOK]"
     * and return a Code.BOOK_NOT_IN_INVENTORY_ERROR
     * <p>
     * Check for a shelf for the book, if no shelf exists print out
     * "no shelf for [subject] books!"
     * return a Code.SHELF_EXISTS_ERROR
     * <p>
     * If the Shelf has less than 1 copy of the book print out
     * "ERROR: no copies of [book] remain"
     * And return a Code.BOOK_NOT_IN_INVENTORY_ERROR
     * <p>
     * Use the reader.addBook() method if it does not return a Code.SUCCESS
     * Print out
     * "Couldn't checkout [book]"
     * and return the code that was returned from the addBook method
     * <p>
     * Call shelf.removeBook(book)
     * If the code from removeBook is a success print
     * "[Book] checked out successfully"
     * Return the code
     */
    public Code checkOutBook(Reader reader, Book book) {
        boolean readerBool = false;
        for (Reader name : readers) {
            if (reader.equals(name)) {
                readerBool = true;
                break;
            }
        }
        if (!readerBool) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        } else if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, " + LENDING_LIMIT);
            return Code.BOOK_LIMIT_REACHED_ERROR;
        } else if (!books.containsKey(book)) {
            System.out.println("ERROR: could not find " + book.getTitle());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }
        Shelf holder = shelves.get(book.getSubject());
        if (holder.getBookCount(book) < 1) {
            System.out.println("ERROR: No copies of " + book.getTitle() + " remain.");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        Code result = reader.addBook(book);
        if (!result.equals(Code.SUCCESS)) {
            System.out.println("Couldn't checkout " + book.getTitle());
            return result;
        }
        result = holder.removeBook(book);
        if (result.equals(Code.SUCCESS)) {
            System.out.println(book.toString() + " checked out successfully.");
        }
        return result;
    }

    /**
     * getBookByISBN(String isbn)
     * Returns a Book object from the HashMap of books in the Library object that matches the book the String isbn.
     * If no such object exists, print out
     * "ERROR: Could not find a book with isbn: [isbn]"
     * And return null
     */
    public Book getBookByISBN(String isbn) {
        for (Map.Entry<Book, Integer> bookList : books.entrySet()) {
            if (bookList.getKey().getIsbn().equals(isbn)) {
                return bookList.getKey();
            }
        }
        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    /**
     * listShelves(boolean showbooks)
     * If the boolean showbooks is true, call the 'listbooks' method for each shelf.
     * If the boolean showbooks is false display the toString of the each shelf object
     * Return a Code.SUCCESS
     */
    public Code listShelves(boolean showBooks) {
        if (showBooks) {
            for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
                int sum = 0;
                Shelf holder = shelfList.getValue();
                for (Map.Entry<Book, Integer> bookList : holder.getBooks().entrySet()) {
                    sum += bookList.getValue();
                }
                System.out.println(shelfList.getValue().listBooks());
            }
        } else {
            for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
                System.out.println(shelfList.getValue().toString());
            }
        }
        System.out.println();
        return Code.SUCCESS;
    }

    /**
     * addShelf(String shelfSubject)
     * Create a Shelf object and assign a shelf number of the size of the shelves HashMap plus one and a subject of
     * shelfSubject. Call addShelf(shelf) with that object.
     */
    public Code addShelf(String shelfSubject) {
        Shelf newShelf = new Shelf(shelves.size() + 1, shelfSubject);
        return addShelf(newShelf);
    }

    /**
     * addShelf(Shelf shelf)
     * If a shelf already exists in the shelves HashMap print out
     * "ERROR: Shelf already exists [shelf]"
     * And return a Code.SHELF_EXISTS_ERROR
     * <p>
     * Add the shelf to the HashMap of shelves and assign it the next shelf number.
     * The next shelf number will be the largest number in the HashMap plus one.
     * Add all the books with matching subjects to the new shelf.
     * This line is probably okay… I need to verify and update
     * Return a Code.SUCCESS
     */
    public Code addShelf(Shelf shelf) {
        for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
            if (shelfList.getValue().equals(shelf)) {
                System.out.println("ERROR: Shelf already exists" + shelf.toString());
                return Code.SHELF_EXISTS_ERROR;
            }
        }
        shelf.setShelfNumber(shelves.size() + 1);
        for (Map.Entry<Book, Integer> bookList : books.entrySet()) {
            Book holder = bookList.getKey();
            if (holder.getSubject().equals(shelf.getSubject())) {
                for (int i = 0; i < bookList.getValue(); ++i) {
                    shelf.addBook(holder);
                }
            }
        }
        shelves.put(shelf.getSubject(), shelf);
        return Code.SUCCESS;
    }

    /**
     * getShelf(Integer shelfNumber)
     * Return the Shelf from the HashMap shelves that matches the shelfNumber.
     * If no Shelf matches the number print out
     * "No shelf number [shelfNumber] found"
     * And return a null
     */
    public Shelf getShelf(Integer shelfNumber) {
        for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
            Shelf holder = shelfList.getValue();
            if (holder.getShelfNumber() == shelfNumber) {
                return holder;
            }
        }
        System.out.println("No shelf number " + shelfNumber + " found.");
        return null;
    }

    /**
     * getShelf(String subject)
     * Return the Shelf from the HashMap shelves that matches the subject.
     * If no Shelf matches the number print out
     * "No shelf for [subject] books"
     * And return a null
     */
    public Shelf getShelf(String subject) {
        for (Map.Entry<String, Shelf> shelfList : shelves.entrySet()) {
            if (shelfList.getKey().equals(subject)) {
                return shelfList.getValue();
            }
        }
        System.out.println("No shelf for " + subject + " books.");
        return null;
    }

    /**
     * listReaders()
     * Print out the toString of all the readers in the List of readers
     * Return the total number of readers
     */
    public int listReaders() {
        for (Reader readerList : readers) {
            System.out.println(readerList.toString());
        }
        return readers.size();
    }

    /**
     * listReaders(boolean showBooks)
     * If showBooks is true, display the reader and all their books in a display like:
     * <p>
     * Drew Clinkenbeard(#1)  has the following books:
     * [Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87, Headfirst Java by Grady Booch ISBN:1337]
     * Jennifer Clinkenbeard(#2)  has the following books:
     * [Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87]
     * Monte Ray(#3)  has the following books:
     * [Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87, Headfirst Java by Grady Booch ISBN:1337]
     * Laurence Fishburn(#4)  has the following books:
     * [Hitchhikers Guide To the Galaxy by Douglas Adams ISBN:42-w-87, Headfirst Java by Grady Booch ISBN:1337]
     * <p>
     * And return the total number of readers
     * <p>
     * If showBooks is false print out the toString of all the readers in the HashMap of readers
     * Return the total number of readers
     */
    public int listReaders(boolean showBooks) {
        if (showBooks) {
            for (Reader readerList : readers) {
                System.out.println(readerList.getName() + "(#" + readerList.getCardNumber() + ")  has the following books:");
                System.out.print("[");
                List<Book> holder = readerList.getBooks();
                for (int i = 0; i < holder.size(); i++) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(holder.get(i).getTitle() + " by " + holder.get(i).getAuthor() + " ISBN:" + holder.get(i).getIsbn());
                }
                System.out.println("]");
            }
        } else {
            for (Reader readerList : readers) {
                readerList.toString();
            }
        }
        System.out.println();
        return readers.size();
    }

    /**
     * getReaderByCard(int cardNumber)
     * Returns a reader whose library card number matches cardNumber
     * <p>
     * If no such reader exists print "Could not find a reader with card #[cardNumber]"
     * And return null
     */
    public Reader getReaderByCard(int cardNumber) {
        for (Reader readerList : readers) {
            if (readerList.getCardNumber() == cardNumber) {
                return readerList;
            }
        }
        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    /**
     * addReader(Reader reader)
     * Adds reader to the List of readers in the Library object. This method will require a new Code be
     * added to the Code object.
     * <p>
     * READER_ALREADY_EXISTS_ERROR(-47, "Reader already exists!")
     * <p>
     * If there is already a reader object that equals reader, print
     * "[reader name] already has an account!"
     * And return a Code.READER_ALREADY_EXISTS_ERROR
     * <p>
     * If there is another reader with the same card number, print
     * "[existing reader name] and [reader.name] have the same card number!"
     * And return a Code.READER_CARD_NUMBER_ERROR
     * <p>
     * If the reader is successfully added print
     * "[reader.name] added to the library!"
     * If the reader object's library card value is larger than the current field libraryCard in the Library
     * object, set the libraryCard field to that value.
     * Lastly return a Code.SUCCESS
     */
    public Code addReader(Reader reader) {
        for (Reader readerList : readers) {
            if (reader.equals(readerList)) {
                System.out.println(reader.getName() + " already has an account!");
                return Code.READER_ALREADY_EXISTS_ERROR;
            } else if (reader.getCardNumber() == readerList.getCardNumber()) {
                System.out.println(readerList.getName() + " and " + reader.getName() + " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }
        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");
        if (reader.getCardNumber() > libraryCard) {
            libraryCard = reader.getCardNumber();
        }
        return Code.SUCCESS;
    }

    /**
     * removeReader(Reader reader)
     * Removes a reader from the List of readers in the Library object. This method will require a new Code to be
     * added to the Code object.
     * <p>
     * READER_STILL_HAS_BOOKS_ERROR(-48, "Must return all books.")
     * <p>
     * If the reader exists in the List of readers but they have books in their inventory, print
     * "[reader name] must return all books!"
     * And return a Code.READER_STILL_HAS_BOOKS_ERROR;
     * <p>
     * If the reader is not in the List of Readers in the Library object print
     * "[reader]
     * is not part of this Library"
     * And return a Code.READER_NOT_IN_LIBRARY_ERROR
     * <p>
     * If the reader exists in the Library object and has no books in their inventory, remove them from the List of
     * readers and return a Code.Success
     */
    public Code removeReader(Reader reader) {
        boolean readerBool = false;
        if (reader.getBookCount() > 0) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }
        for (Reader readerList : readers) {
            if (readerList.equals(reader)) {
                readerBool = true;
                break;
            }
        }
        if (!readerBool) {
            System.out.println(reader.getName() + " is not part of this library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        readers.remove(reader);
        return Code.SUCCESS;
    }

    /**
     * convertInt(String recordCountString, Code code)
     * Returns the Integer form of the recordCountString.
     * <p>
     * If there is a NumberFormateException when converting recordCountString to an integer, use the table below
     * to determine which error message to display.  Code objects may be used in a switch statement.
     * <p>
     * If there is a NumberFormateException the following is printed
     * "Value which caused the error: [recordCountString]"
     * "Error message: [Code.message]"
     * Return the numerical code associated with the Code object.
     * Additionally, depending on the Code that was supplied as a parameter, different messages are printed.
     * See the table below for which message to print.
     * <p>
     * Code.BOOK_COUNT_ERROR
     * "Error: Could not read number of books"
     * Code.PAGE_COUNT_ERROR
     * "Error: could not parse page count"
     * Code.DATE_CONVERSION_ERROR
     * "Error: Could not parse date component"
     * Any other code
     * "Error: Unknown conversion error"
     * <p>
     * If there is not an error return the Integer from the conversion of recordCountString
     * <p>
     * For a bit of information on how this is used look at initBook.
     */
    public static int convertInt(String recordCountString, Code code) {
        try {
            return Integer.parseInt(recordCountString);
        } catch (NumberFormatException e) {
            System.out.println("Value which caused the error: " + recordCountString);
            switch (code) {
                case BOOK_COUNT_ERROR -> {
                    System.out.println("Error: Could not read number of books");
                    return Code.BOOK_COUNT_ERROR.getCode();
                }
                case PAGE_COUNT_ERROR -> {
                    System.out.println("Error: could not parse page count");
                    return Code.PAGE_COUNT_ERROR.getCode();
                }
                case DATE_CONVERSION_ERROR -> {
                    System.out.println("Error: Could not parse date component");
                    return Code.DATE_CONVERSION_ERROR.getCode();
                }
                default -> {
                    System.out.println("Error: Unknown conversion error");
                    return code.getCode();
                }
            }
        }
    }

    /**
     * convertDate(String date, Code errorCode)
     * Converts the date String to a LocalDate object. The date String is expected to be in the format:
     * "yyyy-mm-dd"
     * For example for February, 18 1982 the String would look like:
     * "1982-02-18"
     * <p>
     * If the date string is "0000" return a LocalDate object set to 01-Jan-1970
     * <p>
     * If the date String does not split into 3 elements on a "-" characters print out
     * "ERROR: date conversion error, could not parse [date]"
     * "Using default date (01-jan-1970)"
     * And return a LocalDate object set to 01-Jan-1970
     * <p>
     * If any of the converted values from the split String are less than 0 print out :
     * <p>
     * "Error converting date: Year [year] "
     * "Error converting date: Month [month]"
     * "Error converting date: Dat [day]"
     * "Using default date (01-jan-1970)"
     * <p>
     * Where [year], [month], and [day] are the converted values,
     * and return a LocalDate object set to 01-Jan-1970
     * <p>
     * If there are no errors return a LocalDate object set to the parsed date values.
     */
    public static LocalDate convertDate(String date, Code errorCode) {
        if (date.equals("0000")) {
            return LocalDate.of(1970, 1, 1);
        }
        String[] dateParts = date.split("-");
        if (dateParts.length != 3) {
            System.out.println("ERROR: date conversion error, could not parse " + date);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }
        int year, month, day;
        try {
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            day = Integer.parseInt(dateParts[2]);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: date conversion error, could not parse " + date);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }
        if (year < 0) {
            System.out.println("Error converting date: Year " + year);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }
        if (month < 1 || month > 12) {
            System.out.println("Error converting date: Month " + month);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }
        int maxDay = YearMonth.of(year, month).lengthOfMonth();
        if (day < 1 || day > maxDay) {
            System.out.println("Error converting date: Day " + day);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.of(1970, 1, 1);
        }
        return LocalDate.of(year, month, day);
    }


    /**
     * getLibraryCardNumber()
     * Returns the current value for libraryCard +1
     */
    public static int getLibraryCardNumber() {
        return libraryCard++;
    }

}
