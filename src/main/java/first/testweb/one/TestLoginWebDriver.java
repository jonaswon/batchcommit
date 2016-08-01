package first.testweb.one;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * ����֮��-�Ļ�����������д���ʣ�
 * @author chuiting@linewell.com
 * @since 2016-07-27
 */
public class TestLoginWebDriver {
	
	/**
	 * ������
	 * 1�������ļ�����һ���û��ɴ򿪶���������ÿ��������򿪶�ȡ��ͬ�����ݣ����������������ȡ��ͬ��ģ�����ݣ�
	 * 2�����ö���û����е�¼�����û���ȡ��Ӧ�����ݣ������û���ȡ��ͬ��ģ�����ݣ�
	 * 3��һ���û�һ���������ȡ���ļ�����������־�ļ���
	 * 4����¼������־���ڷ��������ֳ�����ȶ�
	 * 5��ͨ�������ļ��ķ�ʽ�ɴ�ie��������ȸ������
	 * 6����ӵ�¼ҳ���ַ����д��ҳ���ַ��ҳ����д��Ԫ������
	 * 
	 * ����ɣ�
	 * 1����ie�����
	 * 2���Զ������û��������룬�ȴ�10s�ֶ�������֤�룬���е�¼ϵͳ
	 * 3����ȡ�����б������б�
	 * 4����ȡ˯��ʱ��
	 * 5����д����ҳ�棨���⡢��ѡ����������ύ��
	 * 6��ѭ�����������б����������ύ
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
		// �û���
		WebElement nameElement = driver.findElement(By.name(Constants.mobileName));
		nameElement.sendKeys(user.getMobile());
		// ����
		WebElement passwdElement = driver.findElement(By.name(Constants.passwdName));
		passwdElement.sendKeys(user.getPasswd());

		// ��¼
		WebElement elementBtn = driver.findElement(By.id(Constants.loginId));
		elementBtn.submit();
		Thread.sleep(6000);

		// �����б�
		List<String> areas = Constants.getAreas();
		// �����б���
		int areaSize = areas.size();
		
		// �����б�
		List<String[]> titles = Constants.getTitles();
		if (null == titles || 0 == titles.size()) {
			driver.close();
		}
		// ������
		int titleSize = titles.size();
		// ���������url
		String askUrlRodm = null;
		
		// ����
		WebElement titleElement = null;
		// �����б�
		List<WebElement> anonymousElements = null;
		// ������ѡ��
		WebElement checkEle = null;
		// �ύ��ť
		WebElement askBtn = null;
		
		long sleeptime = Constants.getSleepTime();
		// ��������
		for (int i = 0; i < titleSize; i++) {
			askUrlRodm = areas.get(i % areaSize);
			
			driver.get(askUrlRodm);
			Thread.sleep(sleeptime);
			titleElement = driver.findElement(By.name(Constants.titleName));
			titleElement.sendKeys(titles.get(i));
			
			// ��������ѡ��
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
