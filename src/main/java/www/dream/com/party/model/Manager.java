package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 시스템 운영자
 * @author Park
 */
@Data
@NoArgsConstructor
public class Manager extends Party {
	public Manager(String userId) {
		super(userId);
	} // 7-1. Party를 상속받는 Manager 클래스 생성
	
	private static List<AuthorityVO> listAuthority = new ArrayList<AuthorityVO>();
	static {
		listAuthority.add(new AuthorityVO("manager"));
		listAuthority.add(new AuthorityVO("user"));
	}
	@Override
	public List<AuthorityVO> getAuthorityList() {
		return listAuthority;
	}
}
