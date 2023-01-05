package homework.homework_1.bg.tu_varna.sit.task1;

import java.util.*;

public class Application {

    private static Map<Integer, String> data = new HashMap<>();
    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            data.put(i, "Value of " + i);
        }

        System.out.println("result 1");
        System.out.println(getValue());

        System.out.println("result 2");
        System.out.println(getValues(4));

    }



    private static Map.Entry<Integer, Integer> getValue() {
        String result = getListWithValue(2).get(0);
        return getParsData(result);
    }
    private static Optional<Map.Entry<Integer, Integer>> getValue_v2() {
        Map<Integer, Integer> map = getValues(2);
        return map.entrySet().stream().findFirst();
    }
    private static Map<Integer, Integer> getValues(Integer d) {
        Map<Integer, Integer> map = new HashMap<>();

        List<String> result = getListWithValue(d);

        for (String str : result) {
            Map.Entry<Integer, Integer> res = getParsData(str);
            map.put(res.getKey(), res.getValue());
        }
        return map;
    }
    private static Map.Entry<Integer, Integer> getParsData(String data) {
        String[] result = data.split("Value of ");
        Integer number = Integer.getInteger(result[0]);
        return new AbstractMap.SimpleEntry<>(number, number*number);
    }

    private static List<String> getListWithValue(Integer d) {
        List<String> result = new ArrayList<>();

        for (Map.Entry<Integer, String> value : data.entrySet()) {
            if (value.getKey() % d == 0) {
                result.add(value.getValue());
            }
        }

        return result;
    }

}
