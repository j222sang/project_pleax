package www.dream.com.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.model.MultimediaType;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;

// 17. Test를 하기위한 @의 모임. 
@RunWith(SpringJUnit4ClassRunner.class) // 5. test 하기위해서 아까했던 TestDI를 가져오고
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//0521 Board의 속성 Data 값을 확인하기 위한 Junit Test

public class AttachMapperTest { 
	
	@Autowired // 
	private AttachFileVOMapper attachMapper; 

	//@Test
	public void test200Insert() {
		String postId = "TTTT2";
		List<AttachFileVO> list = new ArrayList<>();
		AttachFileVO avo = new AttachFileVO();
		avo.setUuid(UUID.randomUUID().toString());
		avo.setSavedFolderPath("sFP");
		avo.setPureFileName("pFN");
		avo.setMultimediaType(MultimediaType.image);
		list.add(avo);
		
		avo = new AttachFileVO();
		avo.setUuid(UUID.randomUUID().toString());
		avo.setSavedFolderPath("sFP");
		avo.setPureFileName("pFN");
		avo.setMultimediaType(MultimediaType.video);
		list.add(avo);
		
		attachMapper.insert(postId, list);
	}
	
	@Test
	public void test400Delete() {
		String postId = "TTTT2";
		attachMapper.delete(postId);
	}
}
