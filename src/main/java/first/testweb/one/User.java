package first.testweb.one;

/**
 * 用户
 * @author chuiting@linewell.com
 * @since 2016-07-27
 */
public class User {
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 密码
	 */
	private String passwd;

	/**
	 * 获取手机号
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置手机号
	 * @param mobile 手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 获取密码
	 * @return
	 */
	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * 设置密码
	 * @param passwd 密码
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
