package www.dream.com.selectKey.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import www.dream.com.selectKey.model.DetailVO;
import www.dream.com.selectKey.model.MasterVO;

public interface MasterDetail {
	// 여기에는 함수 등록만하지 함수 정의는 xml에서 한다.
	void insertMaster(MasterVO newBie);

	int findByName(@Param("string") String string, @Param("reg_dt") Date reg_dt);

	void insertDetail(@Param("masterId") int idOfNew, @Param("detail") DetailVO addr);

	void insertMasterBySelectKey(MasterVO newBie);
}
