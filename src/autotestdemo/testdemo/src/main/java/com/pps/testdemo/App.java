package com.pps.testdemo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
		String path = new File("").getAbsolutePath();
		String driver = path + File.separator + "geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driver);
		
		//Set Firefox Headless mode as TRUE
		FirefoxOptions options = new FirefoxOptions().setHeadless(false);
		
		//Instantiate Web Driver
		FirefoxDriver webdriver = new FirefoxDriver(options);
		webdriver.get("http://www.baidu.com");
		Capabilities capabilities = webdriver.getCapabilities();
		Navigation navigation = webdriver.navigate();
		Options manage = webdriver.manage();
		Window window = manage.window();		
		LocalStorage local = webdriver.getLocalStorage();
		SessionStorage session = webdriver.getSessionStorage();

		System.out.println("Page title is - " + webdriver.getTitle());
		//Search on ppsbbs
		webdriver.findElement(By.id("kw")).sendKeys("selenium webdriver");
		
		try {
            /**
             * WebDriver自带了一个智能等待的方法。
            manage.timeouts().implicitlyWait(arg0, arg1）；
            Arg0：等待的时间长度，int 类型 ；
            Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
             */
            manage.timeouts().implicitlyWait(3, TimeUnit.SECONDS);        
        } catch (Exception e) {
            e.printStackTrace();
        }
		//Display number of results on Console
		System.out.println("Total Results - " + webdriver.findElement(By.id("kw")).getTagName());
		
		manage.deleteAllCookies();
        /**
         * dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
         * 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
         * 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit作为一个case退出的方法。
         */
        webdriver.quit();//退出浏览器
    }
}