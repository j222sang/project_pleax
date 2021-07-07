package www.dream.com.selectKey.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
// ddl을 만들었기에 그에 따른 Java에 맞는 형식을 만들어 줘야한다.(기계적으로)
@Data
public class MasterVO {
	private int	id;
	private String	name;
	private Date reg_dt;
	
	private List<DetailVO> listDetail;
}
