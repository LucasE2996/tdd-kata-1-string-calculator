import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_REGEX = "([,\n])"; // will select commas or new lines (\n)
    private static final int CUSTOM_REGEX_INDEX = 2;
    private String regex;
    private int counter;

    public int add(String numbers) {
        counter++;
        if (numbers.isEmpty())
            return 0;

        chooseRegex(numbers);
        if (!regex.equals(DEFAULT_REGEX))
            numbers = removeUselessCharacters(numbers);
        List<Integer> numList = convertToNumbers(numbers);
        checkForNegativeValues(numList);

        return numList.stream().reduce(0, Integer::sum);
    }

    public int callGetCalledCount() {
        return counter;
    }

    private String removeUselessCharacters(String numbers) {
        return numbers.substring(CUSTOM_REGEX_INDEX+2);
    }

    private void chooseRegex(String numbers) {
        if (numbers.contains("//"))
            regex = String.valueOf(numbers.charAt(CUSTOM_REGEX_INDEX));
        else
            regex = DEFAULT_REGEX;
    }

    private void checkForNegativeValues(List<Integer> numbers) {
        List<Integer> negativeValues = numbers.stream().filter(number -> number < 0).collect(Collectors.toList());
        if (!negativeValues.isEmpty())
            throw new NegativeNumberException(negativeValues);
    }

    private List<Integer> convertToNumbers(String numbers) {
        List<String> splitNumbers = Arrays.asList(numbers.split(regex));
        return splitNumbers.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    static class NegativeNumberException extends NullPointerException {
        public NegativeNumberException(List<Integer> negativeValues) {
            super("Negatives not allowed: " + negativeValues.toString());
            System.out.println("Negatives not allowed: " + negativeValues.toString());
        }
    }

}
