package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;

@Data
@NoArgsConstructor // 이걸 만들어줘도 괜찬다.(@Data를 쓴 상태로 생성자를 강제로 만들어버리면)
public class BoardVO  extends CommonMngVO { //bulletinBoard.ddl을 만들었기에그 Data를 가져오기위해 VO를 만듬
	private int id; // 아이디 이거 int인 이유가 예~~전에 숫자배열 엄청 만들어놓은거 있는데, 그걸 Id로 쓰기위해서
	private String name;	// 이름
	private String description;	//설명
	
	/*
	public BoardVO() {	// 이렇게 매개변수 없는걸 만들어도 괜찮고
	}
	*/
	
	public BoardVO(int id) {	//Test를 위해서 Id를 받아들이는 생성자 만들기
		this.id = id; // 이렇게 강제적으로 생성자를 만들면 NoAruConstructor가 사라짐
	}
	@Override
	public String toString() {
		return "BoardVO [id=" + id + ", name=" + name + ", description=" + description + ", toString()="
				+ super.toString() + "]"; // 출력을 확인하기 위한 toString 0520에 했던 Admin같은 것
	}
	
	
	
}
