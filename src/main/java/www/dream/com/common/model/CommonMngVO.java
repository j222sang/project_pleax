package www.dream.com.common.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import www.dream.com.framework.printer.PrintTarget;
/**
 * 공통 관리 정보
 * @author Park
 *
 */
@Data
public abstract class CommonMngVO { // 1. 게시판에서 VO, 공통적 역할을 할 것을 작성
	/*
	private Date reg_dt;
	private Date upt_dt;
	*/
	
	private Date registrationDate; // 2. 가지고 와야하는 정보를 ddl에서 가져오고 Java 형식에 알맞은 자료형, 변수명 선언(등록시점)
	@PrintTarget(order=400, caption="수정일", pattern="yyyy-MM-dd")
	private Date updateDate;
	private String formattedUpdateDate;
	
	public String getFormattedUpdateDate() {
		if (updateDate != null) {
			SimpleDateFormat  fmt = new SimpleDateFormat("yyyy-MM-dd");
			return fmt.format(updateDate);
		}
		return "";
	}
	@Override
	public String toString() {
		return "CommonMngVO [등록일=" 
	+ registrationDate + ", 수정일=" + updateDate + "]";
	}
}
