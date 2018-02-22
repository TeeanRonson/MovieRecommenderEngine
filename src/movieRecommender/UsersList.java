package movieRecommender;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * A custom linked list that stores user info. Each node in the list is of type
 * UserNode.
 */
public class UsersList {
    private UserNode head;
    private UserNode tail;


    /**
     * Stores two private data members
     * head is a dummy node
     * tail is the last node of the list
     */
    public UsersList() {
        this.head = new UserNode(-1);
        this.tail = head;
    }


    /** Insert the rating for the given userId and given movieId.
     * @param userId  id of the user
     * @param movieId id of the movie
     * @param rating  rating given by this user to this movie
     */
    public void insert(int userId, int movieId, double rating) {

        UserNode user = new UserNode(userId);

        if (head.next() == null) {
            head.setNext(user);
            user.insert(movieId, rating);
            tail = user;

        } else {

            UserNode current = head.next();

            while (current != null) {
                if (current.getId() == userId) {
                   current.insert(movieId, rating);
                   return;
                }
               current = current.next();
            }

            tail.setNext(user);
            user.insert(movieId, rating);
            tail = user;
        }

    }

    /**
     * Prints out the id's of each node stored in the list
     */
    public void writing() {

        UserNode current = head.next();
        while (current != null) {
            System.out.println(current.getId());
            current = current.next();
        }
    }

    /**
     * Appends a new node to the list
     * @param newNode a new node to append to the list
     */
    public void append(UserNode newNode) {

       tail.setNext(newNode);
       tail = newNode;

    }

    /** Returns a UserNode with the given userId
     * @param userId id of the user
     * @return UserNode for a given userId
     */
    public UserNode get(int userId) {

        UserNode current = this.head.next();

        while(current != null) {

            if (current.getId() == userId) {
                return current;
            }
            current = current.next();
        }
        return null;
    }

    /**
     * The method computes the similarity between the user with the given userid
     * and all the other users.
     * Finds the maximum similarity and returns the "most similar user".
     * @param userid id of the user
     * @return the node that corresponds to the most similar user
     */
    public UserNode findMostSimilarUser(int userid) {

        UserNode mostSimilarUser = null;
        UserNode user = this.get(userid);
        UserNode current = head.next();

        double topScore = 0;
        double currentScore = 0;

        while (current != null) {

            if (current.getId() == user.getId()) {

            } else {
                currentScore = user.computeSimilarity(current);

                if (currentScore > topScore) {
                    topScore = currentScore;
                    mostSimilarUser = current;
                }
            }
            current = current.next();
        }

        return mostSimilarUser;

    }

    /** Print UsersList to a file  with the given name in the following format:
     (userid) movieId:rating; movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating; movieId:rating; movieId:rating;
     Info for each userid should be printed on a separate line
     * @param filename name of the file where to output UsersList info
     */
    public void print(String filename) {

        Path outPath = Paths.get(filename);
        outPath.getParent().toFile().mkdirs();

        try(BufferedWriter out = Files.newBufferedWriter(outPath)) {
            UserNode current = this.head.next();

            while(current != null) {
                out.write("(" + current.getId() + ") ");
                for (MovieRatingNode x: current.getMovieRatings()) {
                    out.write(x.getMovieId() + ":" + x.getMovieRating() + "; ");
                }
                out.write("\n");
                current = current.next();
            }

        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }

    }
}