package test;

import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ShopGSMUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ShopGSMUtils.clickOnPutOrder;
import static utils.ShopGSMUtils.orderConfirmation;

public class ShopGSMTests {

    public static WebDriver driver;
    public static WebDriverWait wait;

    @Rule
    public TestName name = new TestName();
    public static SoftAssertions softly = new SoftAssertions();

    @Before
    public void initBeforeTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get(ShopGSMUtils.urlWebsite);
    }

    @After
    public void clean() {
        softly.assertAll();
        driver.close();
    }

    @Test
    public void checkHomePageTitle() {
        softly.assertThat(ShopGSMUtils.getHomePageTitle())
                .describedAs("THE TITLE IS NOT CORRECT")
                .isEqualTo("ShopGSM - Huse, Folii, Cabluri si multe alte accesorii premium. " +
                        "Livrare imediata din stoc. Deschidere colet. " +
                        "Transportul Gratuit la comenzi de peste 49 lei");
    }

    @Test
    public void checkMainLinkButtons() {

        List<String> listMainLinks = new ArrayList<>(Arrays.asList("HUSE", "FOLII", "CABLURI", "MASTI DE PROTECTIE", "INCARCATOARE", "AUDIO", "SUPORTURI", "STOCARE", "TELEFOANE", "TABLETE", "TV"));

        for (int i = 0; i < listMainLinks.size(); i++) {
            softly.assertThat(ShopGSMUtils.getMainLinksFromHomepage().get(i))
                    .describedAs("THE MAIN LINKS ARE NOT CORRECT")
                    .isEqualTo(listMainLinks.get(i));
        }
        softly.assertThat(ShopGSMUtils.getMainLinksFromHomepage().get(0))
                .describedAs("THE MAIN LINKS ARE NOT CORRECT")
                .isEqualTo("HUSE");

        softly.assertThat(ShopGSMUtils.getMainLinksFromHomepage().get(1))
                .describedAs("THE MAIN LINKS ARE NOT CORRECT")
                .isEqualTo("FOLII");
    }

    @Test
    public void checkCarouselsTitlesHomepage() {
        List<String> listMainTitlesCarousel = new ArrayList<>(Arrays.asList("VA RECOMANDAM", "NOUTATI - HUSE TELEFOANE", "NOUTATI - FOLII TELEFOANE", "NOUTATI - CABLURI TELEFOANE"));
        for (int i = 0; i < listMainTitlesCarousel.size(); i++) {
            softly.assertThat(ShopGSMUtils.getMainPageTitlesCarousels().get(i))
                    .describedAs("THE MAIN LINKS ARE NOT CORRECT")
                    .isEqualTo(listMainTitlesCarousel.get(i));
        }
    }

    @Test
    public void checkContactPage() {
        ShopGSMUtils.goToContactPage();
        softly.assertThat(driver.getTitle())
                .describedAs("THE TITLE IS NOT CORRECT")
                .isEqualTo("ShopGSM - Contactați-ne");
    }

    @Test
    public void checkMiniCartPopUp() {
        ShopGSMUtils.addItemsInBasket();
        softly.assertThat(ShopGSMUtils.popUpMessageWhenAddingItemsInCart.getText())
                .describedAs("THE ITEM ADDED TO CART IS WRONG")
                .isEqualTo("Ați adăugat Husa Senno Pure Flex Slim TPU 0.8mm, Apple iPhone 7/8 in coș.");
    }

    @Test
    public void checkFullCartAfterQtyUpdate() throws InterruptedException {
        ShopGSMUtils.addItemsInBasket();
        ShopGSMUtils.goToFullCartAndIncreaseQuantity(3);
        Thread.sleep(2000);
        softly.assertThat(driver.findElement(By.xpath("//*[@class='input-text qty']")).getAttribute("value"))
                .describedAs("THE QUANTITY IS WRONG")
                .isEqualTo("3");
    }

    @Test
    public void checkAFullCheckoutProcess() throws InterruptedException {
        ShopGSMUtils.addItemsInBasket();
        ShopGSMUtils.goToFullCartAndIncreaseQuantity(3);
        ShopGSMUtils.clickOnCheckoutPage();
        ShopGSMUtils.fulfillCheckoutCartPageWithUserData();
        clickOnPutOrder();
        softly.assertThat(orderConfirmation.getText())
                .describedAs("THE ORDER IS NOT CONFIRMED")
                .isEqualTo("Veți primi un email pentru confirmarea comenzii cu detalii și informații de urmărire al acesteia.");
    }
}
