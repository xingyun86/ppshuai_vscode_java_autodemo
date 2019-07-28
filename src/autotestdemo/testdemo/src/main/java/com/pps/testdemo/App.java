package com.pps.testdemo;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.grid.selenium.GridLauncherV3;
import org.openqa.grid.shared.Stoppable;
/**
 * Hello world!
 *
 */
public class App 
{
    static int m_nRunning = 0;
    static Logger m_logger_main = null;
    static Logger m_logger_service = null;
    static FileHandler m_fileHandler = null;
    static ConsoleHandler m_consoleHandler = null;
    static Stoppable m_stoppable = null;
    static RemoteWebDriver m_webDriver = null;
    static ArrayList<String> arrayList = null;
    static FirefoxOptions desiredCapabilities = null;
    static String url = "http://127.0.0.1:4444/wd/hub";
    public static Logger initLogger(String loggerName)
    {
        Logger _logger = null;
        try {
            _logger = Logger.getLogger(loggerName);
            _logger.setLevel(Level.ALL);
            
            m_consoleHandler = new ConsoleHandler();
            m_consoleHandler.setLevel(Level.ALL);
            m_fileHandler = new FileHandler("./" + loggerName + ".log");
            m_fileHandler.setLevel(Level.ALL);
            m_fileHandler.setFormatter(new LogHander());
            _logger.addHandler(m_consoleHandler);
            _logger.addHandler(m_fileHandler); 
        } catch (Exception e) {
            //TODO: handle exception
            _logger.info(e.getMessage());
            System.exit(0);
        }
        return _logger;
    }
    public static void prepare(String[] args)
    {
    }
    
    public static int running(String[] args)
    {
        return m_nRunning;
    }
    public static void startup(String[] args)
    {
        //app_init("jvmapp-service-");
        m_logger_service = initLogger("jvmapp-service" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
        try
        {
            //m_stoppable = gridLauncherV3.launch(new String[]{});                
            m_stoppable = new GridLauncherV3().launch(new String[]{"-host","127.0.0.1","-port","4444","-role","standalone"}/*"node","hub"*/);
            m_logger_service.info("service started!");
        }catch(Exception e){
            //TODO: handle exception
            //e.printStackTrace();
            m_stoppable = null;
        }
    }
    public static void cleanup(String[] args)
    {
        //app_init();
        try
        {
            if(m_stoppable != null)
            {
                // Exit application
                m_stoppable.stop();
            }
            m_logger_service.info("service stopped!");
        }catch(Exception e){
            //TODO: handle exception
            //e.printStackTrace();
            m_stoppable = null;
        }
        System.exit(0);
    }
    public static void main( String[] args )
    {
        int m_index = 0;
        
        m_logger_main = initLogger("jvmapp-main" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
        //app_init("main");

		//String root = new File("").getAbsolutePath();
		//String driver = root + File.separator + "geckodriver.exe";
        
        m_logger_main.info("Enter process now!");

        arrayList = new ArrayList<>(Arrays.asList(
            "http://www.baidu.com",
            "http://www.ppsbbs.tech",
            "http://www.baidu.com",
            "http://www.ppsbbs.tech",
            "http://www.baidu.com",
            "http://www.ppsbbs.tech"
        ));
        desiredCapabilities = new FirefoxOptions().setHeadless(true);
        
        m_index = 0;
        m_nRunning = 1;
        while(m_nRunning == 1)
        {
            try
            {
                m_logger_main.info("ArraySize="+Integer.toString(arrayList.size()));
                m_webDriver = new RemoteWebDriver(new URL(url), desiredCapabilities);
                for(; m_index < arrayList.size();)
                {
                    String s = arrayList.get(m_index);
                    m_webDriver.get(s);
                    //webdriver.switchTo().window(webdriver.getWindowHandles().toArray()[0].toString());
    
                    //Search on ppsbbs
                    //m_logger.info("A text is - " +m_webDriver.findElement(By.tagName("a")).getText());
                    m_logger_main.info("A href is - " +m_webDriver.findElement(By.tagName("a")).getAttribute("href"));
                
                    //
                    // WebDriver自带了一个智能等待的方法。
                    //manage.timeouts().implicitlyWait(arg0, arg1）；
                    //Arg0：等待的时间长度，int 类型 ；
                    //Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
                    //
                    //manage.timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                    
                    m_logger_main.info("Page title is - " + m_webDriver.getTitle());
                    m_logger_main.info("Current url is - " + m_webDriver.getCurrentUrl());
                    //Display number of results on Console
                    //System.out.println("Total Results - " + webdriver.findElement(By.id("kw")).getTagName());
                    
                    m_webDriver.manage().deleteAllCookies();
                    m_logger_main.info(s);
                    m_logger_main.info(Integer.toString(++m_index));
                    Thread.sleep(1000);
                }
                m_logger_main.info("End ArraySize="+Integer.toString(arrayList.size()));
                
                if(m_webDriver != null)
                {
                    //
                    // dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
                    // 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
                    // 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit作为一个case退出的方法。
                    //
                    m_webDriver.quit();//退出浏览器
                    m_logger_main.info("m_webDriver.quit()");
                }
                m_webDriver = null;
                // Exit application
                m_nRunning = 0;
                m_logger_main.info("for(String s : arrayList) exit");
            } catch (Exception e) {
                //TODO: handle exception   
                if(m_webDriver != null)
                {
                    //
                    // dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
                    // 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
                    // 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit作为一个case退出的方法。
                    //
                    m_webDriver.quit();//退出浏览器
                    m_logger_main.info("m_webDriver.quit()");
                }
                m_webDriver = null;
                continue;             
            }
        }
        m_logger_main.info("all completed");
        //System.exit(0);
    }
}