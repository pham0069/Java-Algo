package algorithm.search;

/**
 * https://www.hackerrank.com/challenges/making-candies/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=search
 *
 * Karl loves playing games on social networking sites.
 * His current favorite is CandyMaker, where the goal is to make candies.
 *
 * Karl just started a level in which he must accumulate n candies starting with m machines and w workers.
 * In a single pass, he can make mxw candies.
 * After each pass, he can decide whether to spend some of his candies to buy more machines or hire more workers.
 * Buying a machine or hiring a worker costs p units,
 * and there is no limit to the number of machines he can own or workers he can employ.
 *
 * Karl wants to minimize the number of passes to obtain the required number of candies at the end of a day.
 * Determine that number of passes.
 *
 * For example, Karl starts with m=1 machine and w=2 workers.
 * The cost to purchase or hire, p=1 and he needs to accumulate 60 candies.
 * He executes the following strategy:
 *
 * 1. Make mxw=1x2=2 candies. Purchase two more machines (m = 1+2 = 3 in next pass)
 * 2. Make 3x2=6 candies. Purchase 3 machines and hire 3 workers. (m = 3+3 = 6, w = 2+3 = 5)
 * 3. Make 6x5=30 candies. Retain all 30 candies.
 * 4. Make 6x5=30 candies. With yesterday's production, Karl has 60 candies.
 * It took 4 passes to make enough candies.
 *
 * Constraints
 * 1 <= m, w, p, n <= 10^12
 *
 *
 * A proof that we must only buy-all or buy-none, buy-some is not optimal
 * At a certain pass, assume that current machine number and worker number is m and w,and m <= w
 * Denote r as number of remaining candies to make, if buy-one is better than buy-none means that
 * (r/(m * w))>=((r+p)/(m * w+w)), this equals to r/p >= m
 *
 * On this basis, prove buying i+1 is better than buying i is to prove that
 * (r + ip)/(mi * wi ) - (r+ip+p)/(mi * wi + wi) >= 0 where mi, wi is machine number and worker number after investing i times
 *
 * (r + ip)/(mi * wi ) - (r+ip+p)/(mi * wi + wi) >= 0 equals r/p >= mi - i,
 * because of r/p >= m, we only need to prove m >= mi - i,
 * since i is split into wi and mi, m + i >= mi is obvious.
 *
 * Straight passes:
 * For example, m = 1, w = 1, p = 100 and current number of candies c = 0 -> we can only make 1 candy per pass
 * In this case, we cannot afford to buy any new resources
 * We have no other way but keeping m=1 and w=1 for 100 passes to earn enough candies to buy resoures
 * These are called straight passes, i.e. # of passes before able to start to buy resources
 * = (p-c)/mw
 *
 *
 * Math.ceil(A/B) = Math.floor ((A-1)/B) + 1
 * A = Bk + q
 * - if q = 0
 * Math.ceil(A/B) = k
 * Math.floor((A-1)/B) = k-1
 * - if q > 0
 * Math.ceil(A/B) = k+1
 * Math.floor((A-1)/B) = k
 *
 * editorial
 * This problem can be solved by using a binary search.
 * Basically we binary search on the answer (number of passes)
 * and checking if the current round number is enough to make the required number of candies.
 * If we can do so, we try to minimize our answer by decreasing the number of passes else we increase the number of passes.
 *
 * Two important observations to make when solving this problem are:
 *
 * 1. Always buy machines and hire workers as early as possible.
 *
 * If we do so, then the subsequent operations will lead to a larger product everytime and hence more candies.
 *
 * 2. To maximize the number of candies made during each round, the numbers of workers and machines should be either
 * equal or as close to each other as possible. Hence, we must always invest in whichever resource we have less of.
 *
 * This is because let's say the current number of workers and machines are x and y respectively.
 * And we decide to spend a z number of candies for the current operation to increase x and y in some ratio.
 * Obviously, our aim is to maximize the product , where x' and y' are the new x and y after the increment respectively.
 * We know that their sum is z. Let's denote this sum by S.
 * So we have, x'+y'=S and we need to maximise x'y', which is maximum when x'=y'=S/2.
 * Hence, x' and y' should be as close as possible.
 *
 * We write a check function having the following parameters: m,w,p, n and mid.
 * This function returns a boolean value denoting whether or not it's possible to produce the required number of candies, n,
 * in less than or equal to mid - number of passes if we currently have w machines and m workers
 * where each new machine or worker costs p candies.
 *
 * If we can make candies with the current resources in the remaining number of passes,
 * then the function returns true; otherwise, we must try to make candies and buy new resources
 * (i.e., if there are more machines than workers, hire more workers or if both are equal increase both of them in equal proportion).
 * Compare the remaining number of passes with the number of passes needed to make candies for buying additional resources and,
 * if the remaining number is less, return false.
 */
public class MakingCandies {
    public static void main(String[] args) {
        System.out.println((minimumPasses2(5184889632L, 5184889632L, 20, 10000)));
    }
    static long minimumPasses(long m, long w, long p, long n) {
        long candies = 0;
        long invest = 0;
        long spend = Long.MAX_VALUE;

        while (candies < n) {
            // prevent overflow in calculating m*w
            long passes = (long) (((p - candies)/ (double) m)/w);

            // can invest on something
            if (passes <= 0) {
                // machines we can buy in total
                long mw = candies/p + m + w;
                long half = mw >>> 1;
                //optimise number of machines and workers
                if (m > w) {
                    m = Math.max(m, half);
                    w = mw - m;
                } else {
                    w = Math.max(w, half);
                    m = mw - w;
                }

                candies %=p;
                passes++;
            }

            //handling overflow
            try {
                long mw = Math.multiplyExact(m, w);
                long pmw = Math.multiplyExact(passes, mw);
                candies += pmw;
                invest += passes;
                // number of additional passes needed if we stop investing in next passes
                long increment = (long) Math.ceil((n-candies)/ (double) mw);
                spend = Math.min(spend, invest + increment);
            } catch (ArithmeticException e) {
                // if overflowing is encountered -> candies count are definitely more than long
                // thus it is more than n since n is long, so we've reached the goal and we can break the loop
                invest += 1;
                spend = Math.min(spend, invest +1);
                break;
            }

        }

        return Math.min(spend, invest);

    }

    //https://stackoverflow.com/questions/60341813/making-candies-solution-expression-explanation

    /**
     * Now, our approach should consist of the following steps:
     *
     * 1. Add candies for the current round to the total number of candies.
     * 2. Calculate how many more rounds are left if no more tools are purchased.
     * If the total number of rounds is less than the current minimum, then update the minimum.
     * Otherwise, stop the procedure.
     * 3. If the total number of candies is not enough to "invest",
     * play as many rounds as needed to collect enough (straight passes)
     * 4. Buy as many "units" as possible and adjust their number accordingly
     */
    static long minimumPasses2(long m, long w, long p, long n) {
        long currentProductionStorage = 0;
        long currentRounds = 0;
        long currentProductionSpeed = 0;
        long minRounds = Long.MAX_VALUE;

        while (true) {
            currentRounds++;

            try {
                currentProductionSpeed = Math.multiplyExact(m, w);
            } catch (ArithmeticException e) {
                // in case of overflow, we know we have reached goal (> long > n)
                minRounds = Math.min(minRounds, currentRounds);
                break;
            }

            currentProductionStorage += currentProductionSpeed;
            long roundsWithoutFurtherInvest = 0;
            if (n >= currentProductionStorage) {
                roundsWithoutFurtherInvest = (long) Math.ceil((n-currentProductionStorage)*1.0/currentProductionSpeed);
                // alternatively/equivalent to
                // roundsWithoutFurtherInvest = (n-currentProductionStorage+currentProductionSpeed-1)/currentProductionSpeed;
            }

            if (minRounds < currentRounds + roundsWithoutFurtherInvest) {
                break;
            }

            minRounds = currentRounds + roundsWithoutFurtherInvest;

            if (currentProductionStorage < p) {
                long roundsBeforeInvest = (p-currentProductionStorage+currentProductionSpeed-1)/currentProductionSpeed;
                if (roundsBeforeInvest < roundsWithoutFurtherInvest) {
                    currentProductionStorage += roundsBeforeInvest * currentProductionSpeed;
                    currentRounds += roundsBeforeInvest;
                } else {
                    break;
                }
            }

            long numOfPurchasedUnits = currentProductionStorage / p;
            currentProductionStorage %= p;

            long diff = Math.abs(w - m);
            long part1 = numOfPurchasedUnits;
            long part2 = 0;
            if (diff < numOfPurchasedUnits) {
                long remainingPurchasedUnits = numOfPurchasedUnits - diff;
                part1 = diff + (remainingPurchasedUnits / 2);
                part2 = (remainingPurchasedUnits / 2) + (remainingPurchasedUnits % 2);
            }

            if (m > w) {
                w += part1;
                m += part2;
            } else {
                m += part1;
                w += part2;
            }

        }

        return minRounds;

    }
}
