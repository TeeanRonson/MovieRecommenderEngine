import movieRecommender.MovieRecommender;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Test file for MovieRecommender class.
 *  Note that this class provides the minimum tests.
 *  You are responsible for thoroughly testing project1 code on your own. */
public class MovieRecommenderTest {

    @Test
    public void testRecommend() {
        MovieRecommender recommender = new MovieRecommender();
        recommender.loadData("movies.csv","ratings.csv");
        System.out.println("Loaded data...");
        String filenameRecommendations = "test" + File.separator + "recommendations";
        recommender.findRecommendations(3, 5, filenameRecommendations);
        //System.out.println();
        //recommender.findAntiRecommendations(3, 5, "antiRecommendations");

        Path actual = Paths.get(filenameRecommendations);  // your output
        Path expected = Paths.get("test" + File.separator + "expectedRecommendations"); // instructor's

        // Compare your recommendations with expected recommendations
        int count = 0;
        try {
            count = TestUtils.checkFiles(expected, actual);
        } catch (IOException e) {
            Assert.fail(" File check failed: " + e.getMessage());
        }
        if (count <= 0)
            Assert.fail(" File check failed, files are different" );
    }

    @Test
    public void testAntiRecommend() {
        MovieRecommender recommender = new MovieRecommender();
        recommender.loadData("movies.csv","ratings.csv");
        System.out.println("Loaded data...");
        String filenameAntiRecommendations = "test" + File.separator + "antiRecommendations";
        recommender.findAntiRecommendations(3, 5, filenameAntiRecommendations);

        Path actual = Paths.get(filenameAntiRecommendations);  // your output
        Path expected = Paths.get("test" + File.separator + "expectedAntiRecommendations"); // instructor's

        // Compare your anti-recommendations with expected anti-recommendations
        int count = 0;
        try {
            count = TestUtils.checkFiles(expected, actual);
        } catch (IOException e) {
            Assert.fail(" File check failed: " + e.getMessage());
        }
        if (count <= 0)
            Assert.fail(" File check failed, files are different" );
    }


}

