package www.dream.com.common.attachFile.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import www.dream.com.framework.util.FileUtil;

@Data
@NoArgsConstructor
public class AttachFileVO {
	public static final String THUMBNAIL_FILE_PREFIX = "thumb_";
	public static final String UUID_PURE_SEP = "_" ;
	
	//사용자가 업로드한 순수 파일 이름
	@Expose
	private String pureFileName;
	@Expose
	private String pureSaveFileName;
	@Expose
	private String pureThumbnailFileName;
	
	
	public static String filterPureFileName(String pureSaveFileName) {
		return pureSaveFileName.substring(pureSaveFileName.indexOf(UUID_PURE_SEP) + 1);
	};
	
	
	
	//서버에서 저장된 폴더 이름
	@Expose
	private String savedFolderPath;
	//UUID
	@Expose
	private String uuid;
	//MultimediaType
	@Expose
	private MultimediaType multimediaType;
	
	@Expose
	private String fileCallPath;
	@Expose
	private String originalFileCallPath;
	
	/**
	 * Ajax를 통하여 사용자의 파일을 서버의 지정 폴더로 업로드할 때 활용하는 생성자 
	 * @param uploadFullPath
	 * @param uploadFile
	 */
	public AttachFileVO(File uploadFullPath, MultipartFile uploadFile) {
		savedFolderPath = uploadFullPath.getAbsolutePath();	
		
		String originalFileName = uploadFile.getOriginalFilename();
		pureFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
		
		uuid = UUID.randomUUID().toString();
		
		pureSaveFileName = uuid + UUID_PURE_SEP + pureFileName;
		try {
			File save = new File(uploadFullPath, pureSaveFileName);
			uploadFile.transferTo(save);
			multimediaType = MultimediaType.identifyMultimediaType(save);
			makeThumbnail(uploadFullPath, save);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 파일 이름에 한글이나 공백 특수문자 등이 있으면 json parsing에 오류가 보입니다.
	 * 이에 인코딩과 디코딩을 강제적으로 개입토록 구현함.
	 * @return
	 */
	public String getJson() {
		
		pureSaveFileName = uuid + UUID_PURE_SEP + pureFileName;
		pureThumbnailFileName = THUMBNAIL_FILE_PREFIX + FileUtil.truncateExt(pureSaveFileName) + ".png";
		fileCallPath = savedFolderPath + File.separator + pureThumbnailFileName;
		originalFileCallPath = savedFolderPath + File.separator + pureSaveFileName;
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String ret = "";
		try {
			ret = URLEncoder.encode(gson.toJson(this), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static AttachFileVO fromJson(String jsonMsg) {
		Gson gson = new Gson();
		try {
			jsonMsg = URLDecoder.decode(jsonMsg, "UTF-8");
			return gson.fromJson(jsonMsg, AttachFileVO.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void makeThumbnail(File uploadPath, File uploadedFile) {
		pureThumbnailFileName = THUMBNAIL_FILE_PREFIX + FileUtil.truncateExt(pureSaveFileName) + ".png";
		File thumbnailFile = new File(uploadPath, pureThumbnailFileName);

		if (multimediaType == MultimediaType.image) {
			try {
				Thumbnailator.createThumbnail(uploadedFile, thumbnailFile, 100, 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (multimediaType == MultimediaType.video) {
			
			try {
				int frameNumber = 0;
				//Video 파일에서 첫번째 프레임의 이미지를 가지고오기
				Picture picture = FrameGrab.getFrameFromFile(uploadedFile, frameNumber);
				BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				
				FileOutputStream fileOutputStream = new FileOutputStream(thumbnailFile);
				//가져온 이미지를 썸네일로 만들기
				Thumbnailator.createThumbnail(is, fileOutputStream, 100, 100);
				fileOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
