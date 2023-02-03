package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amountImput = $("[data-test-id=amount] [class=input__control]");
    private SelenideElement fromImput = $("[data-test-id=from] [class=input__control]");
    private SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private  SelenideElement errorMessage=$("[data-test-id='error-message']");

    public TransferPage() {
        heading.shouldBe(visible);
    }
    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfoTo){
        makeTransfer(amountToTransfer, cardInfoTo);
        return new DashboardPage();
    }

    public void makeTransfer (String amountToTransfer, DataHelper.CardInfo cardInfo){
        amountImput.setValue(amountToTransfer);
        fromImput.setValue(cardInfo.getCardIdTo());
        transferButton.click();
    }
    public void findErrorMessage(String expectedText){
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
