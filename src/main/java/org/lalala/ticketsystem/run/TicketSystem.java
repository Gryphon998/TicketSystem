package org.lalala.ticketsystem.run;

import org.lalala.ticketsystem.bean.Customer;
import org.lalala.ticketsystem.bean.Merchant;
import org.lalala.ticketsystem.bean.Movie;
import org.lalala.ticketsystem.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TicketSystem {
    private static final Logger logger = LoggerFactory.getLogger(TicketSystem.class);
    private static final Scanner SC = new Scanner(System.in);

    private static ArrayList<User> allUser = new ArrayList<>();
    private static Map<Merchant, ArrayList<Movie>> allMovieList = new HashMap<>();
    public static User loginUser;

    static {
        Customer c = new Customer();
        c.setUserAcctNum("zyf888");
        c.setPassWord("123456");
        c.setUserName("黑马刘德华");
        c.setGender('男');
        c.setAcctType(User.AcctType.individual);
        c.setAcctBalance(10000);
        c.setPhone("110110");
        allUser.add(c);

        Customer c1 = new Customer();
        c1.setUserAcctNum("gzl888");
        c1.setPassWord("123456");
        c1.setUserName("黑马关之琳");
        c1.setGender('女');
        c1.setAcctType(User.AcctType.individual);
        c1.setAcctBalance(2000);
        c1.setPhone("111111");
        allUser.add(c1);

        Merchant b = new Merchant();
        b.setUserAcctNum("baozugong888");
        b.setPassWord("123456");
        b.setUserName("黑马包租公");
        b.setAcctBalance(0);
        b.setPhone("110110");
        b.setAcctType(User.AcctType.business);
        b.setAddress("火星6号2B二层");
        b.setMerchantName("甜甜圈国际影城");
        allUser.add(b);
        // 注意，商家一定需要加入到店铺排片信息中去
        ArrayList<Movie> movies = new ArrayList<>();
        allMovieList.put(b , movies); // b = []

        Merchant b2 = new Merchant();
        b2.setUserAcctNum("baozupo888");
        b2.setPassWord("123456");
        b2.setUserName("黑马包租婆");
        b2.setAcctBalance(0);
        b2.setPhone("110110");
        b2.setAcctType(User.AcctType.business);
        b2.setAddress("火星8号8B八层");
        b2.setMerchantName("巧克力国际影城");
        allUser.add(b2);
        // 注意，商家一定需要加入到店铺排片信息中去
        ArrayList<Movie> movies3 = new ArrayList<>();
        allMovieList.put(b2 , movies3); // b2 = []
    }

    public static void main(String[] args) {
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
            User currentUser = Helper.getUser(userID,allUser);

            if(currentUser == null){
                System.out.println("Your account ID doesn't exist, please check and renter");
            } else {
                loginUser = currentUser;

                while (true) {
                    System.out.println("Please enter your password");
                    String password = SC.next();

                    if (loginUser.getPassWord().equals(password)) {
                        logger.info(loginUser.getUserAcctNum() + " logged in to the system");
                        System.out.println("Successful Login, Welcome " + loginUser.getUserName());

                        if (loginUser.getAcctType() == User.AcctType.business) {
                            MerchantOperation.merchantMainPage(allMovieList);
                        } else {
                            CustomerOperation.customerMainPage(allMovieList);
                        }
                        return;
                    } else {
                        System.out.println("Wrong password, please renter");
                    }
                }
            }
        }
    }

    private static void customerRegister() {
        System.out.println("Customer Register");
        Customer customer = new Customer();
        System.out.println("Plz set your account number");
        customer.setUserAcctNum(SC.next());
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
        allUser.add(customer);
        System.out.println("Successful register, welcome, " + customer.getUserName());
    }

    private static void merchantRegister() {
        System.out.println("Merchant Register");
        Merchant merchant = new Merchant();
        System.out.println("Plz set your account number");
        merchant.setUserAcctNum(SC.next());
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
        allUser.add(merchant);
        System.out.println("Successful register, welcome, " + merchant.getUserName());
    }

}