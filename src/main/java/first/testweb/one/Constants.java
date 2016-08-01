package first.testweb.one;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Constants {

	/**
	 * �û���
	 */
	public static String mobileName = "UserForm[mobile]";
	
	/**
	 * ����
	 */
	public static String passwdName = "UserForm[passwd]";
	
	/**
	 * ��¼��ť
	 */
	public static String loginId = "login-form";
	
	/**
	 * ����
	 */
	public static String titleName = "ques[title]";
	
	/**
	 * ����
	 */
	public static String anonymousName = "ques[anonymous]";
	
	/**
	 * �����ύ��ť
	 */
	public static String askId = "user-ask-btn";
	
	
	/**
	 * ��¼��ַ
	 */
	public static String loginUrl = "http://sns.qnzs.youth.cn/user/login/";

	/**
	 * �ֻ���
	 */
	public static String mobile = "13559248541";

	/**
	 * ����
	 */
	public static String passwd = "96121621";

	/**
	 * ���ʵ�ַ �����ڵ�λ���ǣ�Ȫ��-����http://fz.qz.fj.qnzs.youth.cn/question/ask/s/111041/l/0
	 * �����ڵ�λ���ǣ�Ȫ��-����-����-�����ֵ�http://sns.qnzs.youth.cn/question/ask/s/116648/l/1
	 * �����ڵ�λ���ǣ�Ȫ��-����-����-�����ֵ�http://sns.qnzs.youth.cn/question/ask/s/116647/l/1
	 */
	public static String askUrl = "http://sns.qnzs.youth.cn/question/ask/";

	/**
	 * class��·��
	 */
	private static String root = System.getProperty("user.dir") + "//";//Constants.class.getResource("/").getFile();
	
	/**
	 * ǰ׺
	 */
	private static String askPreUrl = "http://sns.qnzs.youth.cn/question/ask/s/";
	
	/**
	 * ��׺
	 */
	private static String askAfterUrl = "/l/1";
	
	/**
	 * �����ָ���
	 */
	private static String areaRegex = "\">";
	
	/**
	 * ���������ļ�
	 */
	private static String areaConfigFile = "config//areachange.txt";
	
	/**
	 * ���������ļ�
	 */
	private static String titleConfigFile = "config//temp.xls";
	
	/**
	 * �û��ļ�
	 */
	private static String userInfoConfigFile = "config//userinfo.properties";
	
	/**
	 * ���������ļ�
	 */
	private static String baseConfigFile = "config//baseconfig.properties";
	
	/**
	 * �ֻ��Źؼ���
	 */
	private static String MOBILE = "mobile";
	/**
	 * ����ؼ���
	 */
	private static String PASSWD = "passwd";
	/**
	 * ˯��ʱ��
	 */
	private static String SLEEPTIME_ITEM = "sleeptime";
	
	/**
	 * Ĭ��˯��ʱ��
	 */
	private static long SLEEPTIME = 10000;
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public static List<String> getAreas() {
		List<String> list = null;
		
		FileInputStream ois = null;
		BufferedReader reader = null;
		try {
			
			ois = new FileInputStream(root + areaConfigFile);
			reader = new BufferedReader(new InputStreamReader(
					ois));
			String line = reader.readLine(); // ��ȡ��һ��
			StringBuffer code = new StringBuffer("");
			list = new ArrayList<String>();
			while (line != null) { // ��� line Ϊ��˵��������
				code = new StringBuffer("");
				code.append(askPreUrl).append(line.split(areaRegex)[0]).append(askAfterUrl);
				list.add(code.toString());
				line = reader.readLine(); // ��ȡ��һ��
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	/**
	 * ��ȡexcel��ȡ�����б�
	 * @return
	 */
	public static List<String[]> getTitles() {
		
		// ���ؽ��
		List<String[]> result = new ArrayList<String[]>();
		// ����
		int colSize = 0;
		// ��ʼ��
		int ignoreRows = 1;
		// ��ʼ��
		int ignoreCols = 1;

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(root + titleConfigFile));
			// ���ļ�
			POIFSFileSystem fs = new POIFSFileSystem(in);
			// �򿪹�����
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			// sheet
			HSSFSheet st = null;
			// ��
			HSSFRow row = null;
			// ��
			HSSFCell cell = null;
			
			// ����
			int tempColSize = 0;
			String[] values = null;
			String value = "";
			
			// ����������
			for (int sheetIndex = 0, sheetsLen = wb.getNumberOfSheets(); sheetIndex < sheetsLen; sheetIndex++) {
				st = wb.getSheetAt(sheetIndex);
				// ��һ��Ϊ���⣬��ȡ
				for (int rowIndex = ignoreRows, rowNum = st.getLastRowNum(); rowIndex <= rowNum; rowIndex++) {
					row = st.getRow(rowIndex);
					if (null == row) {
						continue;
					}
					tempColSize = row.getLastCellNum() - ignoreCols + 1;
					if (tempColSize > colSize) {
						colSize = tempColSize;
					}
					values = new String[colSize];
					Arrays.fill(values, "");
					
					// ������
					for (int columnIndex = ignoreCols, cellNum = row.getLastCellNum(); columnIndex <= cellNum; columnIndex++) {
						value = "";
						cell = row.getCell(columnIndex);
						if (null == cell) {
							continue;
						}
						if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
							value = cell.getStringCellValue();
						}
						values[columnIndex - ignoreCols] = value;
					}
					result.add(values);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * ��ȡ�û�
	 * @return
	 */
	public static User getUser() {
		User user = new User();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(root + userInfoConfigFile));
			props.load(in);
			Enumeration en = props.propertyNames();
			String key = null;
			String property = null;
			
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				property = props.getProperty(key);
				if (MOBILE.equalsIgnoreCase(key)) {
					user.setMobile(property);
				} else if (PASSWD.equalsIgnoreCase(key)) {
					user.setPasswd(property);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * ��ȡ˯��ʱ��
	 * @return
	 */
	public static long getSleepTime() {
		File file = new File(root + baseConfigFile);
		if (!file.exists()) {
			return SLEEPTIME;
		}
		
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(root + baseConfigFile));
			props.load(in);
			Enumeration en = props.propertyNames();
			String key = null;
			String property = null;
			
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				property = props.getProperty(key);
				if (SLEEPTIME_ITEM.equalsIgnoreCase(key)) {
					SLEEPTIME = Integer.parseInt(property);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SLEEPTIME;
	}
}
