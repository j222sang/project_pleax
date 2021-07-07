package www.dream.com.common.dictionaryOrderSeed;

/**
 * 약 4월달쯤 했던 부분인데, Spring Board Project에서 임의의
 * Id Number를 만들기 위해서 사용될 코드
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DicOrderSeedGenerator {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USER_ID = "system";
	private static final String PASSWORD = "1234";
	
	private static Connection conn() { //Connection 확보하는 코드
		try {
			// 주어진 문자열을 기준으로 클래스 찾기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Success");
			Connection connection = DriverManager.getConnection(URL, USER_ID, PASSWORD);
			System.out.println("Connection Success");
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// 62진수의 문자를 준비함
		char[] ch = new char[62]; // 10 + 26 + 26 = 62 숫자, 소문자, 대문자 체계
		
		int idx = 0;
		for (char i = '0'; i <= '9'; i++) {
			ch[idx++] = i; // 10개의 값이 저장
		}
		for (char i = 'A'; i <= 'Z'; i++) {
			ch[idx++] = i; // 26개의 값이 저장
		}
		for (char i = 'a'; i <= 'z'; i++) {
			ch[idx++] = i; // 26개의 값이 저장
		}
		
		Connection conn = conn(); // Connection 객체를 만들것.
		if (conn == null)
			return;
		try {
			//500 만개의 data를 확보할 것
			PreparedStatement stmt = conn.prepareStatement("insert into s_id_seed(seq_id , seed) values(?, ?)");
			
			long seqId= 0;
			char[] chSeed = new char[5];
			for (int i = 0; i < 1; i++) {
				chSeed[0] = ch[i];
				for (int j = 0; j < 62; j++) {
					chSeed[1] = ch[j];
					for (int k = 0; k < 62; k++) {
						chSeed[2] = ch[k];
						for (int l = 0; l < 62; l++) {
							chSeed[3] = ch[l];
							for (int m = 0; m < 62; m++) {
								chSeed[4] = ch[m];
								stmt.setLong(1, seqId);
								stmt.setString(2, new String(chSeed));
								stmt.addBatch();
								stmt.clearParameters();
								seqId++;
							}
						}
					}
					stmt.executeBatch();
				}
			}
		}	catch(SQLException e) {
		e.printStackTrace();
		}
	}
}
