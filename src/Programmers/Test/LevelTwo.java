package Programmers.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelTwo {
    //기능개발
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};
        int iComplete = 0;
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < progresses.length; i++) {
            //프로세스를 완성 시키기위한 최소 날짜 구하기
            int days = (int) Math.ceil((100 - (double)progresses[i]) / (double)speeds[i]);

            //최소날짜와 속도를 곱한값을 프로세스에 더해줌
            progresses[i] = progresses[i] + (speeds[i] * days);

            if (progresses[i] >= 100) {
                boolean bContinue = true;
                //프로세스가 100 이상이면 완료
                iComplete += 1;

                for (int j = i + 1; j <progresses.length; j++) {
                    //완료된 인덱스 이후 인덱스도 마찬가지로 프로세스를 진행시킴
                    progresses[j] = progresses [j] + (speeds[j] * days);

                    //만약 연속되게 완료된 프로세스가 있으면 완료처리하고 없으면 프로세스만 증가
                    if (progresses[j] >= 100 && bContinue) {
                        iComplete += 1;
                    }
                    else {
                        bContinue = false;
                    }
                }
            }
            //완료된 프로세스를 결과에 저장하고 완료된 이후의 프로세스들을 위 동작 다시 반복
            if (iComplete != 0) {
                result.add(iComplete);
                i = i + (iComplete - 1);
                iComplete = 0;
            }
        }

        answer = result.stream().mapToInt(i -> i).toArray();

        return answer;
    }

    //위장
    public int solution(String[][] clothes) {
        //최소 하나의 부위는 입어야 하므로 결과에서 모두 안입는 경우를 제외 하도록 시작값은 -1로 설정
        int answer = -1;
        int iCombination = 1;

        HashMap<String, Integer> hsClothes = new HashMap<>();

        //종류에 따라 구분하여 HashMap에 담는다
        //Key는 종류, Value는 수량
        //해당 종류를 안입는 경우도 있으니 Default값은 1
        for (int i = 0; i < clothes.length; i++) {
            hsClothes.put(clothes[i][1], hsClothes.getOrDefault(clothes[i][1], 1) +1);
        }

        //모든 종류의 경우의 수를 곱해준다
        for (String key : hsClothes.keySet()) {
            iCombination *= (hsClothes.get(key));
        }

        answer += iCombination;

        return answer;
    }

    //올바른 괄호
    public boolean solution(String s) {
        boolean answer = true;

        //'(' 와 ')'의 갯수를 구한다
        long countLeft = s.chars().filter(c -> c == '(').count();
        long countRight = s.chars().filter(c -> c == ')').count();
        //'(' 와 ')'의 갯수가 다르거나 시작값이 ')'면 잘못됐으므로 false를 반환
        if ((countLeft != countRight) || s.charAt(0) != '(') {
            return false;
        }
        else {
            countLeft = 0;
            countRight = 0;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (c == '(') {
                    countLeft += 1;
                }
                else {
                    countRight += 1;
                }

                //'(' 와 ')'의 갯수를 확인하면서 만약 ')'의 갯수가 더 커지면 잘못됐으므로 false를 반환
                if (countLeft < countRight) {
                    return false;
                }
            }
        }
        return answer;
    }

}
