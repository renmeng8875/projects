/**
 * 
 */
package cannon.mybatis.sharding.plugin;

/**
 * @author fangjialong
 * @date 2015年9月8日 下午5:17:09
 */
public class ShardException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ShardException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ShardException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ShardException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ShardException(Throwable cause) {
		super(cause);
	}

}
