package org.lalala.ticketsystem.run;


import org.lalala.ticketsystem.bean.Customer;
import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;
import org.lalala.ticketsystem.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TicketSystem {
    private static final Logger logger = LoggerFactory.getLogger(TicketSystem.class);
    private static final ArrayList<User> ALL_USERS = new ArrayList<>();
    private static final Map<Merchant, ArrayList<Movie>> ALL_MOVIE_LIST = new HashMap<>();
    private static final Scanner SC = new Scanner(System.in);
    public static User loginUser;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    static {
        Customer c = new Customer();
        c.setUserAcctNum("zyf888");
        c.setPassWord("123456");
        c.setUserName("黑马刘德华");
        c.setGender('男');
        c.setAcctType(User.AcctType.individual);
        c.setAcctBalance(10000);
        c.setPhone("110110");
        ALL_USERS.add(c);

        Customer c1 = new Customer();
        c1.setUserAcctNum("gzl888");
        c1.setPassWord("123456");
        c1.setUserName("黑马关之琳");
        c1.setGender('女');
        c1.setAcctType(User.AcctType.individual);
        c1.setAcctBalance(2000);
        c1.setPhone("111111");
        ALL_USERS.add(c1);

        Merchant b = new Merchant();
        b.setUserAcctNum("baozugong888");
        b.setPassWord("123456");
        b.setUserName("黑马包租公");
        b.setAcctBalance(0);
        b.setPhone("110110");
        b.setAcctType(User.AcctType.business);
        b.setAddress("火星6号2B二层");
        b.setMerchantName("甜甜圈国际影城");
        ALL_USERS.add(b);
        // 注意，商家一定需要加入到店铺排片信息中去
        ArrayList<Movie> movies = new ArrayList<>();
        ALL_MOVIE_LIST.put(b , movies); // b = []

        Merchant b2 = new Merchant();
        b2.setUserAcctNum("baozupo888");
        b2.setPassWord("123456");
        b2.setUserName("黑马包租婆");
        b2.setAcctBalance(0);
        b2.setPhone("110110");
        b2.setAcctType(User.AcctType.business);
        b2.setAddress("火星8号8B八层");
        b2.setMerchantName("巧克力国际影城");
        ALL_USERS.add(b2);
        // 注意，商家一定需要加入到店铺排片信息中去
        ArrayList<Movie> movies3 = new ArrayList<>();
        ALL_MOVIE_LIST.put(b2 , movies3); // b2 = []
    }

    public static void main(String[] args) {

        mainPage();
    }

    private static void mainPage() {
        while (true) {
            System.out.println("Welcome to the lalala online ticket system");
            System.out.println("Please select your option");
            System.out.println("1. User login");
            System.out.println("2. Customer register");
            System.out.println("3. Merchant register");

            int command = SC.nextInt();

            switch (command) {
                case 1:
                    loginPage();
                    break;
                case 2:
                    customerRegister();
                    break;
                case 3:
                    merchantRegister();
                    break;
                default:
                    System.out.println("Error command, please renter");

            }
        }
    }

    private static void loginPage() {
        while (true) {
            System.out.println("Welcome, user login");
            System.out.println("Please enter your user ID");
            String userID = SC.next();
            if(getUser(userID) == null){
                System.out.println("Your account ID doesn't exist, please check and renter");
            }else {
                loginUser = getUser(userID);
                while (true) {
                    System.out.println("Please enter your password");
                    String password = SC.next();
                    if (loginUser.getPassWord().equals(password)){
                        logger.info(loginUser.getUserAcctNum() + " logined to the system");
                        System.out.println("Successful Login, Welcome " + loginUser.getUserName());
                        if (loginUser.getAcctType() == User.AcctType.business){
                            merchantMainPage();
                        }else {
                            customerMainPage();
                        }
                        return;
    
                    } else {
                        System.out.println("Wrong password, please renter");
                    }
                }

            }
        }
    }

    private static void merchantMainPage() {
        while (true) {
            System.out.println("Hello " + loginUser.getUserName() + " please select your option");
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
        System.out.println("Your account number is " + loginUser.getUserAcctNum());
        System.out.println("Your total balance is " + loginUser.getAcctBalance());
        System.out.println("Your contact number is " + loginUser.getPhone());
    }
    private static void uploadMovie() {
        System.out.println("Merchant upload movies page");
        ArrayList<Movie> movies = getMovieListByUserID();
        Movie movie = new Movie();

        while (true) {
            System.out.println("Please enter movie name");
            String movieName = SC.next();
            if (checkMovieInList(movieName, movies) == true){
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
                String time  = SC.nextLine();
                // public Movie(String name, String actor, double time, double price, int number, Date startTime)        // 封装成电影对象 ，加入集合movices中去
                movie.setStartTime(sdf.parse(time));
                movies.add(movie);
                System.out.println("You have upload movie：《" + movie.getName() + "》");
                return; // 直接退出去
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("something wrong with time");
            }
        }

//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        Date date = null;
//        SC.nextLine();
//
//        while (date == null) {
//            System.out.println("Enter date and time in the format yyyy-MM-dd");
//            String line = SC.nextLine();
//            try {
//                date = format.parse(line);
//                movie.setStartTime(date);
//            } catch (ParseException e) {
//                System.out.println("Sorry, that's not valid. Please try again.");
//            }
//        }
//
//        movies.add(movie);
//        ALL_MOVIE_LIST.put((Merchant) loginUser, movies);
//
//        System.out.println("Congratulations, you have successfully upload movie into your account, " +
//                "here is your show list:");
//        movieShowList();
//        return;
    }




    private static void cancelMovie() {
        System.out.println("Here is your movie List: ");
        movieShowList();
        ArrayList<Movie>movies = getMovieListByUserID();

        while (true) {
            System.out.println("Please enter the movie name you wan to cancel");
            String movieName = SC.next();
            if (checkMovieInList(movieName, movies) == false){
                System.out.println("The name you enter is not in your list, please check");
            } else {
                Movie movie = getMovieByName(movieName);
                movies.remove(movie);
                ALL_MOVIE_LIST.put((Merchant) loginUser,movies);
                System.out.println("Movie is successfully canceled, here is your new movie list:");
                movieShowList();
                break;
            }
        }

    }


    private static void movieShowList() {

//        for (Movie movie:getMovieListByUserID()){
//            System.out.println(movie.getName())
        System.out.println(loginUser.getUserName() + "\t\tphone：" + loginUser.getPhone()
                + "\t\tbalance：" + loginUser.getAcctBalance());
        List<Movie> movies = ALL_MOVIE_LIST.get(loginUser);
        if(movies.size() > 0) {
            System.out.println("name\t\t\tactor\t\ttime\t\tscore\t\tprice\t\tremaining tickets\t\tstarting time");
            for (Movie movie : movies) {

                System.out.println(movie.getName()+"\t\t\t" + movie.getStarring()+ "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getMoviePrice() + "\t\t"
                        + movie.getRemainingTickets() + "\t\t" + sdf.format(movie.getStartTime()));
            }
        }else {
            System.out.println("There's no movie in your account");
        }
    }

    private static void movieSetting() {
        System.out.println("Movie Setting Page");
        Merchant merchant = (Merchant) loginUser;
        List<Movie> movies = ALL_MOVIE_LIST.get(merchant);

        if(movies.size() == 0) {
            System.out.println("There's no movie in your account");
            return;
        }

        System.out.println("Here is your Movie List ");
        movieShowList();

        while (true) {
            System.out.println("Please enter the name you want to set up：");
            String movieName = SC.nextLine();

            Movie movie = getMovieByName(movieName);
            if(movie != null){
                // 修改它
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
                        movieShowList();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("something wrong with time");
                    }
                }
            }else {
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


    private static void customerMainPage() {
        while (true) {
            System.out.println("Hello" + loginUser.getUserName() + " please select your option");
            System.out.println("Please select your option：");
            System.out.println("1. Show all movies");
            System.out.println("2. Score system");
            System.out.println("3. Buying tickets");
            System.out.println("4. Logging out");
            String command = SC.nextLine();
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
        ALL_MOVIE_LIST.forEach((merchant, movies) -> {
            System.out.println(merchant.getMerchantName() + "\t\tcontact number：" + merchant.getPhone() +
                    "\t\taddress: " + merchant.getAddress());
            System.out.println("name\t\t\tactor\t\ttime\t\tscore\t\tprice\t\tremaining tickets\t\tstarting time");
            for (Movie movie : movies) {
                System.out.println(movie.getName()+"\t\t\t" + movie.getStarring()+ "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getMoviePrice() + "\t\t"
                        + movie.getRemainingTickets() + "\t\t" + sdf.format(movie.getStartTime()));
            }
        });
    }
    private static void movieScoreSystem() {
        Customer customer = (Customer) loginUser;
        Map<Movie, Boolean> movies = customer.getPurchasedMovie();

        if(movies.size() == 0 ){
            System.out.println("You can not rate movies, yo haven't watch any movie yet :(");
            return;
        }

        movies.forEach((movie, flag) -> {
            if(flag){
                System.out.println(movie.getName() +"you have already rate the movie");
            }else {
                System.out.println("Please rate：" + movie.getName() +"（0-10）：");
                double score = Double.valueOf(SC.nextLine());
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
            String shopName = SC.nextLine();
            Merchant merchant = getMerchantByShopName(shopName);
            if(merchant == null){
                System.out.println("System can't find the merchant you enter, please check");
            }else {
                List<Movie> movies = ALL_MOVIE_LIST.get(merchant);
                if(movies.size() > 0) {
                    while (true) {
                        System.out.println("PLease enter the movie name you want to buy");
                        String movieName = SC.nextLine();
                        Movie movie = getMovieByShopAndName(merchant, movieName);
                        if(movie != null){
                            while (true) {
                                System.out.println("Please enter the ticket number：");
                                String number = SC.nextLine();
                                int buyNumber = Integer.valueOf(number);
                                if(movie.getRemainingTickets() >= buyNumber){
                                    double money = BigDecimal.valueOf(
                                            movie.getMoviePrice()).multiply(BigDecimal.valueOf(buyNumber))
                                            .doubleValue();
                                    if(loginUser.getAcctBalance() >= money){
                                        System.out.println("You have successfully bought "+ movie.getName()
                                                + buyNumber + "tickets, total is " + money);
                                        loginUser.setAcctBalance(loginUser.getAcctBalance() - money);
                                        merchant.setAcctBalance(merchant.getAcctBalance() + money);
                                        movie.setRemainingTickets(movie.getRemainingTickets() -  buyNumber);
                                        Customer customer = (Customer)loginUser;
                                        customer.getPurchasedMovie().put(movie, false);

                                        return;
                                    }else {
                                        System.out.println("Your account balance is not enough");
                                        System.out.println("Stay in this page? y/n");
                                        String command = SC.nextLine();
                                        switch (command) {
                                            case "y":
                                                break;
                                            default:
                                                System.out.println("Sure！");
                                                return;
                                        }
                                    }
                                }else {
                                    System.out.println("The remaining ticket number is ：" + movie.getRemainingTickets());
                                    System.out.println("Do you still want to buy？y/n");
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

                        }else {
                            System.out.println("The name you type is wrong, please check");
                        }
                    }

                }else {
                    System.out.println("This merchant has no movie in the show");
                    System.out.println("Stay in this page? y/n");
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

    private static Merchant getMerchantByShopName(String shopName) {
        Set<Merchant> merchants = ALL_MOVIE_LIST.keySet();
        for (Merchant merchant : merchants) {
            if(merchant.getMerchantName().equals(shopName)){
                return  merchant;
            }
        }
        return null;
    }

    public static Movie getMovieByShopAndName(Merchant merchant , String name){
        List<Movie> movies = ALL_MOVIE_LIST.get(merchant);
        for (Movie movie : movies) {
            if(movie.getName().contains(name)){
                return movie;
            }
        }
        return null;

    }
    private static User getUser(String userID) {
        for(User i:ALL_USERS){
            if (i.getUserAcctNum().equals(userID)){
                return i;
            }
        }
        return null;
    }

    private static Movie getMovieByName(String movieName) {
        for (Movie movie:getMovieListByUserID()){
            if (movie.getName().equals(movieName)){
                return movie;
            }
        }
        return null;
    }

    private static ArrayList<Movie> getMovieListByUserID() {
        for (Merchant merchant : ALL_MOVIE_LIST.keySet()) {
            if (merchant.getUserAcctNum().equals(loginUser.getUserAcctNum())){
                return ALL_MOVIE_LIST.get(merchant);
            }
        }
        return null;
    }

    private static void customerRegister() {
        System.out.println("Customer Register");
        Customer customer = new Customer();

        System.out.println("Plz enter your name");
        String userName = SC.next();
        customer.setUserName(userName);

        while (true) {
            System.out.println("Plz enter your password");
            String password = SC.next();
            System.out.println("Plz confirm your password");
            String passwordConfirmed = SC.next();

            if (password.equals(passwordConfirmed)) {
                customer.setPassWord(passwordConfirmed);
                break;
            } else {
                System.out.println("Password doesn't match, Plz reenter");
            }
        }

        customer.setAcctType(User.AcctType.individual);
        ALL_USERS.add(customer);
        System.out.println("Successful register, welcome, " + customer.getUserName());
    }

    private static void merchantRegister() {
        System.out.println("Merchant Register");
        Merchant merchant = new Merchant();

        System.out.println("Plz enter your name");
        String userName = SC.next();
        merchant.setUserName(userName);

        while (true) {
            System.out.println("Plz enter your password");
            String password = SC.next();
            System.out.println("Plz confirm your password");
            String passwordConfirmed = SC.next();

            if (password.equals(passwordConfirmed)) {
                merchant.setPassWord(passwordConfirmed);
                break;
            } else {
                System.out.println("Password doesn't match, Plz reenter");
            }
        }

        merchant.setAcctType(User.AcctType.business);
        ALL_USERS.add(merchant);
        System.out.println("Successful register, welcome, " + merchant.getUserName());
    }
    private static boolean checkMovieInList(String movieName, ArrayList<Movie> movies) {
        for (Movie i:movies){
            if (i.getName().equals(movieName)){
                return true;
            }
        }
        return false;
    }
}