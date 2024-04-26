import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Integer> sumForBricks = new ArrayList<>();
        while (true){
            int brickLength = sc.nextInt();
            if(brickLength == 0)
                break;
            sc.nextLine();
            int cutPlacesCount = sc.nextInt();
            sc.nextLine();
            String cutPlacesStr = sc.nextLine();
            String [] cutPlaces = cutPlacesStr.split(" ");
            boolean successfulParceToInt = true;
            List<Integer> cutPlacesInt = new ArrayList<>();
            for(String cut: Arrays.asList(cutPlaces)){
                try{
                    cutPlacesInt.add(Integer.parseInt(cut));
                }catch(NumberFormatException e){
                    System.out.println("Error: please enter numerals");
                    successfulParceToInt = false;
                    break;
                }
            }
            if(successfulParceToInt){
                sumForBricks.add(findNewCut(brickLength, cutPlacesCount, cutPlacesInt, 0, brickLength));
            }
        }
        for(int sumForCurrBrick: sumForBricks){
            System.out.println("The minimum cutting price is " + sumForCurrBrick);
        }

    }

    public static int findNewCut(int brickLength, int cutPlacesCount, List<Integer> cutPlacesInt, int brickStart, int brickEnd){
        //System.out.println(Arrays.toString(cutPlaces));
        int summBrickLength = 0;
        if(cutPlacesCount % 2 == 0){
            int firstCut = cutPlacesInt.get(0);
            int lastCut = cutPlacesInt.get(cutPlacesCount - 1);
            if((firstCut - brickStart) > (brickEnd - lastCut)){
                cutPlacesInt.remove(0);
                summBrickLength = findNewCut(brickEnd - firstCut, cutPlacesCount-1, cutPlacesInt, firstCut, brickEnd);
            }else{
                cutPlacesInt.remove(cutPlacesCount - 1);
                summBrickLength = findNewCut(lastCut - brickStart, cutPlacesCount-1, cutPlacesInt, brickStart, lastCut);
            }
        }else{
            if(cutPlacesCount == 1){
                return brickLength;
            }else{
                int middlePlace = (brickStart + brickEnd) / 2;
                int previousDistanceToMiddle = middlePlace;
                for(int i = 0; i < cutPlacesCount; i++){
                    int currDistanceToMiddle = Math.abs(cutPlacesInt.get(i) - middlePlace);
                    if( currDistanceToMiddle > previousDistanceToMiddle){
                        int cut = cutPlacesInt.get(i - 1);
                        summBrickLength = findNewCut(cut - brickStart, i - 1, cutPlacesInt.subList(0, i - 1), brickStart, cut);
                        summBrickLength += findNewCut(brickEnd - cut, cutPlacesCount - i, cutPlacesInt.subList(i - 1, cutPlacesCount - 1), cut, brickEnd);
                        break;
                    }else{
                        previousDistanceToMiddle = currDistanceToMiddle;
                    }
                }
            }
        }
        summBrickLength += brickLength;
        return summBrickLength;
    }
}