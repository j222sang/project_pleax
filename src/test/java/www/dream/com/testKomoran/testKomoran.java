package www.dream.com.testKomoran;

import java.util.List;

import org.junit.Test;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class testKomoran {
	private enum TargetPos {NNG, NNP, SL, SH};

	@Test
	public void test() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		String strToAnalyze = "리그오브레전드는 재미있는 게임 인데 정신병 걸릴 것 같아요";

		KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

		System.out.println(analyzeResultList.getPlainText());

		List<Token> tokenList = analyzeResultList.getTokenList();
		for (Token token : tokenList) {
			TargetPos dbug = null;
			try {
				dbug = TargetPos.valueOf(token.getPos()); 
			} catch (Exception e) {
				
			}
			if (dbug != null) {
			System.out.format("%s/%s\n", token.getMorph(),token.getPos());
					
			}
		}
	}
}
