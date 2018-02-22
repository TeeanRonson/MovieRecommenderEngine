package movieRecommender;


/**
 Class MovieRatingNode represents a node in the MovieRatingsList.
  */

public class MovieRatingNode {

    private int movieId; // id of the movie
    private double movieRating; // movie rating, ranges from 1 to 5
    private MovieRatingNode next;  // reference to the "next" MovieRatingNode

    /** A constructor for MovieRatingNode.
     * @param movieId id of the movie
     * @param rating  rating of the movie
     */
    public MovieRatingNode(int movieId, double rating) {
        this.movieId = movieId;
        next = null;

        if (rating < 0.5 || rating > 5) {
            System.out.println("Invalid rating: " + rating + "; Using a default value of 3.");
            movieRating = 3;
        }
        else
            movieRating = rating;
    }

    /**
     * Method that advances to the next node in the list.
     * @return next node
     */
    public MovieRatingNode next() {
        return next;
    }

    /** Method that sets the next node to the given node in the argument
     **
     * @param anotherNode given MovieRatingNode
     */
    public void setNext(MovieRatingNode anotherNode) {
        this.next = anotherNode;
    }

    /** Return the movie id stored in this node
     * @return movieId
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Returns the movie rating
     * @return movie rating
     */
    public double getMovieRating() {
        return movieRating;
    }

    /**
     * Method sets a new movie rating for the given node.
     * @param newRating
     */
    public void setMovieRating(double newRating) {
        movieRating = newRating;
    }

    /** Return a string with the movie id and rating
     * @return
     */
    public String toString() {
        return movieId + ", " + movieRating;
    }
}
