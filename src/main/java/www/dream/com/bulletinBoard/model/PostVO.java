package www.dream.com.bulletinBoard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.framework.lengPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassPrintTarget;
import www.dream.com.framework.printer.PrintTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.hashTag.model.IHashTagOpponent;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * 
 * @author Park
 *
 */
@Data
@NoArgsConstructor // 여기서도 생성자를 강제로 만들거기 때문에
@ClassPrintTarget
public class PostVO extends ReplyVO implements IHashTagOpponent{
	public static final String DESCRIM4POST = "post";

	@HashTarget
	private String title;
	private int readCnt;
	private int likeCnt; // 좋아요 수
	private int dislikeCnt; // 싫어요 수
	
	private List<String> listAttachInStringFormat;
	private List<AttachFileVO> listAttach;

	public PostVO(String title, String content, Party writer) {
		super(content, writer);
		this.title = title;
	}
	
	public String getType() {
		return "Post";
	}
	
	@PrintTarget(order=100, caption="제목", withAnchor=true)
	public String getTitleWithCnt() {
		return title + " [" + super.replyCnt + "]"; 
	}
	
	public List<String> getAttachListInGson() { // 기본 Architecture을 만듬
		List<String> ret = new ArrayList<>();
		ret.addAll(listAttach.stream().map(vo -> vo.getJson()).collect(Collectors.toList()));
		return ret;
	}

	public void parseAttachInfo() {
		if (listAttach == null) {
			listAttach = new ArrayList<>();
		}
		
		if (listAttachInStringFormat != null) {
			for (String ai : listAttachInStringFormat) {
				listAttach.add(AttachFileVO.fromJson(ai));
			}
		}
	}
	
	@Override
	public String toString() {
		return "PostVO [" + ToStringSuperHelp.trimSuperString(super.toString()) + "," + " title=" + title + ",readCnt="
				+ readCnt + ", likeCnt=" + likeCnt + ", dislikeCnt=" + dislikeCnt + "]";
	}
}
