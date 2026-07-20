package com.ll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {
    public static int run(String expression) {
        String[] tokens = expression.split(" ");

        List<String> list = new ArrayList<>(Arrays.asList(tokens));
        //     10 + 20
        return calculate(list);
    }

    //    0 + 20 + 30  // operand를 만나면 전항과 후항을 operand로 계산
    //    0[0] +[1] 20[2] +[3] 30[4]
    //    20[0] +[1] 30[2]

    public static int calculate(List<String> list) {
        int targetIdx = -1;
        int result = 0;
        int num1, num2;
        int parenCount = 0;
        boolean stop = false;

        if (list.size() == 1) {
            return Integer.parseInt(list.getFirst());
        }

        for (int i = 0; i < list.size(); i++) {
            if (stop) break;

            if (list.get(i).startsWith("-(")) {

                list.set(i, list.get(i).substring(1));
                return -calculate(list);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (stop) break;

            // (10 + 20) * 3
            // [0] [1] [2] [3] [4]
            if (list.get(i).startsWith("(")) {
                list.set(i, list.get(i).substring(1));
                targetIdx = i;  // 0
                int targetJdx = 0;
                parenCount++;
                for (int j = targetIdx; j < list.size(); j++) {
                    if (list.get(j).startsWith("(")) {
                        parenCount++;
                    } else if (list.get(j).endsWith(")")) { // target j = 4;
                        list.set(j, list.get(j).substring(0, list.get(j).length() - 1));
                        parenCount--;
                    }
                    if (parenCount == 0) {
                        targetJdx = j;
                        List<String> newList = new ArrayList<>();
                        for (int k = targetIdx; k < targetJdx + 1; k++) {
                            newList.add(list.get(k));
                        }
                        int newNumber = calculate(newList);
                        list.subList(targetIdx, targetJdx + 1).clear();
                        list.add(targetIdx, Integer.toString(newNumber));
                        break;
                    }
                }
                return calculate(list);
            }

        }

        for (int i = 0; i < list.size(); i++) {
            if (stop) break;

            if (list.get(i).equals("*")) {
                targetIdx = i;

                num1 = Integer.parseInt(list.get(i - 1));
                num2 = Integer.parseInt(list.get(i + 1));
                result = num1 * num2;
                list.set(i - 1, Integer.toString(result));
                stop = true;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (stop) break;


            if (list.get(i).equals("+")) {
                targetIdx = i;

                num1 = Integer.parseInt(list.get(i - 1));
                num2 = Integer.parseInt(list.get(i + 1));
                result = num1 + num2;
                list.set(i - 1, Integer.toString(result));
                stop = true;

                // 20 + 20 + 30
                // 20 + 30 (2개가 사라짐)

            } else if (list.get(i).equals("-")) {
                targetIdx = i;

                num1 = Integer.parseInt(list.get(i - 1));
                num2 = Integer.parseInt(list.get(i + 1));
                result = num1 - num2;
                list.set(i - 1, Integer.toString(result));
                stop = true;
            }
        }
        for (int i = targetIdx; i < list.size() - 2; i++) {
            list.set(i, list.get(i + 2));
            // 20 + 30 + 30
        }

        list.removeLast();
        list.removeLast();

        return calculate(list);
    }
}
