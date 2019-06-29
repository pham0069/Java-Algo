package design.design_pattern.behavioral;

import lombok.Getter;
import lombok.Setter;

/**
 * In this program, we have CricketData that contains:
 * - some statistics (runs, wickets, overs)
 * - 2 displays that will update whenever statistics are updated
 *
 * Problem:
 * - CricketData holds reference to concrete display objects even though it needs to call only the update method of
 * these objects. It has too much additional info than required.
 * - The statement "currentScoreDisplay.update(runs,wickets,overs);” violates one of the most important design principle
 * “Program to interfaces, not implementations.” as we are using concrete objects to share data rather than abstract interfaces.
 * - CricketData and display elements are tightly coupled
 * - If in future another requirement comes in and we need another display element to be added, we need to make changes
 * to the non-varying part of our code(CricketData). This is definitely not a good design practice and application might
 * not be able to handle changes and not easy to maintain.
 */
public class ObserverProblem {
    @Getter @Setter
    static class CricketData {
        int runs, wickets;
        float overs;
        CurrentScoreDisplay currentScoreDisplay;
        AverageScoreDisplay averageScoreDisplay;

        public CricketData(CurrentScoreDisplay currentScoreDisplay,
                           AverageScoreDisplay averageScoreDisplay) {
            this.currentScoreDisplay = currentScoreDisplay;
            this.averageScoreDisplay = averageScoreDisplay;
        }

        public void dataChanged(int runs, int wickets, float overs) {
            setRuns(runs);
            setWickets(wickets);
            setOvers(overs);

            currentScoreDisplay.update(runs,wickets,overs);
            averageScoreDisplay.update(runs,wickets,overs);
        }
    }

    // A class to display average score. Data of this class is updated by CricketData
    static class AverageScoreDisplay {
        private float runRate;
        private int predictedScore;

        public void update(int runs, int wickets, float overs) {
            this.runRate = (float)runs/overs;
            this.predictedScore = (int) (this.runRate * 50);
            display();
        }

        public void display() {
            System.out.println("\nAverage Score Display:\n" +
                    "Run Rate: " + runRate + "\nPredictedScore: " + predictedScore);
        }
    }

    // A class to display score. Data of this class is updated by CricketData
    static class CurrentScoreDisplay {
        private int runs, wickets;
        private float overs;

        public void update(int runs,int wickets,float overs) {
            this.runs = runs;
            this.wickets = wickets;
            this.overs = overs;
            display();
        }

        public void display() {
            System.out.println("\nCurrent Score Display: \n" +
                    "Runs: " + runs +"\nWickets:" + wickets + "\nOvers: " + overs );
        }
    }

    public static void main(String args[]) {
        AverageScoreDisplay averageScoreDisplay = new AverageScoreDisplay();
        CurrentScoreDisplay currentScoreDisplay = new CurrentScoreDisplay();
        CricketData cricketData = new CricketData(currentScoreDisplay, averageScoreDisplay);

        // In real app you would have some logic to call this function when data changes
        cricketData.dataChanged(90, 2, 10.2f);
    }
}
