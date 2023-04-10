import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Drake Goldsmith
 * Date: 2/12/23
 * Description: This is a test class to check the code for
 * the class Reader
 */

class ReaderTest {

    private final int testCardNumber = 2187;
    private final String testName = "Drew Clinkenbeard";
    private final String testPhone = "831-582-4007";
    private final static int testBookCount = 0;
    private List<Book> testBooks;

    private Reader testReader = null;
    private final Book testBook = new Book("1337", "Headfirst Java", "education", 1337, "Grady Booch", LocalDate.now());
    private final Book testBook2 = new Book("1227", "Headfirst Python", "education", 1000, "Tom Stacy", LocalDate.now());


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
        testReader = new Reader(testCardNumber, testName, testPhone);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Runs once AFTER each test.");
        testReader = null;
    }

    @Test
    void testConstructor() {
        Reader testReader2 = null;
        assertNull(testReader2);
        testReader2 = testReader;
        assertNotNull(testReader2);
        assertEquals(testReader2, testReader);
    }

    @Test
    void testToString() {
        Reader testReader2 = new Reader(2, "Carl", "345-234-1234");
        testReader2.addBook(testBook);
        testReader2.addBook(testBook2);
        System.out.println("toString test.");
        String test = testReader2.toString();
        System.out.println(test);
    }

    @Test
    void getCardNumber() {
        System.out.println("getCardNumber test.");
        assertEquals(2187, testReader.getCardNumber());
        assertNotEquals(3452, testReader.getCardNumber());
    }

    @Test
    void setCardNumber() {
        System.out.println("setCardNumber test.");
        assertEquals(2187, testReader.getCardNumber());
        testReader.setCardNumber(2100);
        assertNotEquals(2187, testReader.getCardNumber());
        assertEquals(2100, testReader.getCardNumber());
    }

    @Test
    void getName() {
        System.out.println("getName test.");
        assertEquals("Drew Clinkenbeard", testReader.getName());
        assertNotEquals("Craig Smith", testReader.getName());
    }

    @Test
    void setName() {
        System.out.println("setName test.");
        assertEquals("Drew Clinkenbeard", testReader.getName());
        testReader.setName("Craig Smith");
        assertNotEquals("Drew Clinkenbeard", testReader.getName());
        assertEquals("Craig Smith", testReader.getName());
    }

    @Test
    void getPhone() {
        System.out.println("getPhone test.");
        assertEquals("831-582-4007", testReader.getPhone());
        assertNotEquals("123-456-7890", testReader.getPhone());
    }

    @Test
    void setPhone() {
        System.out.println("setPhone test.");
        assertEquals("831-582-4007", testReader.getPhone());
        testReader.setPhone("123-456-7890");
        assertNotEquals("831-582-4007", testReader.getPhone());
        assertEquals("123-456-7890", testReader.getPhone());
    }

    @Test
    void getBooks() {
        System.out.println("getBooks test.");
        Reader testReader2 = testReader;
        assertEquals(testReader2.getBooks(), testReader.getBooks());
        Reader testReader3 = new Reader(234, "Bob Jones", "234-567-8900");
        testReader3.addBook(testBook);
        assertNotEquals(testReader3.getBooks(), testReader.getBooks());
    }

    @Test
    void setBooks() {
        System.out.println("setBooks test.");
        Reader testReader2 = testReader;
        assertEquals(testReader2.getBooks(), testReader.getBooks());
        Reader testReader3 = new Reader(234, "Bob Jones", "234-567-8900");
        testReader3.addBook(testBook);
        testReader.setBooks(testReader3.getBooks());
        assertEquals(testReader3.getBooks(), testReader.getBooks());
    }

    @Test
    void addBook() {
        assertEquals(testReader.addBook(testBook), Code.SUCCESS);
        assertNotEquals(testReader.addBook(testBook), Code.SUCCESS);
        assertEquals(testReader.addBook(testBook), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
    }

    @Test
    void getBookCountTest() {
        Reader testReader3 = new Reader(234, "Bob Jones", "234-567-8900");
        assertEquals(testReader3.getBookCount(), 0);
        testReader3.addBook(testBook);
        assertEquals(testReader3.getBookCount(), 1);
        testReader3.removeBook(testBook);
        assertEquals(testReader3.getBookCount(), 0);
    }

    @Test
    void removeBook() {
        assertEquals(testReader.removeBook(testBook), Code.READER_DOESNT_HAVE_BOOK_ERROR);
        testReader.addBook(testBook);
        assertEquals(testReader.removeBook(testBook), Code.SUCCESS);
    }

    @Test
    void hasBook() {
        assertFalse(testReader.hasBook(testBook));
        testReader.addBook(testBook);
        assertTrue(testReader.hasBook(testBook));
    }

    @Test
    void testEquals() {
        System.out.println("testEquals test.");
        Reader testReader2 = new Reader(234, "Bob Jones", "234-567-8900");
        assertNotEquals(testReader2, testReader);
        Reader testReader3 = new Reader(testCardNumber, testName, testPhone);
        assertEquals(testReader3, testReader);
    }
}