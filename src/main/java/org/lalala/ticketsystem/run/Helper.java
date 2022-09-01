package org.lalala.ticketsystem.run;

import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;
import org.lalala.ticketsystem.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Helper {
    public static Merchant getMerchantByShopName(String shopName, Map<Merchant, ArrayList<Movie>> allMovieList) {
        Set<Merchant> merchants = allMovieList.keySet();
        for (Merchant merchant : merchants) {
            if(merchant.getMerchantName().equals(shopName)){
                return  merchant;
            }
        }
        return null;
    }

    public static Movie getMovieByShopAndName(Merchant merchant , String name,
                                              Map<Merchant, ArrayList<Movie>> allMovieList){
        List<Movie> movies = allMovieList.get(merchant);
        for (Movie movie : movies) {
            if(movie.getName().contains(name)){
                return movie;
            }
        }
        return null;
    }

    public static User getUser(String userID, ArrayList<User> allUser) {
        for(User user:allUser){
            if (user.getUserAcctNum().equals(userID)){
                return user;
            }
        }
        return null;
    }

    public static Movie getMovieByName(String movieName, Map<Merchant, ArrayList<Movie>> allMovieList) {
        for (Movie movie:getMovieListByUserID(allMovieList)){
            if (movie.getName().equals(movieName)){
                return movie;
            }
        }
        return null;
    }

    public static ArrayList<Movie> getMovieListByUserID(Map<Merchant, ArrayList<Movie>> allMovieList) {
        for (Merchant merchant : allMovieList.keySet()) {
            if (merchant.getUserAcctNum().equals(TicketSystem.loginUser.getUserAcctNum())){
                return allMovieList.get(merchant);
            }
        }
        return null;
    }

    public static boolean checkMovieInList(String movieName, ArrayList<Movie> movies) {
        for (Movie movie:movies){
            if (movie.getName().equals(movieName)){
                return true;
            }
        }
        return false;
    }

    public static void userChoice(String command) {
        switch (command) {
            case "y":
                break;
            default:
                System.out.println("Sure！");
                return;
        }
    }
}
