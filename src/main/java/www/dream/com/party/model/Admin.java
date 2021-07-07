package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사장님(운영자 등록, 삭제 권한 있음)
 * @author Park
 */
@Data
@NoArgsConstructor
 // 7. Party를 상속받는 Admin 클래스 생성 // 21. 잠시 상속구조 끊어내고 Admin에서 Test 하면 잘 나온다.
 // 여기에서도 생성자를 만들기 위해서
public class Admin extends Party {
	
	private static List<AuthorityVO> listAuthority = new ArrayList<AuthorityVO>();
	static {
		listAuthority.add(new AuthorityVO("admin"));
		listAuthority.add(new AuthorityVO("manager"));
		listAuthority.add(new AuthorityVO("user"));
	}
	
	@Override
	public List<AuthorityVO> getAuthorityList() {
		return listAuthority;
	} 
	
	public Admin(String userId) { // super로 만들어줘야함, party가 abstract 라서
		super(userId);
	}
	
	@Override
	public String toString() {
		return "Admin [toString()=" + super.toString() + "]";
		// Data Test를 하기위한 toString 구문.
	}

	
}
