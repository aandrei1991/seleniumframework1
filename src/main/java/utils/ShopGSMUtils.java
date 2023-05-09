package utils;

import enums.userInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import test.ShopGSMTests;

import java.util.ArrayList;
import java.util.List;

import static enums.userInfo.TestData.*;

public class ShopGSMUtils {
    public static String urlWebsite = "https://www.shopgsm.ro/";
    public static List<WebElement> mainLinks;
    public static List<WebElement> mainPageTitlesCarousel;
    public static WebElement contactPageLink;
    public static WebElement appleHuse;
    public static WebElement iphone7;
    public static WebElement addToCartHuse;
    public static WebElement popUpMessageWhenAddingItemsInCart;
    public static WebElement viewCart;
    public static WebElement email;
    public static WebElement name;
    public static WebElement surname;
    public static WebElement phone;
    public static WebElement address;
    public static WebElement city;
    public static WebElement orderConfirmation;

    public static String getHomePageTitle() {
        return ShopGSMTests.driver.getTitle();
    }

    public static List<String> getMainLinksFromHomepage() {
        mainLinks = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='store.menu']/nav/ul/li")));
        List<String> links = new ArrayList<>();
        for (WebElement link : mainLinks) {
            links.add(link.getText());
        }
        return links;
    }

    public static List<String> getMainPageTitlesCarousels() {
        mainPageTitlesCarousel = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h2[@class='filterproduct-title']")));
        List<String> titles = new ArrayList<>();
        for (WebElement title : mainPageTitlesCarousel) {
            titles.add(title.getText());
        }
        return titles;
    }

    public static void goToContactPage() {
        contactPageLink = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Contact']")));
        contactPageLink.click();
    }

    public static void addItemsInBasket() {
        getMainLinksFromHomepage();
        mainLinks.get(0).click();
        appleHuse = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main-brand-block']/ol/li[1]/div/a[2]")));
        appleHuse.click();
        iphone7 = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main-brand-block']/ol/li[1]/div/a[2]")));
        iphone7.click();
        addToCartHuse = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer-product-list']/div[2]/ol/li[1]/div/div[2]/div[3]/div/div/form/button")));
        addToCartHuse.click();
        popUpMessageWhenAddingItemsInCart = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='maincontent']/div[2]/div[2]/div/div/div")));
    }

    public static void goToFullCartAndIncreaseQuantity(int number) throws InterruptedException {
        viewCart = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='action viewcart']")));
        viewCart.click();
        for (int i = 1; i < number; i++) {
            WebElement increaseQuantity = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='shopping-cart-table']/tbody/tr[2]/td[3]/div/div[2]/a[1]/i")));
            increaseQuantity.click();
        }
        WebElement updateCartDetails = ShopGSMTests.driver.findElement(By.xpath("//*[@id='form-validate']/div[2]/button[2]"));
        updateCartDetails.click();
        Thread.sleep(2000);
    }

    public static void clickOnCheckoutPage() {
        WebElement checkOutButton = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-role='proceed-to-checkout']")));
        checkOutButton.click();
    }

    public static void fulfillCheckoutCartPageWithUserData() throws InterruptedException {
        email = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Adresa Email']")));
        email.sendKeys(userInfo.TestData.EMAIL.value);
        Thread.sleep(2000);
        name = ShopGSMTests.driver.findElement(By.xpath("//*[@placeholder='Prenume']"));
        name.sendKeys(userInfo.TestData.NUME.value);
        surname = ShopGSMTests.driver.findElement(By.xpath("//*[@placeholder='Nume']"));
        surname.sendKeys(PRENUME.value);
        phone = ShopGSMTests.driver.findElement(By.xpath("//*[@placeholder='Telefon']"));
        phone.sendKeys(TELEFON.value);
        address = ShopGSMTests.driver.findElement(By.xpath("//*[@placeholder='Adresa']"));
        address.sendKeys(ADRESA.value);
        city = ShopGSMTests.driver.findElement(By.xpath("//*[@placeholder='Oras']"));
        city.sendKeys(ORAS.value);
    }

    public static void clickOnPutOrder() throws InterruptedException {
        Thread.sleep(3000);
        WebElement clickOnPutOrder = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout']/div[4]/div[3]/div/div[4]/div/div/button")));
        clickOnPutOrder.click();
        orderConfirmation = ShopGSMTests.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='success-messages']/p[2]")));
    }
}
