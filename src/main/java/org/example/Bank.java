package org.example;

import java.sql.*;

public class Bank {
    private String name;

    public Bank(String name) {
        this.name = name;
    }
    public void listAccount(){
        Connection con = BankConnection.connect();
        try {
            Statement statement = con.createStatement();
            String sql = "Select * from account";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)
                        + " " + resultSet.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void openAccount(Account account){
        Connection con = BankConnection.connect();
        String sql = "insert into account(accID,accName,accBalance)" +
                "values(?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, account.getNumber());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeAccount(int number){
        Connection con = BankConnection.connect();
        String sql = "delete from account where accID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void depositMoney(Account account , Double amount){
        account.deposit(amount);
        Connection con = BankConnection.connect();
        String sql = "update account set accBalance = ? where accID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void withdrawMoney(Account account , Double amount){
        account.withdraw(amount);
    }
    public Account getAccount(int number){
        Connection con = BankConnection.connect();
        String sql = "Select * from account where accID =" + number;
        Account account = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                String accountName = resultSet.getString(2);
                double balance = resultSet.getDouble(3);
                account = new Account(number,accountName,balance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        double balance = 500;

        return account;
    }
}
