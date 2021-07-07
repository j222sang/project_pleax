package www.dream.com.framework.springSecurityAdapter;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import www.dream.com.party.model.Party;

public class CustomUser extends User {
	
	private Party party;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(Party party) {
		this(party.getUserId(), party.getUserPwd(), party.getAuthorityList());
		this.party = party;
		
	}
	
	public Party getCurUser() {
		return party;
	}
}
