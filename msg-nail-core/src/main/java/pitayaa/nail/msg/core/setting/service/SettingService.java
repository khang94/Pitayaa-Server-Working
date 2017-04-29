package pitayaa.nail.msg.core.setting.service;

public interface SettingService {

	void initStructureFolder();

	String getFolderStoreProperties(String propertiesName);

	void generateSubfolder(String path);

}
