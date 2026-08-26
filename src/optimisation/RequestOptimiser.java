package optimisation;

import model.ServiceRequest;

/** Greedy and 0/1 knapsack use urgency as value and estimated service hours as weight. */
public final class RequestOptimiser  {

 private RequestOptimiser() {
    }
 public static String greedy(ServiceRequest[] a,int hours) {
        int used=0,value=0;
        for(ServiceRequest r:a) {
            int cost=6-r.getUrgency();
            if(used+cost<=hours) {
                used+=cost;
                value+=r.getUrgency();
            }
        }return "Greedy urgency-first value="+value+", hours="+used+"/"+hours;
    }
    public static String knapsack(ServiceRequest[] a,int hours) {
        return "DP optimal value="+knapsackValue(a, hours)+" within "+hours+" hours";
    }
    public static int knapsackValue(ServiceRequest[] a, int hours) {
        int[][] dp=new int[a.length+1][hours+1];
        for(int i=1;i<=a.length;i++) {
            int w=6-a[i-1].getUrgency(),v=a[i-1].getUrgency();
            for(int h=0;h<=hours;h++)dp[i][h]=w>h?dp[i-1][h]:Math.max(dp[i-1][h],dp[i-1][h-w]+v);
        }return dp[a.length][hours];
    }
    /** Exhaustive subset enumeration; intentionally limited to small n for the tractability comparison. */
    public static String bruteForce(ServiceRequest[] requests, int hours) {
        if (requests.length > 25) return "Brute force is limited to 25 requests";
        return "Brute-force optimal value=" + bruteForceValue(requests, hours) + " within " + hours + " hours";
    }
    public static int bruteForceValue(ServiceRequest[] requests, int hours) {
        if (requests.length > 25) throw new IllegalArgumentException("brute force supports at most 25 requests");
        return explore(requests, 0, hours);
    }
    private static int explore(ServiceRequest[] requests, int index, int hoursLeft) {
        if (index == requests.length) return 0;
        int skip = explore(requests, index + 1, hoursLeft);
        int cost = 6 - requests[index].getUrgency();
        if (cost > hoursLeft) return skip;
        int take = requests[index].getUrgency() + explore(requests, index + 1, hoursLeft - cost);
        return Math.max(skip, take);
    }
 public static String counterExample() {
        return "Counterexample: capacity 50; jobs (value, cost)=(60,10),(100,20),(120,30). Value-density greedy picks 60+100=160, while DP picks 100+120=220.";
    }
}
