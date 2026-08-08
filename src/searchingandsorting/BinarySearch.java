public class BinarySearch {
    public static int binarySearchById(ServiceRequest[] arr, String targetId){
        int low = 0;
        int high = arr.length - 1;

        while(low<=high){
            int mid = low +(high-low)/2;
            int comparison = arr[mid].getRequestId().compareTo(targetId);
            
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