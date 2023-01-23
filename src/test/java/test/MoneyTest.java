package test;

import org.junit.jupiter.api.Test;
import  ru.netology.data.DataHelper;
import  ru.netology.page.DashboardPage;
import  ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {
    @Test
    void shouldTransferMoneyFromSecondToFirstCardEnough() { //перевод с 1-й на 2-ю
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 5000;
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToFirst();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - amount);
    }


    @Test
    void shouldTransferMoneyFromFirstToSecondCardEnough() { //перевод с 2-й на 1-ю
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 5000;//amount for transfer
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToSecond();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - amount);
    }


    @Test
    void shouldTransferMoneyFromSecondToFirstCardUnderZero() { //перевод всего баланса
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = -10_000;//amount for transfer
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToFirst(); // transfer to first from second card
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + Math.abs(amount));
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - Math.abs(amount));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCardZero() { // превод 0 руб.
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 0;
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToSecond();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - amount);
    }

    @Test
    void shouldTransferMoneyFromWrongSecondToFirstCard() { //перевод с нулевым балансом
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 500;//amount for transfer
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoWrongToFirst();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);
        dashboardPage.verification("Ошибка! Произошла ошибка");
    }

}
