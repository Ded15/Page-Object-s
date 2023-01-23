package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amount = $("[data-test-id=amount] [class=input__control]");
    private SelenideElement from = $("[data-test-id=from] [class=input__control]");
    private SelenideElement actionTransferButton = $("[data-test-id=\"action-transfer\"]");

    public TransferPage() {
        heading.shouldBe(visible);
    }


    public TransferPage moneyTransfer(DataHelper.CardInfoTo cardInfo, int amountValue) {

        amount.setValue(String.valueOf(amountValue));
        from.setValue(cardInfo.getCardIdFrom());
        actionTransferButton.click();

        return new TransferPage();
    }
}
