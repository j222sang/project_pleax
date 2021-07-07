package www.dream.com.common.attachFile.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//여러가지 File 형식들을 다룰거라서 enum으로 정의
public enum MultimediaType {
	image, video, audio, others;
	
	public static MultimediaType identifyMultimediaType(File file) {
		String contentType = null;
		try {
			contentType = Files.probeContentType(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (contentType == null ||contentType.startsWith("text"))
			return MultimediaType.others;
		
		if (contentType.startsWith("image"))
			return MultimediaType.image;
		if (contentType.startsWith("audio"))
			return MultimediaType.audio;
		if (contentType.startsWith("video"))
			return MultimediaType.video;
		
		System.out.println("새로운 국제 표준이 만들어 졌을까요?" + contentType);
		return null;
	}
}
