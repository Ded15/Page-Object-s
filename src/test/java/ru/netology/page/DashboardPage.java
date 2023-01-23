package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement errorNotification = $("[data-test-id=\"error-notification\"] [class=\"notification__content\"]");

    private final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public void verification(String error) {
        errorNotification.shouldHave(text(error), Duration.ofMillis(15000));
    }

    public int[] getStartBalance(DataHelper.CardInfoTo cardInfo) {
        int[] startBalance = new int[2];
        startBalance[0] = getCardBalance(cardInfo.getCardIdTo());
        startBalance[1] = getCardBalance(cardInfo.getCardIdFrom());
        return startBalance;
    }

    public TransferPage choiceTransferTo(DataHelper.CardInfoTo cardInfo) {

        $(byXpath(".//*[contains(text(), '**** **** **** " + cardInfo.getCardIdTo().substring(15) + "\')]/button")).click();

        return new TransferPage();
    }


    public int getCardBalance(String cardBalance) {
        String cardText = "null";
        for (SelenideElement card : cards) {
            if (card.getText().contains(cardBalance.substring(15))) { // 4 last chars of the card
                cardText = card.getText();
            }
        }
        int balance = extractBalance(cardText);
        return balance;
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}