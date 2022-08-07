package Programmers.Test;

import java.util.*;

public class LevelThree {

    //N으로 표현
    //최소 1 ~ 최대 8까지 반복한 결과 Set을 저장할 List
    static List<Set<Integer>> result = new ArrayList<>();

    //계산을 위한 Method
    //계산할때 순서에 따라 결과값이 달라지는  -, /는 순서를 뒤집어서 한번 더 계산
    // / 는 분모가 0일 경우는 계산하지 않음
    public static void calculationSet(Set<Integer> newSet, Set<Integer> prevSet, Set<Integer> currSet) {
        for (int prevNumber : prevSet) {
            for (int currNumber : currSet) {
                newSet.add(prevNumber + currNumber);
                newSet.add(prevNumber - currNumber);
                newSet.add(currNumber - prevNumber);
                newSet.add(prevNumber * currNumber);
                if (currNumber != 0) {
                    newSet.add(prevNumber / currNumber);
                }

                if (prevNumber != 0) {
                    newSet.add(currNumber / prevNumber);
                }
            }
        }

    }

    //최대 8번까지 반복하거나, 원하는 결과값이 나올때 까지 계산을 반복하는 재귀함수
    public static int bottomUp(int N, int index, List<Set<Integer>> result, int checkValue) {
        int anwser = -1;
        //8번까지 원하는 결과가 나오지 않을 경우 -1를 Return
        if (index > 8) {
            return anwser;
        }

        String temp = "";
        //현재 실행 순서의 계산값을 저장할 Set
        Set<Integer> newResult = new HashSet<>();
        //횟수만큼 숫자를 이어붙임
        for (int i = 0; i < index; i++) {
            temp += N;
        }

        //이어붙인 숫자와 결과가 같다면 현재 횟수를 Return
        if (Integer.parseInt(temp) == checkValue) {
            return index;
        }

        //이어붙인 숫자를 Set에 저장
        newResult.add(Integer.parseInt(temp));

        if (result.size() > 0) {
            if (result.size() == 1) {
                //반약 횟수가 2번이라면 N으로 사칙연산
                newResult.add(N + N);
                newResult.add(N - N);
                newResult.add(N * N);
                newResult.add(N / N);
            } else {
                for (int i = index - 1; i >= 0; i--) {
                    for (int j = 0; j < i; j++) {
                        if (i + j == index - 1) {
                            //가능한 연산의 수만큼 반복해서 Set에 결과를 저장
                            // Ex) 6 -> 1, 5, 2, 4, 3, 3의 Set의로 계산
                            calculationSet(newResult, result.get(j), result.get(i - 1));
                        }
                    }
                }
            }
        }

        //현재 계산 결과가 저장된 Set을 List에 저장
        result.add(newResult);

        //현재 Set에서 결과와 일치하는 값이 있는지 찾고 일치하는 값이 있으면 현재 Index를 Return
        for (int number : result.get(index - 1)) {
            if (number == checkValue) {
                anwser = index;
            }
        }

        //만약 현재 Index에 원하는 결과가 없다면 다음 Index로 동일 작업 수행
        if (anwser == -1) {
            anwser = bottomUp(N, ++index, result, checkValue);
        }

        return anwser;
    }

    public static int solution(int N, int number) {
        int answer = 0;
        int index = 1;

        // N을 1번 ~ 최대 8번까지 사용하여 사칙연산을 시작
        answer = bottomUp(N, index, result, number);

        return answer;
    }

    //정수 삼각형
    public static int solution(int[][] triangle) {
        //첫번째 ROW는 계산할 필요가 없으니 두번째 ROW부터 루프를 실행
        for (int i = 1; i < triangle.length; i++) {
            //제일 첫 COLUMN과 제일 마지막 COLUMN은 상위 ROW의 처음과 마지막 COLUMN만 더해준 후 해당 COLUMN에 값을 저장한다.
            triangle[i][0] = triangle[i - 1][0] + triangle[i][0];
            triangle[i][triangle[i].length - 1] = triangle[i - 1][triangle[i].length - 2] + triangle[i][triangle[i].length - 1];

            // 두번째 COLUMN부터 마지막 전 COLUMN까지 해당 COLUMN의 상위 ROW의 동일 위치의 COLUMN과 -1번째 COLUMN을 각각 더해서 더 큰 값을 해당 COLUMN에 저장한다.
            for (int j = 1; j < triangle[i].length - 1; j++) {
                triangle[i][j] = Integer.max(triangle[i - 1][j - 1] + triangle[i][j], triangle[i - 1][j] + triangle[i][j]);
            }
        }

        //마지막 ROW의 값 중 제일 큰 값을 찾아 반환한다.
        Integer[] result = Arrays.stream(triangle[triangle.length - 1]).boxed().toArray(Integer[]::new);
        Arrays.sort(result, Comparator.reverseOrder());

        return result[0];
    }

    //이중우선순위큐
    public static int[] solution(String[] operations) {
        int[] answer = new int[]{0, 0};

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < operations.length; i++) {
            //공백으로 String을 명령어와 값으로 나눈다.
            String[] array = operations[i].split(" ");
            int val = Integer.parseInt(array[1]);

            //명령어에 따른 처리
            switch (array[0]) {
                //명령어가 I이면 큐에 값을 저장
                case "I":
                    result.add(val);
                    break;
                //명령어가 D이면 큐에서 값을 삭제 (1은 최대값을, -1은 최소값을 삭제)
                case "D":
                    if (result.size() > 0) {
                        //중복될 경우는 하나만 지워야 하므로 해당 값의 Index를 찾아서 해당 인덱스를 이용하여 삭제
                        if (val == 1) {
                            result.remove(result.indexOf(result.stream().max(Integer::compare).orElse(-1)));
                        } else {
                            result.remove(result.indexOf(result.stream().min(Integer::compare).orElse(-1)));
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        //결과값이 있을 경우는 최대값과 최소값을 0, 1 Index에 저장
        if (result.size() > 0) {
            answer[0] = result.stream().max(Integer::compare).orElse(-1);
            answer[1] = result.stream().min(Integer::compare).orElse(-1);
        }

        return answer;
    }

}