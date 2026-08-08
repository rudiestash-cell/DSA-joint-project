import java.util.Comparator;

public class BinarySearch {
    public static int binarySearch(ServiceRequest[] arr, ServiceRequest target, Comparator<ServiceRequest> comparator){
        int low = 0;
        int high = arr.length - 1;

        while(low<=high){
            int mid = low +(high-low)/2;
            int comparison = comparator.compare(arr[mid], target);
            
            if(comparison == 0){
                return mid;
            }
            else if (comparison < 0){
                low = mid + 1;
            }
            else{
                high = mid - 1;
            }
        }
        return -1;
    }
}