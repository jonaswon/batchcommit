package first.testweb.one;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 青年之声-心互动（批量填写提问）
 * @author chuiting@linewell.com
 * @since 2016-07-27
 */
public class TestLoginWebDriver {
	
	/**
	 * 待改善
	 * 1）配置文件配置一个用户可打开多个浏览器，每个浏览器打开读取不同的数据（根据浏览器个数读取不同的模板数据）
	 * 2）配置多个用户进行登录，该用户读取对应其数据（根据用户读取不同的模板数据）
	 * 3）一个用户一个浏览器读取的文件结果输出到日志文件中
	 * 4）记录错误日志利于分析，保持程序的稳定
	 * 5）通过配置文件的方式可打开ie、火狐、谷歌浏览器
	 * 6）添加登录页面地址、填写表单页面地址、页面填写的元素配置
	 * 
	 * 已完成：
	 * 1）打开ie浏览器
	 * 2）自动输入用户名、密码，等待10s手动输入验证码，进行登录系统
	 * 3）获取地区列表、标题列表
	 * 4）获取睡眠时间
	 * 5）填写提问页面（标题、勾选匿名，点击提交）
	 * 6）循环遍历标题列表，进行批量提交
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.ie.driver", "C:\\testwebdriver\\IEDriverServer.exe");
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		WebDriver driver = new InternetExplorerDriver(ieCapabilities);
		driver.get(Constants.loginUrl);

		Thread.sleep(10000);
		
		User user = Constants.getUser();
		// 用户名
		WebElement nameElement = driver.findElement(By.name(Constants.mobileName));
		nameElement.sendKeys(user.getMobile());
		// 密码
		WebElement passwdElement = driver.findElement(By.name(Constants.passwdName));
		passwdElement.sendKeys(user.getPasswd());

		// 登录
		WebElement elementBtn = driver.findElement(By.id(Constants.loginId));
		elementBtn.submit();
		Thread.sleep(6000);

		// 地区列表
		List<String> areas = Constants.getAreas();
		// 地区列表数
		int areaSize = areas.size();
		
		// 标题列表
		List<String[]> titles = Constants.getTitles();
		if (null == titles || 0 == titles.size()) {
			driver.close();
		}
		// 标题数
		int titleSize = titles.size();
		// 请求的提问url
		String askUrlRodm = null;
		
		// 标题
		WebElement titleElement = null;
		// 匿名列表
		List<WebElement> anonymousElements = null;
		// 匿名复选框
		WebElement checkEle = null;
		// 提交按钮
		WebElement askBtn = null;
		
		long sleeptime = Constants.getSleepTime();
		// 遍历标题
		for (int i = 0; i < titleSize; i++) {
			askUrlRodm = areas.get(i % areaSize);
			
			driver.get(askUrlRodm);
			Thread.sleep(sleeptime);
			titleElement = driver.findElement(By.name(Constants.titleName));
			titleElement.sendKeys(titles.get(i));
			
			// 匿名设置选中
			// document.getElementsByName("ques[anonymous]")[0].checked
			anonymousElements = driver.findElements(By.name(Constants.anonymousName));
			checkEle = anonymousElements.get(0);
			checkEle.click();
			Thread.sleep(3000);
			
			askBtn = driver.findElement(By.className(Constants.askId));
			askBtn.click();
			Thread.sleep(6000);
		}

		// driver.close();
	}
}
