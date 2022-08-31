package org.lalala.ticketsystem.run;

import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MerchantOperation {
    public static void merchantMainPage() {
        while (true) {
            System.out.println("Hello " + TicketSystem.loginUser.getUserName() + " please select your option");
            System.out.println("1. Show merchant information");
            System.out.println("2. Upload movies");
            System.out.println("3. Cancel movies");
            System.out.println("4. Movie show list");
            System.out.println("5. Movie setting");
            System.out.println("6. log out");
            int command = TicketSystem.SC.nextInt();

            switch (command){
                case 1:
                    showInfo();
                    break;
                case 2:
                    uploadMovie();
                    break;
                case 3:
                    cancelMovie();
                    break;
                case 4:
                    movieShowList();
                    break;
                case 5:
                    movieSetting();
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

    private static void uploadMovie() {
        System.out.println("Merchant upload movies page");
        ArrayList<Movie> movies = Helper.getMovieListByUserID();
        Movie movie = new Movie();

        while (true) {
            System.out.println("Please enter movie name");
            String movieName = TicketSystem.SC.next();
            if (Helper.checkMovieInList(movieName, movies) == true) {
                System.out.println("You already have this movie in your account.");
            } else {
                movie.setName(movieName);
                break;
            }
        }
        System.out.println("Please enter movie duration");
        movie.setTime(TicketSystem.SC.nextDouble());
        System.out.println("Please set ticket number");
        movie.setRemainingTickets(TicketSystem.SC.nextInt());
        System.out.println("Please enter movie starring");
        movie.setStarring(TicketSystem.SC.next());
        System.out.println("Please enter movie price");
        movie.setMoviePrice(TicketSystem.SC.nextDouble());

        while (true) {
            try {
                System.out.println("Please enter movie start time：");
                String time = TicketSystem.SC.nextLine();
                movie.setStartTime(TicketSystem.sdf.parse(time));
                movies.add(movie);
                System.out.println("You have upload movie：《" + movie.getName() + "》");
                return;
            } catch (ParseException e) {
                e.printStackTrace();
                TicketSystem.logger.error("something wrong with time");
            }
        }
    }

    private static void cancelMovie() {
        System.out.println("Here is your movie List: ");
        movieShowList();
        ArrayList<Movie>movies = Helper.getMovieListByUserID();

        while (true) {
            System.out.println("Please enter the movie name you wan to cancel");
            String movieName = TicketSystem.SC.next();
            if (Helper.checkMovieInList(movieName, movies) == false){
                System.out.println("The name you enter is not in your list, please check");
            } else {
                Movie movie = Helper.getMovieByName(movieName);
                movies.remove(movie);
                TicketSystem.ALL_MOVIE_LIST.put((Merchant) TicketSystem.loginUser,movies);
                System.out.println("Movie is successfully canceled, here is your new movie list:");
                movieShowList();
                break;
            }
        }
    }

    private static void movieShowList() {
        System.out.println(TicketSystem.loginUser.getUserName() + "\t\tphone：" + TicketSystem.loginUser.getPhone()
                + "\t\tbalance：" + TicketSystem.loginUser.getAcctBalance());
        List<Movie> movies = TicketSystem.ALL_MOVIE_LIST.get(TicketSystem.loginUser);

        if(movies.size() > 0) {
            System.out.println("name\t\t\tactor\t\ttime\t\tscore\t\tprice\t\tremaining tickets\t\tstarting time");

            for (Movie movie : movies) {
                System.out.println(movie.getName()+"\t\t\t" + movie.getStarring()+ "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getMoviePrice() + "\t\t"
                        + movie.getRemainingTickets() + "\t\t" + TicketSystem.sdf.format(movie.getStartTime()));
            }
        } else {
            System.out.println("There's no movie in your account");
        }
    }

    private static void movieSetting() {
        System.out.println("Movie Setting Page");
        Merchant merchant = (Merchant) TicketSystem.loginUser;
        List<Movie> movies = TicketSystem.ALL_MOVIE_LIST.get(merchant);

        if(movies.size() == 0) {
            System.out.println("There's no movie in your account");
            return;
        }
        System.out.println("Here is your Movie List ");
        movieShowList();

        while (true) {
            System.out.println("Please enter the name you want to set up：");
            String movieName = TicketSystem.SC.nextLine();
            Movie movie = Helper.getMovieByName(movieName);

            if(movie != null){
                System.out.println("Please type the new name ：");
                String name  = TicketSystem.SC.nextLine();
                System.out.println("Please enter the nrw starring");
                String starring  = TicketSystem.SC.nextLine();
                System.out.println("Please enter the new duration：");
                String durationTime  = TicketSystem.SC.nextLine();
                System.out.println("Please enter the new price：");
                String price  = TicketSystem.SC.nextLine();
                System.out.println("please enter the new tickets number：");
                String totalNumber  = TicketSystem.SC.nextLine();

                while (true) {
                    try {
                        System.out.println("please enter the new starting time：");
                        String time  = TicketSystem.SC.nextLine();
                        movie.setName(name);
                        movie.setStarring(starring);
                        movie.setMoviePrice(Double.valueOf(price));
                        movie.setTime(Double.valueOf(time));
                        movie.setRemainingTickets(Integer.valueOf(totalNumber));
                        movie.setStartTime(TicketSystem.sdf.parse(time));
                        System.out.println("You have successfully change the movie info");
                        movieShowList();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        TicketSystem.logger.error("something wrong with time");
                    }
                }
            } else {
                System.out.println("There's no such movie name in your list！");
                System.out.println("Do you want to retry? y/n");
                String command = TicketSystem.SC.nextLine();

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
