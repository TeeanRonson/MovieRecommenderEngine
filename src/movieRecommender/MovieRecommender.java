package movieRecommender;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/** MovieRecommender. A class that is responsible for:
    - Reading movie and ratings data from the input files and loading it into several data structures.
 *  - Computing movie recommendations for a given user and printing them to a file.
 *  - Computing movie "anti-recommendations" for a given user and printing them to file.
 */


public class MovieRecommender {
    private UsersList usersData;
    private HashMap<Integer, String> movieMap;

    /**
     * Stores two private data members
     * movieMap is a HashMap that will map movie id's to its titles
     * usersData is a custom linked list that stores user id's and the user's movie ratings information
     */
    public MovieRecommender() {
         movieMap = new HashMap<>();
         usersData = new UsersList();
    }

    /**
     * Reads user ratings from the file and calls several private methods
     * @param movieFilename name of the file with movie info
     * @param ratingsFilename name of the file with ratings info
     */
    public void loadData(String movieFilename, String ratingsFilename) {

        loadMovies(movieFilename);
        loadRatings(ratingsFilename);
    }

    /** Private helper method that loads information about movie ids and titles from the given file.
     *  Stores the information in a hashmap that maps each movie id to a movie title
     * @param movieFilename csv file that contains movie information.
     *
     */
    private void loadMovies(String movieFilename) {

        try (BufferedReader reader = new BufferedReader(new FileReader(movieFilename))) {

            int docLines = 0;
            int movieId = 0;
            String currentLine;
            String id = "";
            String title = "";

            while ((currentLine = reader.readLine()) != null) {
                docLines++;

                if (docLines > 1) {
                    if (currentLine.contains("\"")) {
                        String[] movTitle = currentLine.split("\"");
                        String[] movNum = currentLine.split(",");
                        title = movTitle[1];
                        movieId = Integer.parseInt(movNum[0]);

                    } else {
                        String[] info = currentLine.split(",");
                        id = info[0];
                        title = info[1];
                        movieId = Integer.parseInt(id);
                    }
                    movieMap.put(movieId, title);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Input Unsuccessful");
        }
    }

    /**
     * Private helper method that load's user movie ratings for each movie from the file
     * and stores it into the UsersList
     * @param ratingsFilename name of the file that contains ratings
     */
    private void loadRatings(String ratingsFilename) {

        try (BufferedReader reader = new BufferedReader(new FileReader(ratingsFilename))) {

            int docLines = 0;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                docLines++;

                if (docLines > 1) {

                    String[] info = currentLine.split(",");
                    String userId = info[0];
                    String movieId = info[1];
                    String movieRating = info[2];

                    int user = Integer.parseInt(userId);
                    int movid = Integer.parseInt(movieId);
                    double rate = Double.parseDouble(movieRating);

                    usersData.insert(user, movid, rate);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File Input Unsuccessful");
        }
    }

    /**
     * Computes up to 'num' movie recommendations for the user with the given user
     * id and prints these movie titles to the given file. First calls
     * findMostSimilarUser and then getFavoriteMovies(num) method on the
     * "most similar user" to get up to 'num' recommendations.
     * Prints movies that the user with the given userId has not seen yet.
     * @param userid id of the user
     * @param num max number of recommendations
     * @param filename name of the file to output recommended movie titles
     */
    public void findRecommendations(int userid, int num, String filename) {

        Path outPath = Paths.get(filename);
        outPath.getParent().toFile().mkdirs();

        UserNode mostSimilar = usersData.findMostSimilarUser(userid);
        UserNode user = usersData.get(userid);
        int[] favourite = mostSimilar.getFavoriteMovies(num);
        boolean seenMovie = false;

        try(BufferedWriter out = Files.newBufferedWriter(outPath)) {

            for (int i = 0; i < favourite.length; i++) {
                int movieId = favourite[i];
                for (MovieRatingNode x: user.getMovieRatings()) {
                    if (x.getMovieId() == movieId) {
                        seenMovie = true;
                        break;
                    }
                }
                if (seenMovie == false) {
                    String movie = movieMap.get(movieId);
                    out.write(movie + "\n");
                }
            }

        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     * Computes up to 'num' movie anti-recommendations for the user with the given
     * user id and prints these movie titles to the given file. These are the
     * movies the user should avoid. First calls findMostSimilarUser and then
     * getLeastFavoriteMovies(num) method on the "most similar user" to get up
     * to num movies the most similar user strongly disliked. Prints only
     * those movies to the file that the user with the given userid has not seen yet.
     * @param userid id of the user
     * @param num max number of anti-recommendations
     * @param filename name of the file where to output anti-recommendations (movie titles)
     */
    public void findAntiRecommendations(int userid, int num, String filename) {

        Path outPath = Paths.get(filename);
        outPath.getParent().toFile().mkdirs();

        UserNode mostSimilar = usersData.findMostSimilarUser(userid);
        UserNode user = usersData.get(userid);
        int[] anti = mostSimilar.getLeastFavoriteMovies(num);
        boolean seenMovie = false;

        try(BufferedWriter out = Files.newBufferedWriter(outPath)) {

            for (int i = 0; i < anti.length; i++) {
                int movieId = anti[i];
                for (MovieRatingNode x: user.getMovieRatings()) {
                    if (x.getMovieId() == movieId) {
                        seenMovie = true;
                        break;
                    }
                }
                if (seenMovie == false) {
                    String movie = movieMap.get(movieId);
                    if (movie != null) {
                        out.write(movie + "\n");
                    }
                }
            }

        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
