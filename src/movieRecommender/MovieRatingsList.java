package movieRecommender;

import java.util.HashMap;
import java.util.Iterator;
import java.lang.Math;


/**
 * MovieRatingsList.
 * A class that stores movie ratings for a given user in a custom singly linked list of
 * MovieRatingNode objects.
 * Has various methods to manipulate the list.
 * Stores only the head of the list.
 * The list is sorted by rating (from highest to lowest).
 */
public class MovieRatingsList implements Iterable<MovieRatingNode> {

	private MovieRatingNode head;

	/**
	 * Private data member stores the lead of the custom linked list as a dummy node.
	 */
	public MovieRatingsList() {
		this.head = new MovieRatingNode(-1, 3);

	}

	/**
	 * Sets the rating for a given movie using its movie id.
	 * The position of the node within the list remains sorted by rating (from highest to lowest).
	 *
	 * @param movieId id of the movie
	 * @param newRating rating of this movie
	 */
	public void setRating(int movieId, double newRating) {

		MovieRatingNode previous = head;
		MovieRatingNode current = head.next();

		while (current != null && current.getMovieId() != movieId) {
			previous = previous.next();
			current = current.next();
		}

		if (current == null) {
			return;

		} else {

			previous.setNext(current.next());
			insertByRating(movieId, newRating);
		}
	}

    /**
     * Returns the rating for the given movie id
	 * If the movie is not in the list, returns -1.
     * @param movieId movie id
     * @return rating of a movie with this movie id
     */
	public double getRating(int movieId) {

		MovieRatingNode current = head.next();
		double result;

		while(current.getMovieId() != movieId) {
			current = current.next();
		}

		if (current == null) {
			result = -1;
		} else {
			result = current.getMovieRating();
		}

		return result;
	}


    /**
     * Insert a new MovieRatingNode (with a given movie id and rating) into the list.
     * Insert according to the value of the rating from highest to lowest.
     * @param movieId id of the movie
     * @param rating rating of the movie
     */
	public void insertByRating(int movieId, double rating) {

		MovieRatingNode newNode = new MovieRatingNode(movieId, rating);
		MovieRatingNode previous = this.head;
		MovieRatingNode current = previous.next();

		if (previous.next() == null) {
			previous.setNext(newNode);

		} else {

			while (current != null && rating < current.getMovieRating()) {
				previous = previous.next();
				current = current.next();
				if (current == null) {
					previous.setNext(newNode);
					return;
				}
			}

			while (current != null && newNode.getMovieRating() == current.getMovieRating()) {

				if (newNode.getMovieId() < current.getMovieId()) {
					current = current.next();
					previous = previous.next();

				} else {

					newNode.setNext(current);
					previous.setNext(newNode);
					return;
				}
			}

			newNode.setNext(current);
			previous.setNext(newNode);
		}
	}

    /**
     * Computes the similarity between two lists of ratings using Pearson's correlation coefficient.
     * @param otherList another MovieRatingList
     * @return similarity computed using Pearson's correlation coefficient
     */
    public double computeSimilarity(MovieRatingsList otherList) {

		MovieRatingNode currentY = otherList.head.next();
		MovieRatingNode currentX = this.head.next();
		HashMap<Integer, Double> userMap = new HashMap<>();

		int id = 0;
		int n = 0;
		double sumX = 0;
		double sumY = 0;
		double sumX2 = 0;
		double sumY2 = 0;
		double sumXY = 0;
		double numerator = 0;
		double denominator = 0;
		double denominatorTerm1 = 0;
		double denominatorTerm2 = 0;
		double similarity = 0;

		while (currentX != null) {
			userMap.put(currentX.getMovieId(), currentX.getMovieRating());
			currentX = currentX.next();
		}

		currentX = this.head.next();

		while (currentY != null && currentX != null) {
			id = currentY.getMovieId();
			if (userMap.containsKey(id)) {
				n++;
				sumX += userMap.get(id);
				sumY += currentY.getMovieRating();
				sumXY += (currentY.getMovieRating() * userMap.get(id));
				sumX2 += Math.pow(userMap.get(id), 2);
				sumY2 += Math.pow(currentY.getMovieRating(), 2);
			}
			currentY = currentY.next();
		}

		denominatorTerm1 = Math.sqrt((n*sumX2) - Math.pow(sumX, 2));
		denominatorTerm2 = Math.sqrt((n*sumY2) - Math.pow(sumY, 2));
		denominator = denominatorTerm1 * denominatorTerm2;
		numerator = (n*sumXY) - (sumX * sumY);

		similarity = numerator/denominator;

		return similarity;

    }

    /**
     * Returns a sublist of the list within the range: egRating to endRating, inclusive.
     * @param begRating lower bound for ratings in the resulting list
     * @param endRating upper bound for ratings in the resulting list
     * @return sublist of the MovieRatingsList that contains only nodes with
     * ratings in the given interval
     */
	public MovieRatingsList sublist(int begRating, int endRating) {

		MovieRatingsList result = new MovieRatingsList();
		MovieRatingNode previous = this.head;
		MovieRatingNode current = previous.next();

		if (current == null) {
			return null;

		} else {

			while (current != null && current.getMovieRating() > endRating) {
				current = current.next();
			}

			while (current != null && current.getMovieRating() <= endRating && current.getMovieRating() >= begRating) {
				result.insertByRating(current.getMovieId(), current.getMovieRating());
				current = current.next();
			}
		}
		return result;
	}

	/** Traverses the list and prints the list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public void print() {
		MovieRatingNode previous = this.head;
		MovieRatingNode current = previous.next();

		while (current != null) {
			System.out.println(current.getMovieId() + ":" + current.getMovieRating() + "; ");
			current = current.next();
		}
	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * @return the middle MovieRatingNode
	 */
	public MovieRatingNode getMiddleNode() {

		MovieRatingNode previous = this.head;
		MovieRatingNode currentFast = previous.next();
		MovieRatingNode currentSlow = previous.next();

		while (currentFast.next() != null && currentFast.next().next() != null) {
			currentFast = currentFast.next().next();
			currentSlow = currentSlow.next();
		}

		return currentSlow;

	}

    /**
     * Returns the median rating.
	 * Finds the middle node and return it's rating. If the
     * middle node is null, return -1.
     * @return rating stored in the node at the middle of the list
     */
	public double getMedianRating() {

		MovieRatingNode previous = this.head;
		MovieRatingNode currentFast = previous.next();
		MovieRatingNode currentSlow = previous.next();

		while (currentFast.next() != null && currentFast.next().next() != null) {
			currentFast = currentFast.next().next();
			currentSlow = currentSlow.next();
		}

		return currentSlow.getMovieRating();
	}

    /**
     * Returns a RatingsList that contains n best rated movies.
	 * These are the first n movies from the beginning of the list. If the list is
     * smaller than size n, it will return the whole list
     * @param n the maximum number of movies to return
     * @return MovieRatingsList
     */
	public MovieRatingsList getNBestRankedMovies(int n) {

		int count = 0;
		MovieRatingsList bestRanked = new MovieRatingsList();
		MovieRatingNode previous = this.head;
		MovieRatingNode current = previous.next();

		while (count < n && current != null) {
			bestRanked.insertByRating(current.getMovieId(), current.getMovieRating());
			current = current.next();
			count++;
		}

		return bestRanked;
	}

    /**
     * Returns a RatingsList that contains n worst rated movies for this user.
     * These are the last n movies from the end of the list.
     * @param n the maximum number of movies to return
     * @return MovieRatingsList
     */
	public MovieRatingsList getNWorstRankedMovies(int n) {

		MovieRatingsList result = new MovieRatingsList();
		MovieRatingNode previous = this.head;
		MovieRatingNode ahead = previous.next();
		MovieRatingNode behind = previous.next();
		int count = 0;

		while (ahead != null && count < n) {
			ahead = ahead.next();
			count++;
		}

		while (ahead != null) {
			ahead = ahead.next();
			behind = behind.next();
		}

		while (behind != null) {
			result.insertByRating(behind.getMovieId(), behind.getMovieRating());
			behind = behind.next();
		}

		return result;
	}

    /**
     * Returns a new list that is the reverse of the original list.
	 * The returned list is sorted by movie ratings from lowest to highest.
     * @param h head of the MovieRatingList to reverse
     * @return reversed list
     */
	public MovieRatingsList reverse(MovieRatingNode h) {

		MovieRatingNode dummy = new MovieRatingNode(-2, 3.0);
        MovieRatingsList r = new MovieRatingsList();
        MovieRatingNode previous = head;
        MovieRatingNode current = previous.next();
        MovieRatingNode next = current.next();

        if (current == null) {
            return null;
        }

        while (next != null) {
            current.setNext(previous);
            previous = current;
            current = next;
            next = next.next();
        }

        current.setNext(previous);
        dummy.setNext(current);

        r.head = dummy;
        h.setNext(null);

		return r;
	}

	/**
	 * Method that calls to the private iterator class
	 * Returns a new MovieRatingsListIterator
	 * @return
	 */
	public Iterator<MovieRatingNode> iterator() {

		return new MovieRatingsListIterator(0);
	}

	/**
	 * Inner class: MovieRatingsListIterator
	 * The iterator for the ratings list. Allows iterating over the MovieRatingNode-s of
	 * the list
	 */
	private class MovieRatingsListIterator implements Iterator<MovieRatingNode> {

		MovieRatingNode previous;
		MovieRatingNode current;

		/**
		 * Has two private data members
		 * @param index
		 */
		public MovieRatingsListIterator(int index) {

			previous = null;
			current = head;

			for (int i = 0; i < index; i++) {
				previous = current;
				current = current.next();
			}
		}

		/**
		 * Method checks if the current and next node exists
		 * Returns true if it exists, false otherwise
		 * @return
		 */
		@Override
		public boolean hasNext() {

			if (current != null && current.next() != null) {
				return true;
			}

			return false; // don't forget to change
		}

		/**
		 * Points the current node to the next node
		 * @return
		 */
		@Override
		public MovieRatingNode next() {

			if (hasNext() == false) {
				System.out.println("No next element");
				return null;
			}

			previous = current;
			current = current.next();
			return current;
		}
	}
}
