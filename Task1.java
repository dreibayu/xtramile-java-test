import java.util.*;

public class Task1 {

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1, 2, 1, 3);

        List<Integer> result = findUniqueNumbers(input);

        System.out.println(result);
    }

    public static List<Integer> findUniqueNumbers(List<Integer> numbers) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (Integer number : numbers) {
            if (frequencyMap.containsKey(number)) {
                frequencyMap.put(number, frequencyMap.get(number) + 1);
            } else {
                frequencyMap.put(number, 1);
            }
        }

        List<Integer> uniqueNumbers = new ArrayList<>();

        for (Integer number : numbers) {
            if (frequencyMap.get(number) == 1) {
                uniqueNumbers.add(number);
            }
        }

        return uniqueNumbers;
    }
}