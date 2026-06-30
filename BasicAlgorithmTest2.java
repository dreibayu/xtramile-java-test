import java.util.*;

public class BasicAlgorithmTest2 {

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(5, 9, 7, 11);

        int result = findLargestPairSum(input);

        System.out.println(result);
    }

    public static int findLargestPairSum(List<Integer> numbers) {
        int firstMax = 0;
        int secondMax = 0;

        for (Integer number : numbers) {
            if (number >= firstMax) {
                secondMax = firstMax;
                firstMax = number;
            } else if (number > secondMax) {
                secondMax = number;
            }
        }

        return firstMax + secondMax;
    }
}