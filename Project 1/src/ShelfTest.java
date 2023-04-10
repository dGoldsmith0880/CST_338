import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Title: ShelfTest.java
 * @Author: Drake Goldsmith
 * @Since: 4 March 2023
 * Description: A class to test the methods for the class Shelf.java
 */

class ShelfTest {

    private final Book testBook = new Book("1337", "Headfirst Java", "education", 1337, "Grady Booch", LocalDate.now());
    private final Book testBook2 = new Book("1227", "Headfirst Python", "education", 1000, "Tom Stacy", LocalDate.now());
    private final Book testBook3 = new Book("2332", "Horror House", "horror", 123, "Carl Thompson", LocalDate.now());
    private final Book testBook4 = new Book("4323", "Night", "horror", 343, "Charles Switzerland", LocalDate.now());


    HashMap<Book, Integer> testMap = new HashMap<>();
    HashMap<Book, Integer> testMap2 = new HashMap<>();

    private Shelf testShelf = null;

    @BeforeAll
    static void mainSetUp() {
        System.out.println("main setup, runs once before all.");
    }

    @AfterAll
    static void mainTearDown() {
        System.out.println("main tear down runs once at the end.");
    }

    @BeforeEach
    void setUp() {
        System.out.println("runs before test.");
        testShelf = new Shelf(1, "education");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Runs once AFTER each test.");
        testShelf = null;
    }

    @Test
    void testConstructor() {
        Shelf testShelf2 = null;
        assertNull(testShelf2);
        testShelf2 = testShelf;
        assertNotNull(testShelf2);
        assertEquals(testShelf2, testShelf);
    }

    @Test
    void addBook() {
        System.out.println("addBook test");
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        assertEquals(1, testShelf.getBookCount(testBook));
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        assertEquals(2, testShelf.getBookCount(testBook));
        assertEquals(Code.SHELF_SUBJECT_MISMATCH_ERROR, testShelf.addBook(testBook3));
    }

    @Test
    void getBookCount() {
        System.out.println("getBookCount test.");
        Random random = new Random();
        int rand = random.nextInt(10);
        for (int i = 0; i < rand; i++) {
            testShelf.addBook(testBook);
        }
        assertEquals(rand, testShelf.getBookCount(testBook));
        testShelf.removeBook(testBook);
        assertEquals(rand - 1, testShelf.getBookCount(testBook));
        for (int i = 0; i < rand - 1; i++) {
            testShelf.removeBook(testBook);
        }
        assertEquals(0, testShelf.getBookCount(testBook));
    }

    @Test
    void getBooks() {
        System.out.println("getBooks test.");
        testMap.put(testBook, 1);
        testShelf.addBook(testBook);
        assertEquals(testMap, testShelf.getBooks());
        testMap.remove(testBook);
        assertNotEquals(testMap, testShelf.getBooks());
    }

    @Test
    void getShelfNumber() {
        System.out.println("getShelfNumber test.");
        assertEquals(1, testShelf.getShelfNumber());
        assertNotEquals(2, testShelf.getShelfNumber());
    }

    @Test
    void getSubject() {
        System.out.println("getSubject test.");
        assertEquals("education", testShelf.getSubject());
        assertNotEquals("sci-fi", testShelf.getSubject());
    }

    @Test
    void listBooks() {
        System.out.println("listBooks test.");
        Shelf testShelf3 = new Shelf(2, "horror");
        testShelf3.addBook(testBook3);
        System.out.println(testShelf3.listBooks());
        testShelf3.addBook(testBook4);
        testShelf3.addBook(testBook4);
        System.out.println(testShelf3.listBooks());
        testShelf3.removeBook(testBook3);
        System.out.println(testShelf3.listBooks());
        testShelf3.removeBook(testBook4);
        testShelf3.removeBook(testBook4);
        System.out.println(testShelf3.listBooks());
    }

    @Test
    void removeBook() {
        System.out.println("removeBook test");
        Shelf testShelf3 = new Shelf(2, "horror");
        assertEquals(Code.SUCCESS, testShelf3.addBook(testBook3));
        testShelf3.addBook(testBook4);
        assertEquals(1, testShelf3.getBookCount(testBook3));
        System.out.println(testShelf3.listBooks());
        assertEquals(Code.SUCCESS, testShelf3.removeBook(testBook3));
        assertEquals(0, testShelf3.getBookCount(testBook3));
        System.out.println(testShelf3.listBooks());
    }

    @Test
    void setBooks() {
        System.out.println("setBooks test.");
        assertEquals(testMap, testShelf.getBooks());
        testMap2.put(testBook, 1);
        testShelf.setBooks(testMap2);
        assertNotEquals(testMap, testShelf.getBooks());
        testMap.put(testBook, 1);
        assertEquals(testMap, testShelf.getBooks());
        testMap.remove(testBook);
        testMap2.remove(testBook);
    }

    @Test
    void setShelfNumber() {
        System.out.println("setShelfNumber test.");
        assertEquals(1, testShelf.getShelfNumber());
        testShelf.setShelfNumber(2);
        assertNotEquals(1, testShelf.getShelfNumber());
        assertEquals(2, testShelf.getShelfNumber());
    }

    @Test
    void setSubject() {
        System.out.println("setSubject test.");
        assertEquals("education", testShelf.getSubject());
        testShelf.setSubject("sci-fi");
        assertNotEquals("education", testShelf.getSubject());
        assertEquals("sci-fi", testShelf.getSubject());
    }

    @Test
    void testToString() {
        System.out.println("toString test.");
        Shelf testShelf3 = new Shelf(2, "horror");
        testShelf3.addBook(testBook);
        String test = testShelf3.toString();
        System.out.println(test);
        testShelf3.addBook(testBook2);
        test = testShelf3.toString();
        System.out.println(test);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShelfTest shelfTest = (ShelfTest) o;

        if (testBook != null ? !testBook.equals(shelfTest.testBook) : shelfTest.testBook != null) return false;
        if (testBook2 != null ? !testBook2.equals(shelfTest.testBook2) : shelfTest.testBook2 != null) return false;
        if (testBook2 != null ? !testBook2.equals(shelfTest.testBook2) : shelfTest.testBook2 != null) return false;
        return Objects.equals(testShelf, shelfTest.testShelf);
    }

    @Override
    public int hashCode() {
        int result = testBook != null ? testBook.hashCode() : 0;
        result = 31 * result + (testBook2 != null ? testBook2.hashCode() : 0);
        result = 31 * result + (testBook2 != null ? testBook2.hashCode() : 0);
        result = 31 * result + (testShelf != null ? testShelf.hashCode() : 0);
        return result;
    }
}