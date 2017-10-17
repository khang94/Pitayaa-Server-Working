package pitayaa.nail.msg.core.file.service;


import pitayaa.nail.domain.enumtype.ImageType;
import pitayaa.nail.domain.view.View;

public interface FileUpload {
	public String saveFile(View view) throws Exception;
}
