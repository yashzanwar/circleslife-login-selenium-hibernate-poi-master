package Pages;

import Data.DeliveryDetails;
import Framework.BrowserAction;
import Framework.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BuyPlan extends BrowserAction {
    public BuyPlan(WebDriver driver) {
        this.driver = driver;
    }

    Locator buyPlan(){
        return new Locator(By.xpath("//div[text()='BUY']"),"Buy Plan Button");
    }

    Locator buyThisPlan(){
        return new Locator(By.xpath("//div[text()='Buy This Plan']"),"Buy This plan Button");
    }

    Locator secureCheckout(){
        return new Locator(By.xpath("//*[text()='Secure Checkout']"),"Secure Checkout");
    }

    Locator fillName(){
        return new Locator(By.xpath("//input[@name='firstName']"),"Name Text Box");
    }

    Locator dropdown(String type){
        return new Locator(By.xpath("//select[@name='"+type+"']"),"BirthDay Selection");
    }

    Locator contactNumber(){
        return new Locator(By.xpath("//input[@name='contactNumber']"),"Contact Number");
    }

    Locator nricNo(){
        return new Locator(By.xpath("//input[@name='nric']"),"nric number");
    }

    Locator frontPic(){
        return new Locator(By.xpath("//div[text()='Front']"),"front pic");
    }

    Locator backPic(){
        return new Locator(By.xpath("//*[@id='st-container']/div/div/div[2]/span/div/div/div[2]/div/div/div/div[2]/div[2]/div[4]/div[2]/div[2]/div"),"back pic");
    }

    Locator shippingAddress(){
        return new Locator(By.xpath("//*[@id='PlacesAutocomplete__root']/input"),"Address");
    }

    Locator houseNo(){
        return new Locator(By.xpath("//input[@name='deliveryBlkHse']"),"house No");
    }

    Locator streetName(){
        return new Locator(By.xpath("//input[@name='deliveryStreetName']"),"Delivery Street Name");
    }

    Locator deliveryDateSlot(){
        return new Locator(By.xpath("//div[@class='col-xs-15 delivery-slots-day']"),"delivery slots");
    }

    Locator deliveryTimeSlot() {
        return new Locator(By.xpath("//div[@class='col-sm-4 col-xs-6 delivery-slots-time']"),"delivery time slot");
    }

    Locator uploadLater(){
        return new Locator(By.xpath("//*[@id='st-container']/div/div/div[2]/span/div/div/div[2]/div/div/div/div[2]/div[2]/div[3]/div/div/div/div/div/div/div[2]/a"),"upload later");
    }

    Locator checkLaterBox(){
        return new Locator(By.xpath("//img[@src='/assets/_react/bundle/images/854e8c8de8c388f5e5bbbf309cd1caca.svg']"),"check box");
    }

    Locator postalCode(){
        return new Locator(By.xpath("//input[@name='deliveryPostalCode']"),"postal code");
    }

    Locator payNow(){
        return new Locator(By.xpath("//*[@id='st-container']/div/div/div[2]/span/div/footer/div/div/div"), "Pay Now Button");
    }

    Locator visaButton(){
        return new Locator(By.xpath("//*[@name='visa_button']"),"Visa Button");
    }

    Locator autoComplete(){
        return new Locator(By.xpath("//*[@id='PlacesAutocomplete__autocomplete-container']//div[1]"),"Selected 1st address");
    }

    public void buyAPlan() throws IllegalAccessException, InstantiationException, InterruptedException {
        click(buyPlan());
        Thread.sleep(2000);
        click(buyThisPlan());
        click(secureCheckout());
    }

    public void fillDeliveryInfo(DeliveryDetails deliveryDetails) throws IllegalAccessException, InstantiationException, InterruptedException {
        ClearValue(fillName());
        EnterValue(fillName(),deliveryDetails.getFirstname().concat(" ").concat(deliveryDetails.getLastname()));
        Select select = new Select(driver.findElement(dropdown("birthDay").getBy()));
        select.selectByValue("07");
        select = new Select(driver.findElement(dropdown("birthMonth").getBy()));
        select.selectByValue("11");
        select = new Select(driver.findElement(dropdown("birthYear").getBy()));
        select.selectByValue("1994");
        EnterValue(contactNumber(),deliveryDetails.getTelephone());
        select = new Select(driver.findElement(dropdown("topNationality").getBy()));
        select.selectByValue("SGP");
        select = new Select(driver.findElement(dropdown("docType").getBy()));
        select.selectByValue("NRIC");
        ClearValue(nricNo());
        EnterValue(nricNo(),deliveryDetails.getNric());
        click(uploadLater());
        click(checkLaterBox());
        ClearValue(shippingAddress());
        EnterValue(shippingAddress(),deliveryDetails.getShippingaddress());
        click(autoComplete());
        Thread.sleep(1000);
        ClearValue(postalCode());
        EnterValue(postalCode(),deliveryDetails.getPostalcode());
        ClearValue(houseNo());
        EnterValue(houseNo(),deliveryDetails.getHouseno());
        ClearValue(streetName());
        EnterValue(streetName(),deliveryDetails.getStreetname());
        click(secureCheckout());
        }

        public void pay() throws IllegalAccessException, InstantiationException {
        click(payNow());
        }

        public boolean isCheckoutPageOpened(){
        return waitUntilDisplayed(visaButton(),60);
        }
    }

