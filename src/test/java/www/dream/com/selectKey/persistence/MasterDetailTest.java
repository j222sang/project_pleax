package www.dream.com.selectKey.persistence;

import java.util.Calendar;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.selectKey.model.DetailVO;
import www.dream.com.selectKey.model.MasterVO;

// 17. Test를 하기위한 @의 모임. 
@RunWith(SpringJUnit4ClassRunner.class) // 5. test 하기위해서 아까했던 TestDI를 가져오고
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MasterDetailTest { // Junit test case를 만들고, root-context.xml에 mybatis scan 경로 설정해주고
	
	@Autowired // 18. 
	private MasterDetail masterDetailMapper;  // private형 변수 선언
	
	

	@Test
	public void testInsertMaster() { // PK값 Test ... 05.24
		try {
			MasterVO newBie = new MasterVO(); // MasterVO 클래스에 대하여 객체 newBie 생성
			newBie.setName("Java NewBie"); // newBie 에 대해서 이름을 Java NewBie 저장할 것
			Date now = Calendar.getInstance().getTime(); // MasterVO.java에 reg_dt라는 등록시점을 추가했다. 그걸 이용하기 위한 객체 만들기
			// newBie.setReg_dt(Calendar.getInstance().getTime()); // newBie라는 객체에 Date Library를 사용하여서, 현재 시간을 불러 오겠다.
			newBie.setReg_dt(now); // 그 불러오겠다는 변수의 이름이 now. newBie객체에 now를 넣어준다. now라는 객체가 갖고있는건 바로 윗 두줄에서
			masterDetailMapper.insertMaster(newBie); // insertMaster라는 함수를 하나 만들어 줄 것
			// master 정보넣기
			
			int idOfNew = masterDetailMapper.findByName("Java NewBie", now); // selectKey를 사용하지 않을거라서, 강제로 저러한 함수를 만들어 줄 것
			
			DetailVO addr = new DetailVO(); // DetailVO 클래스에 대하여 객체 addr 생성
			addr.setInfo("address"); // party.ddl 에 address라고 저장을 해놓았다.
			masterDetailMapper.insertDetail(idOfNew, addr); // idOfNew를 던져 주면서 addr을 My-batis로 던져준다.  
		} catch(Exception e) {
			e.printStackTrace(); // 
		}
	}
	
	@Test
	public void testInsertMasterBySelectKey() { // 성능 개선을 위한 다른 Test 함수
		try {
			MasterVO newBie = new MasterVO(); // MasterVO 클래스에 대하여 객체 newBie 생성
			newBie.setName("Java NewBie"); // newBie 에 대해서 이름을 Java NewBie 저장할 것
			masterDetailMapper.insertMasterBySelectKey(newBie); // insertMaster라는 함수를 하나 만들어 줄 것
			// master 정보넣기
			
			
			DetailVO addr = new DetailVO(); // DetailVO 클래스에 대하여 객체 addr 생성
			addr.setInfo("address"); // party.ddl 에 address라고 저장을 해놓았다.
			masterDetailMapper.insertDetail(newBie.getId(), addr); // idOfNew를 던져 주면서 addr을 Mybatis로 던져준다.  
		} catch(Exception e) {
			e.printStackTrace(); // 
		}
	}

}
