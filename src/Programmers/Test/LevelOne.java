package Programmers.Test;

import java.util.*;
import java.util.stream.Collectors;

public class LevelOne {
    //신규 아이디 추천
    public static String solution(String new_id) {
        String answer = "";

        //1단계 소문자 치환
        answer = new_id.toLowerCase();

        //2단계 숫자, 알파벳, -, ., _ 제외하고 제거
        answer = answer.replaceAll("[^0-9a-zA-Z\\_\\-\\.]","");

        //3단계 마침표가 2번 이상 연속된 부분을 하나의 마침표로 치환
        answer = answer.replaceAll("\\.+", ".");

        //4단계 마침표가 처음에 위치하는 경우 제거
        if (answer.startsWith(".") )
        {
            answer = answer.substring(1);
        }

        //5단계 아이디가 빈 문자열이라면 a를 대입
        if (answer.isEmpty())
        {
            answer = "a";
        }

        //6단계 아이디의 길이가 16자 이상이라면 첫 15개의 문자를 제외한 나머지 문자들을 제거
        if (answer.length() > 15)
        {
            answer = answer.substring(0, 15);
        }

        //4단계 마침표가 마지막에 위치하는 경우 제거
        if (answer.endsWith(".") )
        {
            answer = answer.substring(0, answer.length() - 1);
        }

        //7단계 아이디가 2자 이하라면 길이가 3이 될 때까지 마지막 문자를 반복해서 붙인다
        int strlength = answer.length();
        if (strlength <= 2)
        {
            String temp = answer.substring(strlength - 1, strlength);

            for (int i = strlength + 1; i <= 3; i++)
            {
                answer += temp;
            }
        }

        return answer;
    }

    //키패드 누르기
    public static String solution(int[] numbers, String hand) {
        String answer = "";

        HashMap<Integer, int[]> mapPoint = new HashMap<Integer, int[]>();
        Character defaultHand = hand.equals("left") ? 'L' : 'R';

        mapPoint.put(1, new int[]{0, 0});
        mapPoint.put(2, new int[]{0, 1});
        mapPoint.put(3, new int[]{0, 2});
        mapPoint.put(4, new int[]{1, 0});
        mapPoint.put(5, new int[]{1, 1});
        mapPoint.put(6, new int[]{1, 2});
        mapPoint.put(7, new int[]{2, 0});
        mapPoint.put(8, new int[]{2, 1});
        mapPoint.put(9, new int[]{2, 2});
        mapPoint.put(0, new int[]{3, 1});

        int[] numPoint = new int[2];
        int[] leftHandPoint = new int[]{3, 0};
        int[] rightHandPoint = new int[]{3, 2};

        double distanceLeftHand = 0;
        double distanceRightHand = 0;

        int iA = 0;
        int iB = 0;
        int iC = 0;
        int iD = 0;

        int iAtoC = 0;
        int iBtoD = 0;

        for (int i = 0; i < numbers.length; i++)
        {
            numPoint = mapPoint.get(numbers[i]);

            if(numPoint[1] == 0)
            {
                leftHandPoint = numPoint;
                answer += 'L';
            }
            else if(numPoint[1] == 2)
            {
                rightHandPoint = numPoint;
                answer += 'R';
            }
            else
            {
                iA = numPoint[0];
                iB = numPoint[1];
                iC = leftHandPoint[0];
                iD = leftHandPoint[1];

                iAtoC = (iC - iA) < 0 ? (iC - iA)  * - 1 : (iC - iA) ;
                iBtoD = (iD - iB) < 0 ? (iD - iB)  * - 1 : (iD - iB) ;

                distanceLeftHand = Math.abs(iAtoC) + Math.abs(iBtoD);
                iC = rightHandPoint[0];
                iD = rightHandPoint[1];

                iAtoC = (iC - iA) < 0 ? (iC - iA)  * - 1 : (iC - iA) ;
                iBtoD = (iD - iB) < 0 ? (iD - iB)  * - 1 : (iD - iB) ;

                distanceRightHand = Math.abs(iAtoC) + Math.abs(iBtoD);

                if (distanceLeftHand < distanceRightHand)
                {
                    leftHandPoint = numPoint;
                    answer += 'L';
                }
                else if (distanceLeftHand > distanceRightHand)
                {
                    rightHandPoint = numPoint;
                    answer += 'R';
                }
                else
                {
                    if (defaultHand.equals('L'))
                    {
                        leftHandPoint = numPoint;
                    }
                    else
                    {
                        rightHandPoint = numPoint;
                    }

                    answer += defaultHand;
                }
            }
        }

        return answer;
    }

    //크레인 인형 뽑기
    public static int solution(int[][] board, int[] moves) {
        int answer = 0;

        //바구니
        ArrayList<Integer> result = new ArrayList<Integer>();

        //크레인 작동 횟수만큼 루프
        for (int i = 0; i < moves.length; i++)
        {
            int move = moves[i] - 1;

            //board의 사이즈만큼 루프
            for (int j = 0; j < board.length; j++)
            {
                //현재 크레인의 위치에서 아이템 획득
                int item = board[j][move];

                if (item > 0) //획득한 아이템은 바구니에 담고 board에서 해당 값을 0으로 값 변경
                {
                    board[j][move] = 0;
                    result.add(item);
                    break;
                }
            }

            if (result.size() > 1) // 바구니에 인형이 2개 이상일때부터 삭제 로직 동작
            {
                int lastIndex = result.size() - 2; // 바구니에 있던 마지막 인형의 위치
                int newIndex = result.size() - 1; // 새로 바구니에 추가된 인형의 위치

                // 마지막 인형과 새로 추가된 인형이 같은지 비교 후 같으면 삭제
                if (result.get(lastIndex) == result.get(newIndex))
                {
                    result.remove(newIndex);
                    result.remove(lastIndex);

                    answer+=2;
                }

            }
        }

        return answer;
    }

    //없는 숫자 더하기
    public static int solution(int[] numbers) {
        int answer = 0;

        //없는 숫자를 찾기 편하게 int[]를 List<Integer>로 변환
        List<Integer> lstNumbers = Arrays.stream(numbers).boxed().collect(Collectors.toList());

        // 1 ~ 9를 List에서 찾을 루프문, 0은 어차피 더할 필요가 없으니 제외
        for (int i = 1; i < 10; i++)
        {
            // 만약 i에 해당하는 숫자가 List에 없을 경우 결과값에 더해줌
            if (!lstNumbers.contains(i))
            {
                answer += i;
            }
        }

        return answer;
    }

    //음양 더하기
    public static int solution(int[] absolutes, boolean[] signs) {
        int answer = 0;

        for (int i = 0; i < absolutes.length; i++)
        {
            int val = absolutes[i];

            if (!signs[i])
            {
                val *= -1;
            }

            answer += val;
        }

        return answer;
    }

    //내적
    public static int solution(int[] a, int[] b) {
        int answer = 0;

        for (int i = 0; i < a.length; i++) {
            answer += a[i] * b[i];
        }
        return answer;
    }

    //완주하지 못한 선수
    public static String solution(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> mapResult = new HashMap<>();

        //참여자를 HashMap에 추가, 중복되는 이름은 Value를 +1
        for (String name : participant) {
            mapResult.put(name, mapResult.getOrDefault(name, 0) + 1);
        }

        //완주자는 Value를 -1
        for (String name : completion) {
            mapResult.put(name, mapResult.get(name) - 1);
        }

        //HashMap에서 Value가 0이 아닌 사람은 완주하지 못한 사람.
        for (String name : mapResult.keySet()) {
            if (mapResult.get(name) != 0) {
                answer = name;
                break;
            }
        }

        return answer;
    }

    //폰켓몬
    public static int solutionPhoneketmon(int[] nums) {
        int answer = 0;

        //최대 가질 수 있는 수량
        int iCount = nums.length / 2;

        //nums의 중복 제거, 중복을 제거 했을 때 nums의 Count가 폰켓몬의 종류수
        List<Integer> lstNums = Arrays.stream(nums).boxed().collect(Collectors.toList());
        HashSet<Integer> hsNums = new HashSet<Integer>(lstNums);

        //폰켓몬의 종류가 최대 가질 수 있는 수량 보다 크면 최대 가질 수 있는 수량만 가지고 감
        if (hsNums.size() > iCount)
        {
            answer = iCount;
        }
        else
        {
            answer = hsNums.size();
        }

        return answer;
    }

    //K번째수
    public static int[] solution(int[] array, int[][] commands) {

        // 결과를 commands의 크기만큼 설정
        int[] answer = new int[commands.length];
        int count = 0;

        //commands의 크기만큼 루프 실행
        for (int index = 0; index < commands.length; index++) {
            //잘라내야할 위치와 찾아야 할 숫자의 위치를 설정
            int start = commands[index][0] - 1;
            int end = commands[index][1] - 1;
            int point = commands[index][2] - 1;

            int size = (end - start) + 1;
            int inCount = 0;

            //잘라낸 결과가 저장될 배열의 크기 설정
            int[] result = new int[size];

            //잘라내기 실행
            for (int i = start; i <= end; i++) {
                result[inCount++] = array[i];
            }

            //정렬
            Arrays.sort(result);

            //정렬 후 찾아야 할 숫자의 위치에서 가지고 온 값을 결과에 저장
            answer[count++] = result[point];
        }

        return answer;
    }

}
