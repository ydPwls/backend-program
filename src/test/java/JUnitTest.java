import org.junit.jupiter.api.*;

public class JUnitTest {

    @DisplayName("1 + 2는 3이다")
    @Test
    public void junitTest1() {
        int a = 1;
        int b = 2;
        int sum = 3;

        int result = a + b;

        System.out.println("1 + 2는 3이다.");
        Assertions.assertEquals(sum, result);
    }

    @DisplayName("1 + 3은 4이다.")
    @Test
    public void junitTest2() {
        int a = 1;
        int b = 3;

        int result = a + b;

        System.out.println("1 + 3은 4이다.");
        Assertions.assertEquals(4, result);
    }

    @BeforeEach
    public void prepare() {
        System.out.println("준비");
    }

    @AfterEach
    public void cleanup() {
        System.out.println("설거지");
    }

    @BeforeAll
    public static void prepareAll() {
        System.out.println("최초 준비");
    }

    @AfterAll
    public static void cleanupAll() {
        System.out.println("최종 마무리");
    }
}