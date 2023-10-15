import java.util.Date;

public class App {
    
    public static void main(String[] args) throws Exception {

      

        

        
    }


    //1. Create a function that calculates days between two dates.
    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }


    //2. Create a function that takes an array of numbers and returns the second largest number.
    public static int secondLargest(int[] arr){
        int largest = arr[0];
        int secondLargest = arr[0];

        for(int i = 0; i < arr.length; i++){
            if(arr[i] > largest){
                secondLargest = largest;
                largest = arr[i];
            }
            else if(arr[i] > secondLargest){
                secondLargest = arr[i];
            }
        }
        return secondLargest;
    }

    


}
