package first.testweb.one;

/**
 * �û�
 * @author chuiting@linewell.com
 * @since 2016-07-27
 */
public class User {
	
	/**
	 * �ֻ���
	 */
	private String mobile;
	
	/**
	 * ����
	 */
	private String passwd;

	/**
	 * ��ȡ�ֻ���
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * �����ֻ���
	 * @param mobile �ֻ���
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * ��������
	 * @param passwd ����
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
