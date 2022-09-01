package org.lalala.ticketsystem.run;

import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MerchantOperation {
    private static final Logger logger = LoggerFactory.getLogger(CustomerOperation.class);
    private static final Scanner SC = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void merchantMainPage(Map<Merchant, ArrayList<Movie>> allMovieList) {
        while (true) {
            System.out.println("Hello " + TicketSystem.loginUser.getUserName() + " please select your option");
            System.out.println("1. Show merchant information");
            System.out.println("2. Upload movies");
            System.out.println("3. Cancel movies");
            System.out.println("4. Movie show list");
            System.out.println("5. Movie setting");
            System.out.println("6. log out");
            int command = SC.nextInt();

            switch (command){
                case 1:
                    showInfo();
                    break;
                case 2:
                    uploadMovie(allMovieList);
                    break;
                case 3:
                    cancelMovie(allMovieList);
                    break;
                case 4:
                    movieShowList(allMovieList);
                    break;
                case 5:
                    movieSetting(allMovieList);
                    break;
                case 6:
                    System.out.println("Tank you for visiting, you are successfully log out");
                    return;
                default:
                    System.out.println("Error command, please renter");
            }
        }
    }

    private static void showInfo() {
        System.out.println("User information page");
        System.out.println("Your account number is " + TicketSystem.loginUser.getUserAcctNum());
        System.out.println("Your total balance is " + TicketSystem.loginUser.getAcctBalance());
        System.out.println("Your contact number is " + TicketSystem.loginUser.getPhone());
    }

    private static void uploadMovie(Map<Merchant, ArrayList<Movie>> allMovieList) {
        System.out.println("Merchant upload movies page");
        ArrayList<Movie> movies = Helper.getMovieListByUserID(allMovieList);
        Movie movie = new Movie();

        while (true) {
            System.out.println("Please enter movie name");
            String movieName = SC.next();
            if (Helper.checkMovieInList(movieName, movies) == true) {
                System.out.println("You already have this movie in your account.");
            } else {
                movie.setName(movieName);
                break;
            }
        }
        System.out.println("Please enter movie duration");
        movie.setTime(SC.nextDouble());
        System.out.println("Please set ticket number");
        movie.setRemainingTickets(SC.nextInt());
        System.out.println("Please enter movie starring");
        movie.setStarring(SC.next());
        System.out.println("Please enter movie price");
        movie.setMoviePrice(SC.nextDouble());

        while (true) {
            try {
                System.out.println("Please enter movie start time：");
                String time = SC.nextLine();
                movie.setStartTime(sdf.parse(time));
                movies.add(movie);
                System.out.println("You have upload movie：《" + movie.getName() + "》");
                return;
            } catch (ParseException e) {
                logger.error("something wrong with time", e);
            }
        }
    }

    private static void cancelMovie(Map<Merchant, ArrayList<Movie>> allMovieList) {
        System.out.println("Here is your movie List: ");
        movieShowList(allMovieList);
        ArrayList<Movie>movies = Helper.getMovieListByUserID(allMovieList);

        while (true) {
            System.out.println("Please enter the movie name you wan to cancel");
            String movieName = SC.next();
            if (Helper.checkMovieInList(movieName, movies) == false){
                System.out.println("The name you enter is not in your list, please check");
            } else {
                Movie movie = Helper.getMovieByName(movieName, allMovieList);
                movies.remove(movie);
                System.out.println("Movie is successfully canceled, here is your new movie list:");
                movieShowList(allMovieList);
                break;
            }
        }
    }

    private static void movieShowList(Map<Merchant, ArrayList<Movie>> allMovieList) {
        System.out.println(TicketSystem.loginUser.getUserName() + "\t\tphone：" + TicketSystem.loginUser.getPhone()
                + "\t\tbalance：" + TicketSystem.loginUser.getAcctBalance());
        List<Movie> movies = allMovieList.get(TicketSystem.loginUser);

        if(movies.size() > 0) {
            System.out.println("name\t\t\tactor\t\ttime\t\tscore\t\tprice\t\tremaining tickets\t\tstarting time");

            for (Movie movie : movies) {
                System.out.println(movie.getName()+"\t\t\t" + movie.getStarring()+ "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getMoviePrice() + "\t\t"
                        + movie.getRemainingTickets() + "\t\t" + sdf.format(movie.getStartTime()));
            }
        } else {
            System.out.println("There's no movie in your account");
        }
    }

    private static void movieSetting(Map<Merchant, ArrayList<Movie>> allMovieList) {
        System.out.println("Movie Setting Page");
        Merchant merchant = (Merchant) TicketSystem.loginUser;
        List<Movie> movies = allMovieList.get(merchant);

        if(movies.size() == 0) {
            System.out.println("There's no movie in your account");
            return;
        }
        System.out.println("Here is your Movie List ");
        movieShowList(allMovieList);

        while (true) {
            System.out.println("Please enter the name you want to set up：");
            String movieName = SC.nextLine();
            Movie movie = Helper.getMovieByName(movieName, allMovieList);

            if(movie != null){
                System.out.println("Please type the new name ：");
                String name  = SC.nextLine();
                System.out.println("Please enter the nrw starring");
                String starring  = SC.nextLine();
                System.out.println("Please enter the new duration：");
                String durationTime  = SC.nextLine();
                System.out.println("Please enter the new price：");
                String price  = SC.nextLine();
                System.out.println("please enter the new tickets number：");
                String totalNumber  = SC.nextLine();

                while (true) {
                    try {
                        System.out.println("please enter the new starting time：");
                        String time  = SC.nextLine();
                        movie.setName(name);
                        movie.setStarring(starring);
                        movie.setMoviePrice(Double.valueOf(price));
                        movie.setTime(Double.valueOf(time));
                        movie.setRemainingTickets(Integer.valueOf(totalNumber));
                        movie.setStartTime(sdf.parse(time));
                        System.out.println("You have successfully change the movie info");
                        movieShowList(allMovieList);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("something wrong with time");
                    }
                }
            } else {
                System.out.println("There's no such movie name in your list！");
                System.out.println("Do you want to retry? y/n");
                String command = SC.nextLine();

                switch (command) {
                    case "y":
                        break;
                    default:
                        System.out.println("Sure！");
                        return;
                }
            }
        }
    }
}
