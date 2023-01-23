package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper(){
    }

    @Value
    public static class AuthInfo{
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo(){
        return new AuthInfo("vasya", "qwerty123");
    }


    @Value
    public  static class VerificationCode{
    private String cod;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }
    @Value
    public static class CardInfoTo {
        private String cardIdTo;
        private String cardIdFrom;
    }

    public static CardInfoTo getCardInfoToFirst() {
        return new CardInfoTo("5559 0000 0000 0001", "5559 0000 0000 0002");
    }

    public static CardInfoTo getCardInfoToSecond() {
        return new CardInfoTo("5559 0000 0000 0002", "5559 0000 0000 0001");
    }

    public static CardInfoTo getCardInfoWrongToFirst() {
        return new CardInfoTo("5559 1000 0000 0001", "5559 1000 0000 0002");
    }

    public static CardInfoTo getCardInfoWrongToSecond() {
        return new CardInfoTo("5559 1000 0000 0002", "5559 1000 0000 0001");
    }
}


