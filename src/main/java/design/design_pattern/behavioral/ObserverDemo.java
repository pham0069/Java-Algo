package design.design_pattern.behavioral;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer pattern involves 2 entities
 * - observer: register, unregister with publisher on interested subjects
 * - publisher: notify list of observers on relevant update of subject
 * - subject: optional. Useful if a publisher could publish many types of subjects but observers are not interested in
 * all of them
 *
 * Adv:
 * - publisher only knows that observer must implement Observer interface. Nothing more
 * - No need to modify publisher to add or remove observers
 * - Can reuse publisher and observer classes independently of each other
 *
 * Disadv: memory leak
 *
 * When to use? When multiple objects are dependent on the state of one object
 * as it provides a neat and well tested design for the same.
 *
 */
public class ObserverDemo {
    interface Publisher {
        void registerObserver(Observer o);
        void unregisterObserver(Observer o);
        void notifyObservers();
    }

    interface Observer {
        void update(int runs, int wickets, float overs);
    }

    @Getter @Setter
    static class CricketData implements Publisher {
        int runs, wickets;
        float overs;
        List<Observer> observerList;

        public CricketData() {
            observerList = new ArrayList<>();
        }

        @Override
        public void registerObserver(Observer o) {
            observerList.add(o);
        }

        @Override
        public void unregisterObserver(Observer o) {
            observerList.remove(o);
        }

        @Override
        public void notifyObservers() {
            observerList.forEach(o -> o.update(runs, wickets, overs));
        }

        public void dataChanged(int runs, int wickets, float overs) {
            setRuns(runs);
            setWickets(wickets);
            setOvers(overs);

            notifyObservers();
        }
    }

    static class AverageScoreDisplay implements Observer {
        private float runRate;
        private int predictedScore;

        @Override
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

    static class CurrentScoreDisplay implements Observer {
        private int runs, wickets;
        private float overs;

        @Override
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
        CricketData cricketData = new CricketData();

        // register display elements
        cricketData.registerObserver(averageScoreDisplay);
        cricketData.registerObserver(currentScoreDisplay);

        cricketData.dataChanged(90, 2, 10.2f);

        //remove an observer
        cricketData.unregisterObserver(averageScoreDisplay);
        // now only currentScoreDisplay gets the notification
        cricketData.dataChanged(94, 3, 13f);
    }
}
