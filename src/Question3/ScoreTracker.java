package Question3;

import java.util.ArrayList;
import java.util.Collections;

/*
Approach: The question asks us to keep track of scores and then calculate median score from the stream.
The use of arraylist is to be able to add as many scores as the user requires due to its dynamic size.
Since the questions asks to calculate the median, with each score that is added to the list, the list is arranged in ascending order for efficient median calculation
After the data stream is populated, the median formula is followed.
The middle index of the stream is calculated.
If the stream has odd number of data, then the middle data would be the median, which would be extracted using the middle index.
But with an even number of data, two middle numbers are taken and their average is calculated.
*/

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

/*
Example:
Stream: [85.5, 92.3, 77.8, 90.1] sorted to => [77.8, 85.5, 90.1, 92.3]
middleIndex = 4/2 = 2
So, middleMean = (85.5 + 90.1)/2 = 87.8
*/
