package ru.mai.information_system.communication;

public class Url {

    public static String getUsersUrl() {
        return "http://127.0.0.1:8080/users";
    }

    public static String getBankAccountsUrl() {
        return "http://127.0.0.1:8080/bank_accounts";
    }

    public static String getBankAccountTypesUrl() {
        return "http://127.0.0.1:8080/bank_account_types";
    }

    public static String getTransactionsUrl() {
        return "http://127.0.0.1:8080/transactions";
    }

    public static String getTransactionsByDateUrl() {
        return "http://127.0.0.1:8080/transactions_by_date";
    }

    public static String getTransactionCategoriesUrl() {
        return "http://127.0.0.1:8080/transaction_categories";
    }

    public static String getSupportUrl() {
        return "http://127.0.0.1:8080/support";
    }
}
