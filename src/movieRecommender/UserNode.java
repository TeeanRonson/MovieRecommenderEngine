package movieRecommender;

import javax.swing.text.html.HTMLDocument;

/** UserNode. The class represents a node in the UsersList.
 *  Stores a userId, a list of ratings of type MovieRatingsList,
 *  and a reference to the "next" user in the list.
 */
public class UserNode {
    private int userId;
    private MovieRatingsList movieRatings;
    private UserNode nextUser;

    /** A constructor for the UserNode.
     * @param id User id
     */
    public UserNode(int id) {
        userId = id;
        movieRatings = new MovieRatingsList();
        nextUser = null;
    }

    /**
     * Getter for the next reference
     * @return the next node in the linked list of users
     */
    public UserNode next() {
        return nextUser;
    }

    /**
     * Setter for the next reference
     * @param anotherUserNode A user node
     */
    public void setNext(UserNode anotherUserNode) {
        this.nextUser = anotherUserNode;
    }

    /** Return a userId stored in this node */
    public int getId() {
        return userId;
    }

    /** Print info contained in this node:
     *  userId and a list of ratings.
     */
    public void print() {
        movieRatings.print();
    }

    /**
     * Returns the movie ratings list of MovieRatingsNode
     * @return
     */
    public MovieRatingsList getMovieRatings() {
        return movieRatings;
    }

    /**
     * Add rating info for a given movie to the MovieRatingsList
     * for this user node
     *
     * @param movieId id of the movie
     * @param rating  rating of the movie
     */
    public void insert(int movieId, double rating) {
        movieRatings.insertByRating(movieId, rating);
    }

    /**
     * Returns an array of user's favorite movies (up to n). These are the
     * movies that this user gave the rating of 5.
     *
     * @param n  the maximum number of movies to return
     * @return array containing movie ids of movies rated as 5 (by this user)
     */
    public int[] getFavoriteMovies(int n) {

        int[] result = new int[n];
        int count = 0;


        for (MovieRatingNode x: this.movieRatings) {
            if (x.getMovieRating() == 5.0 && count < n) {
                result[count] = x.getMovieId();
                count++;
            }
        }
        return result;
    }

    /**
     * Returns an array of movies the user likes the least (up to n). These
     * are the movies that this user gave the rating of 1.
     *
     * @param n the maximum number of movies to return
     * @return array of movie ids of movies rated as 1
     */
    public int[] getLeastFavoriteMovies(int n) {

        int[] result = new int[n];
        int count = 0;

        for (MovieRatingNode x: this.movieRatings.getNWorstRankedMovies(n)) {
            if (x.getMovieRating() == 1.0 && count < n) {
                result[count] = x.getMovieId();
                count++;
            }
        }
        return result;
    }

    /**
     * Computes the similarity of this user with the given "other" user using
     * Pearson correlation - simply calls computeSimilarity method
     * from MovieRatingsList
     *
     * @param otherUser a user to compare the current user with
     * @return similarity score
     */
    public double computeSimilarity(UserNode otherUser) {
        double result = this.movieRatings.computeSimilarity(otherUser.movieRatings);
        return result;
    }
}
