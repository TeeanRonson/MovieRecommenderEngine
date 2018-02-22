package movieRecommender;

/** A driver class for the MovieRecommender. In the main method, we
 * create a movie recommender, load movie data from files and compute
 * recommendations and anti-recommendations for a particular user.
 */
public class Driver {
    public static void main(String[] args) {

        MovieRecommender recommender = new MovieRecommender();
        UserNode un = new UserNode(1);
//        UserNode u1 = new UserNode(2);
//        UsersList ul = new UsersList();


//        ul.insert(1, 1, 3.5);
//        ul.insert(1, 2, 4.0);
//        ul.insert(2, 1, 3.0);
//        ul.insert(2, 2, 5.0);
//        ul.insert(3, 1, 1.0);
//        ul.insert(3, 2, 4.5);

//        ul.insert(3, 1, 5.0);

//        ul.append(un);
//        ul.append(u1);

//        ul.findMostSimilarUser(1);
//        System.out.println(ul.findMostSimilarUser(1));

        un.insert(1, 1.0);
        un.insert(6, 1.0);
        un.insert(3, 5.0);
        un.insert(4, 5.0);
        un.print();

        int[] array = un.getFavoriteMovies(2);
        int[] array1 = un.getLeastFavoriteMovies(2);

        for (int i = 0; i < array1.length; i ++) {
            System.out.println("end: " + array1[i]);
        }

        for (int i = 0; i < array.length; i++) {
            System.out.println("Here: " + array[i]);
        }
//          un.getLeastFavoriteMovies(2);

        // movies.csv and ratings.csv should be in the project folder

//        recommender.loadData("movies.csv","ratings.csv");

//        recommender.findRecommendations(3, 15, "recommendations");
//        System.out.println();
//        recommender.findAntiRecommendations(3, 15, "antiRecommendations");

    }
}
