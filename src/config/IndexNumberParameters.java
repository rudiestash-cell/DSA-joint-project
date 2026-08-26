package config;

/**
 * Derives algorithm parameters from team member index numbers, as required
 * by Section 2(iii) of the project brief.
 *
 * Every module that needs a "magic number" (hash table size, route
 * penalty, DP budget, random seed) should call this class instead of
 * hardcoding a value — that's what makes it defensible in the oral exam.
 * Be ready to explain, live, how each number was derived from your team's
 * index numbers if an examiner asks.
 */
public final class IndexNumberParameters {

    private static final long[] TEAM_INDEX_NUMBERS = {
        22051653L,
        22376474L,
        22299011L,
        22407048L,
        22332153L,
        22271189L,
        22055166L,
        22372485L,
        22198084L,
        11022208L,
        22017281L,
        22383681L,
        22302466L,
        22380588L
    };

    private IndexNumberParameters() {
    }

    /** Sum of all digits across all team index numbers. */
    private static int digitSum() {
        int sum = 0;
        for (long index : TEAM_INDEX_NUMBERS) {
            long n = index;
            while (n > 0) {
                sum += (int) (n % 10);
                n /= 10;
            }
        }
        return sum;
    }

    /**
     * Parameter 1: Hash table initial size.
     * Derived as: (digit sum of all index numbers) * 3 + 11, forced odd,
     * to keep collision distribution reasonable.
     */
    public static int hashTableSize() {
        int base = digitSum() * 3 + 11;
        return base % 2 == 0 ? base + 1 : base;
    }

    /**
     * Parameter 2: Route penalty (km) added to roads flagged as
     * "poor condition" in roads.csv, used by Dijkstra weighting.
     * Derived as: last two digits of the first team member's index number,
     * scaled into a realistic km range (0.5 - 5.0 km).
     */
    public static double routePenaltyKm() {
        long lastTwo = TEAM_INDEX_NUMBERS[0] % 100;
        return 0.5 + (lastTwo % 10) * 0.5;
    }

    /**
     * Parameter 3: Random seed for reproducible benchmark data generation
     * and any randomised test fixtures.
     * Derived as: sum of all index numbers, truncated to 6 digits.
     */
    public static long randomSeed() {
        long sum = 0;
        for (long index : TEAM_INDEX_NUMBERS) sum += index;
        return sum % 1_000_000;
    }

    /**
     * Parameter 4 (bonus, strengthens the "3+" requirement):
     * DP knapsack budget (hours) for the optimisation engine.
     * Derived as: 20 + (digit sum mod 40).
     */
    public static int knapsackBudgetHours() {
        return 20 + (digitSum() % 40);
    }

    public static void main(String[] args) {
        System.out.println("Team size:             " + TEAM_INDEX_NUMBERS.length + " members");
        System.out.println("Digit sum:             " + digitSum());
        System.out.println("Hash table size:       " + hashTableSize());
        System.out.println("Route penalty (km):    " + routePenaltyKm());
        System.out.println("Random seed:           " + randomSeed());
        System.out.println("Knapsack budget (hrs): " + knapsackBudgetHours());
    }
}
