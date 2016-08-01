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
	 * 用户名
	 */
	public static String mobileName = "UserForm[mobile]";
	
	/**
	 * 密码
	 */
	public static String passwdName = "UserForm[passwd]";
	
	/**
	 * 登录按钮
	 */
	public static String loginId = "login-form";
	
	/**
	 * 标题
	 */
	public static String titleName = "ques[title]";
	
	/**
	 * 匿名
	 */
	public static String anonymousName = "ques[anonymous]";
	
	/**
	 * 提问提交按钮
	 */
	public static String askId = "user-ask-btn";
	
	
	/**
	 * 登录地址
	 */
	public static String loginUrl = "http://sns.qnzs.youth.cn/user/login/";

	/**
	 * 手机号
	 */
	public static String mobile = "13559248541";

	/**
	 * 密码
	 */
	public static String passwd = "96121621";

	/**
	 * 提问地址 您所在的位置是：泉州-丰泽http://fz.qz.fj.qnzs.youth.cn/question/ask/s/111041/l/0
	 * 您所在的位置是：泉州-丰泽-丰泽-东海街道http://sns.qnzs.youth.cn/question/ask/s/116648/l/1
	 * 您所在的位置是：泉州-丰泽-丰泽-东湖街道http://sns.qnzs.youth.cn/question/ask/s/116647/l/1
	 */
	public static String askUrl = "http://sns.qnzs.youth.cn/question/ask/";

	/**
	 * class根路径
	 */
	private static String root = System.getProperty("user.dir") + "//";//Constants.class.getResource("/").getFile();
	
	/**
	 * 前缀
	 */
	private static String askPreUrl = "http://sns.qnzs.youth.cn/question/ask/s/";
	
	/**
	 * 后缀
	 */
	private static String askAfterUrl = "/l/1";
	
	/**
	 * 地区分隔符
	 */
	private static String areaRegex = "\">";
	
	/**
	 * 地区配置文件
	 */
	private static String areaConfigFile = "config//areachange.txt";
	
	/**
	 * 标题配置文件
	 */
	private static String titleConfigFile = "config//temp.xls";
	
	/**
	 * 用户文件
	 */
	private static String userInfoConfigFile = "config//userinfo.properties";
	
	/**
	 * 基础配置文件
	 */
	private static String baseConfigFile = "config//baseconfig.properties";
	
	/**
	 * 手机号关键字
	 */
	private static String MOBILE = "mobile";
	/**
	 * 密码关键字
	 */
	private static String PASSWD = "passwd";
	/**
	 * 睡眠时间
	 */
	private static String SLEEPTIME_ITEM = "sleeptime";
	
	/**
	 * 默认睡眠时间
	 */
	private static long SLEEPTIME = 10000;
	
	/**
	 * 获取地区
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
			String line = reader.readLine(); // 读取第一行
			StringBuffer code = new StringBuffer("");
			list = new ArrayList<String>();
			while (line != null) { // 如果 line 为空说明读完了
				code = new StringBuffer("");
				code.append(askPreUrl).append(line.split(areaRegex)[0]).append(askAfterUrl);
				list.add(code.toString());
				line = reader.readLine(); // 读取下一行
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
	 * 读取excel获取标题列表
	 * @return
	 */
	public static List<String[]> getTitles() {
		
		// 返回结果
		List<String[]> result = new ArrayList<String[]>();
		// 列数
		int colSize = 0;
		// 起始行
		int ignoreRows = 1;
		// 起始列
		int ignoreCols = 1;

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(root + titleConfigFile));
			// 打开文件
			POIFSFileSystem fs = new POIFSFileSystem(in);
			// 打开工作薄
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			// sheet
			HSSFSheet st = null;
			// 行
			HSSFRow row = null;
			// 列
			HSSFCell cell = null;
			
			// 列数
			int tempColSize = 0;
			String[] values = null;
			String value = "";
			
			// 遍历工作薄
			for (int sheetIndex = 0, sheetsLen = wb.getNumberOfSheets(); sheetIndex < sheetsLen; sheetIndex++) {
				st = wb.getSheetAt(sheetIndex);
				// 第一行为标题，不取
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
					
					// 遍历列
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
	 * 获取用户
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
	 * 获取睡眠时间
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
