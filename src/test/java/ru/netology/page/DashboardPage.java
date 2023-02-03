package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

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

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        var text = cards.findBy(text(cardInfo.getCardIdTo().substring(15))).getText();
        return extractBalance(text);
    }

    public TransferPage choiceTransferTo(DataHelper.CardInfo cardInfo) {
        $(byXpath(".//*[contains(text(), '**** **** **** " + cardInfo.getCardIdTo ().substring(15) + "\')]/button")).click();
        return new TransferPage();
    }
    private int extractBalance(String text){
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text. substring(start+balanceStart.length(),finish);
        return Integer.parseInt(value);
    }
}