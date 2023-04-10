import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Drake Goldsmith
 * Date: 2/02/23
 * Description: A unit test for the Book.java POJO
 */

class BookTest {
    //    1337,Headfirst Java,education,1337,Grady Booch,0000
    private String testISBN = "1337";
    private String testTitle = "Headfirst Java";
    private String testSubject = "education";
    private int testPageCount = 1337;
    private String testAuthor = "Grady Booch";
    private LocalDate testDueDate = LocalDate.now();

    private Book testBook = null;

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
        testBook = new Book(testISBN, testTitle, testSubject, testPageCount, testAuthor, testDueDate);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Runs once AFTER each test.");
        testBook = null;
    }

    @Test
    void testConstructor() {
        Book testBook2 = null;
        assertNull(testBook2);
        testBook2 = testBook;
        assertNotNull(testBook2);
        assertEquals(testBook2, testBook);
    }

    @Test
    void testToString() {
        System.out.println("toString test.");
    }

    @Test
    void getIsbn() {
        System.out.println("getIsbn test.");
        assertEquals("1337", testBook.getIsbn());
        assertNotEquals("1330", testBook.getIsbn());
    }

    @Test
    void setIsbn() {
        System.out.println("setIsbn test.");
        assertEquals("1337", testBook.getIsbn());
        testBook.setIsbn("1327");
        assertNotEquals("1337", testBook.getIsbn());
        assertEquals("1327", testBook.getIsbn());
    }

    @Test
    void getTitle() {
        System.out.println("getTitle test.");
        assertEquals("Headfirst Java", testBook.getTitle());
        assertNotEquals("Headfirst Python", testBook.getTitle());
    }

    @Test
    void setTitle() {
        System.out.println("setTitle test.");
        assertEquals("Headfirst Java", testBook.getTitle());
        testBook.setTitle("Headfirst Python");
        assertNotEquals("Headfirst Java", testBook);
        assertEquals("Headfirst Python", testBook.getTitle());
    }

    @Test
    void getSubject() {
        System.out.println("getSubject test.");
        assertEquals("education", testBook.getSubject());
        assertNotEquals("entertainment", testBook.getSubject());
    }

    @Test
    void setSubject() {
        System.out.println("setSubject test.");
        assertEquals("education", testBook.getSubject());
        testBook.setSubject("entertainment");
        assertNotEquals("education", testBook);
        assertEquals("entertainment", testBook.getSubject());
    }

    @Test
    void getPageCount() {
        System.out.println("getPageCount test.");
        assertEquals(1337, testBook.getPageCount());
        assertNotEquals(1330, testBook.getPageCount());
    }

    @Test
    void setPageCount() {
        System.out.println("setPageCount test.");
        assertEquals(1337, testBook.getPageCount());
        testBook.setPageCount(1330);
        assertNotEquals(1337, testBook);
        assertEquals(1330, testBook.getPageCount());
    }

    @Test
    void getAuthor() {
        System.out.println("getAuthor test.");
        assertEquals("Grady Booch", testBook.getAuthor());
        assertNotEquals("Bob Feller", testBook.getAuthor());
    }

    @Test
    void setAuthor() {
        System.out.println("setAuthor test.");
        assertEquals("Grady Booch", testBook.getAuthor());
        testBook.setAuthor("Bob Feller");
        assertNotEquals("Grady Booch", testBook);
        assertEquals("Bob Feller", testBook.getAuthor());
    }

    @Test
    void getDueDate() {
        System.out.println("getDueDate test.");
        assertEquals(testDueDate, testBook.getDueDate());
        assertNotEquals(LocalDate.EPOCH, testBook.getDueDate());
    }

    @Test
    void setDueDate() {
        System.out.println("setDueDate test.");
        assertEquals(testDueDate, testBook.getDueDate());
        testBook.setDueDate(LocalDate.EPOCH);
        assertNotEquals(testDueDate, testBook);
        assertEquals(LocalDate.EPOCH, testBook.getDueDate());
    }

    @Test
    void testEquals() {
        System.out.println("testEquals test.");
        Book testBook2 = new Book("42-w-87", "Hitchhikers Guide To the Galaxy", "sci-fi", 42, "Douglas Adams", testDueDate);
        assertNotEquals(testBook2, testBook);
        Book testBook3 = new Book(testISBN, testTitle, testSubject, testPageCount, testAuthor, testDueDate);
        assertEquals(testBook3, testBook);
    }
}