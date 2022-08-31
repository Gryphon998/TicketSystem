package org.lalala.ticketsystem.run;

import org.lalala.ticketsystem.bean.Customer;
import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CustomerOperation {
    public static void customerMainPage() {
        while (true) {
            System.out.println("Hello" + TicketSystem.loginUser.getUserName() + " please select your option");
            System.out.println("Please select your option：");
            System.out.println("1. Show all movies");
            System.out.println("2. Score system");
            System.out.println("3. Buying tickets");
            System.out.println("4. Logging out");
            String command = TicketSystem.SC.nextLine();

            switch (command){
                case "1":
                    showAllMovies();
                    break;
                case "2":
                    movieScoreSystem();
                    break;
                case "3":
                    ticketingSystem();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Wrong option, please try again");
                    break;
            }
        }
    }

    private static void showAllMovies() {
        System.out.println("Movie on the list");
        TicketSystem.ALL_MOVIE_LIST.forEach((merchant, movies) -> {
            System.out.println(merchant.getMerchantName() + "\t\tcontact number：" + merchant.getPhone() +
                    "\t\taddress: " + merchant.getAddress());
            System.out.println("name\t\t\tactor\t\ttime\t\tscore\t\tprice\t\tremaining tickets\t\tstarting time");
            for (Movie movie : movies) {
                System.out.println(movie.getName()+"\t\t\t" + movie.getStarring()+ "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getMoviePrice() + "\t\t"
                        + movie.getRemainingTickets() + "\t\t" + TicketSystem.sdf.format(movie.getStartTime()));
            }
        });
    }

    private static void movieScoreSystem() {
        Customer customer = (Customer) TicketSystem.loginUser;
        Map<Movie, Boolean> movies = customer.getPurchasedMovie();

        if(movies.size() == 0 ){
            System.out.println("You can not rate movies, yo haven't watch any movie yet :(");
            return;
        }
        movies.forEach((movie, flag) -> {
            if(flag){
                System.out.println(movie.getName() +"you have already rate the movie");
            } else {
                System.out.println("Please rate：" + movie.getName() +"（0-10）：");
                double score = Double.valueOf(TicketSystem.SC.nextLine());
                movie.setScore((movie.getScore() + score) / 2);
                movies.put(movie, true);
            }
        });
    }

    private static void ticketingSystem() {
        showAllMovies();
        System.out.println("Ticketing System");

        while (true) {
            System.out.println("Please enter the merchant name：");
            String shopName = TicketSystem.SC.nextLine();
            Merchant merchant = Helper.getMerchantByShopName(shopName);

            if(merchant == null){
                System.out.println("System can't find the merchant you enter, please check");
            } else {
                List<Movie> movies = TicketSystem.ALL_MOVIE_LIST.get(merchant);

                if(movies.size() > 0) {
                    while (true) {
                        System.out.println("PLease enter the movie name you want to buy");
                        String movieName = TicketSystem.SC.nextLine();
                        Movie movie = Helper.getMovieByShopAndName(merchant, movieName);

                        if(movie != null){
                            while (true) {
                                System.out.println("Please enter the ticket number：");
                                String number = TicketSystem.SC.nextLine();
                                int buyNumber = Integer.valueOf(number);

                                if(movie.getRemainingTickets() >= buyNumber){
                                    double money = BigDecimal.valueOf(
                                                    movie.getMoviePrice()).multiply(BigDecimal.valueOf(buyNumber))
                                            .doubleValue();

                                    if(TicketSystem.loginUser.getAcctBalance() >= money){
                                        System.out.println("You have successfully bought "+ movie.getName()
                                                + buyNumber + "tickets, total is " + money);
                                        TicketSystem.loginUser.setAcctBalance(TicketSystem.loginUser.getAcctBalance() - money);
                                        merchant.setAcctBalance(merchant.getAcctBalance() + money);
                                        movie.setRemainingTickets(movie.getRemainingTickets() -  buyNumber);
                                        Customer customer = (Customer)TicketSystem.loginUser;
                                        customer.getPurchasedMovie().put(movie, false);
                                        return;
                                    } else {
                                        System.out.println("Your account balance is not enough");
                                        System.out.println("Stay in this page? y/n");
                                        String command = TicketSystem.SC.nextLine();
                                        switch (command) {
                                            case "y":
                                                break;
                                            default:
                                                System.out.println("Sure！");
                                                return;
                                        }
                                    }
                                } else {
                                    System.out.println("The remaining ticket number is ：" + movie.getRemainingTickets());
                                    System.out.println("Do you still want to buy？y/n");
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
                        }else {
                            System.out.println("The name you type is wrong, please check");
                        }
                    }
                }else {
                    System.out.println("This merchant has no movie in the show");
                    System.out.println("Stay in this page? y/n");
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
}
