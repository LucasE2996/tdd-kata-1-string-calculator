import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class StringCalculatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    StringCalculator stringCalculator;

    @Before
    public void before() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void addEmptyString_returnZero() {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    public void addOneNumber_returnTheSameNumber() {
        assertEquals(1, stringCalculator.add("1"));
    }

    @Test
    public void addTwoNumbers_returnSum() {
        assertEquals(3, stringCalculator.add("1,2"));
    }

    @Test
    public void addNumbersSeparatedBySpacesAndComma_returnSum() {
        assertEquals(4, stringCalculator.add("1,2\n1"));
    }

    @Test
    public void setCustomDelimiter_returnSum() {
        String customDelimiter = ";";
        assertEquals(5, stringCalculator.add("//".concat(customDelimiter).concat("\n1;2;2")));
    }

    @Test(expected = StringCalculator.NegativeNumberException.class)
    public void addNegativeNumber_ThrowsException() {
        stringCalculator.add("-1");
    }

    @Test
    public void addMoreThenOneNegativeValue_ThrowsExceptionWithAllNegativeValues() {
        expectedException.expect(StringCalculator.NegativeNumberException.class);
        expectedException.expectMessage("Negatives not allowed: [-1, -2, -3]");

        stringCalculator.add("-1,-2,-3");
    }

    @Test
    public void callGetCalledCount_returnsHowManyTimesAddWereCalled() {
        // thread safe way of generating a random int
        final int times = ThreadLocalRandom.current().nextInt(2, 5 + 1);
        for (int i = times; i > 0; i--)
            stringCalculator.add("1");
        assertEquals(times, stringCalculator.callGetCalledCount());
    }
}
