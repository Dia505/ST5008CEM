package Question3;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreTracker {
    // Arraylist has been used to construct a data stream of scores
    // Arraylist has dynamic size, so can fit as many scores as the user wants
    ArrayList<Double> Stream;

    ScoreTracker() {
        Stream = new ArrayList<>();
    }

    // Function to add scores into the data stream
    void addScore(double score) {
        Stream.add(score);
        // The scores are sorted in ascending order for calculation of median
        Collections.sort(Stream);
    }

    // Function to calculate median score
    double getMedianScore() {
        // The index in the middle of the stream
        int middleIndex = Stream.size()/2;

        // Check if the size of data stream is odd or even
        // If odd, the middle element is the median
        if(Stream.size()%2 == 1) {
            return Stream.get(middleIndex);
        }
        // If even, the two middle elements are taken, and their mean is the median of the total number of scores
        else {
            double middleMean = (Stream.get(middleIndex) + Stream.get(middleIndex-1))/2;
            return middleMean;
        }
    }

    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);

        double median = scoreTracker.getMedianScore();
        System.out.println("Median of scores: " + median);
    }
}
