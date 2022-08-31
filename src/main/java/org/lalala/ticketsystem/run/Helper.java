package org.lalala.ticketsystem.run;

import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;
import org.lalala.ticketsystem.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Helper {
    public static Merchant getMerchantByShopName(String shopName) {
        Set<Merchant> merchants = TicketSystem.ALL_MOVIE_LIST.keySet();
        for (Merchant merchant : merchants) {
            if(merchant.getMerchantName().equals(shopName)){
                return  merchant;
            }
        }
        return null;
    }

    public static Movie getMovieByShopAndName(Merchant merchant , String name){
        List<Movie> movies = TicketSystem.ALL_MOVIE_LIST.get(merchant);
        for (Movie movie : movies) {
            if(movie.getName().contains(name)){
                return movie;
            }
        }
        return null;
    }

    public static User getUser(String userID) {
        for(User i:TicketSystem.ALL_USERS){
            if (i.getUserAcctNum().equals(userID)){
                return i;
            }
        }
        return null;
    }

    public static Movie getMovieByName(String movieName) {
        for (Movie movie:getMovieListByUserID()){
            if (movie.getName().equals(movieName)){
                return movie;
            }
        }
        return null;
    }

    public static ArrayList<Movie> getMovieListByUserID() {
        for (Merchant merchant : TicketSystem.ALL_MOVIE_LIST.keySet()) {
            if (merchant.getUserAcctNum().equals(TicketSystem.loginUser.getUserAcctNum())){
                return TicketSystem.ALL_MOVIE_LIST.get(merchant);
            }
        }
        return null;
    }

    public static boolean checkMovieInList(String movieName, ArrayList<Movie> movies) {
        for (Movie i:movies){
            if (i.getName().equals(movieName)){
                return true;
            }
        }
        return false;
    }
}
