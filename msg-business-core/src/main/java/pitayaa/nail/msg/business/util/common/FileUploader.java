package pitayaa.nail.msg.business.util.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import pitayaa.nail.msg.business.exception.InvalidateFileTypeException;


public class FileUploader {
	public static Boolean uploadFile(String realPath, String fileName,
			byte[] fileData, String validateMimeType) throws Exception {
		Boolean result = false;
		File temp = null;

		try {
			if (!FileUtility.isFolderExist(realPath)) {
				FileUtility.createDirectory(realPath);
			}

			temp = new File(realPath + fileName);

			if(!temp.exists()){
				temp.createNewFile();
			}
			
			FileOutputStream fout = null;
			
			try {
				fout = new FileOutputStream(temp);
				fout.write(fileData);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
	
			} finally {
				if (fout != null) {
					try {
						fout.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			String mimeType = FileUtility.getMime(temp);
			if (validateMimeType.indexOf(mimeType) < 0) {
				throw new InvalidateFileTypeException(String.format("File mime %s is invalidate", mimeType));
			}

			result = true;
		} catch (InvalidateFileTypeException ex) {
			if (temp != null) {
				try {
					temp.delete();
				} catch (Exception exx) {
					exx.printStackTrace();
				}
			}
			
			throw ex;
		} catch (Exception ex) {
			if (temp != null) {
				try {
					temp.delete();
				} catch (Exception exx) {
					exx.printStackTrace();
				}
			}
			throw ex;
		}

		return result;
	}
	
	//TEST LOCAL
	public static Boolean uploadFTP(String realPath, String fileName,
			byte[] fileData, String validateMimeType) throws Exception {
		Boolean result = true;
		File f=new File(realPath+fileName);
		
		
		
		
		FTPClient ftp=null;
		try {
			 ftp = new FTPClient();
			 ftp.enterLocalPassiveMode();
			 ftp.setUseEPSVwithIPv4(true);
			 ftp.connect("103.27.237.40",21);
			 ftp.login("tomcat","1234567809");
/*			 ftp.connect("162.144.198.85",21);
			 ftp.login("ebspos","@vihat2016");
*/			 ftp.setFileType(FTP.BINARY_FILE_TYPE);
			 
			if(fileName!=null && !f.getParent().equals("/")  ){
				String path=f.getParent().replace("\\", "/");
				String[] dirs=path.split("\\/");
				for(String dir:dirs){
					if(!dir.isEmpty() && !ftp.changeWorkingDirectory(dir)){
						ftp.makeDirectory(dir);
						ftp.changeWorkingDirectory(dir); 
					}
				} 
			 }
			
			OutputStream outftp = ftp.storeFileStream(f.getName());
			outftp.write(fileData);
			outftp.close();
		} catch (Exception e) {
			throw e;
		} finally {
			
			
			if(ftp!=null){
				ftp.completePendingCommand();
				ftp.logout();
				ftp.disconnect();
			}
		}
		

		

			

		return result;
	}
	

	public static Boolean uploadImageAndCreateThumbnail(String realPath,
			String fileName, String thumbFileName, byte[] imgData,
			String validateMimeType) throws InvalidateFileTypeException,
			Exception {
		Boolean result = false;
		File imageFile = null;

		try {
			if (!FileUtility.isFolderExist(realPath)) {
				FileUtility.createDirectory(realPath);
			}

			imageFile = new File(realPath + fileName);
			FileOutputStream fout = null;

			try {
				fout = new FileOutputStream(imageFile);
				fout.write(imgData);
			} catch (Exception ex) {
				throw ex;
			} finally {
				if (fout != null) {
					try {
						fout.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			String mimeType = FileUtility.getMime(imageFile);
			if (validateMimeType.indexOf(mimeType) < 0) {
				throw new InvalidateFileTypeException(String.format(
						"File mime %s is invalidate. ", mimeType));
			}

			result = ImageUtility.createImageThumbnail(imageFile.getParent()
					+ File.separator + fileName, imageFile.getParent()
					+ File.separator + thumbFileName,
					ImageUtility.IMAGE_THUMB_WIDTH,
					ImageUtility.IMAGE_THUMB_HEIGHT);
		} catch (InvalidateFileTypeException ex) {
			if (imageFile != null) {
				try {
					imageFile.delete();
				} catch (Exception exx) {
					exx.printStackTrace();
				}
			}

			throw ex;
		} catch (Exception ex) {
			if (imageFile != null) {
				try {
					imageFile.delete();
				} catch (Exception exx) {
					exx.printStackTrace();
				}
			}

			throw ex;
		}

		return result;
	}
	
/*	public static void main(String[] args) {
//		FTPClient ftp=null;
//		Boolean result = true;
//		File f=new File("/ebspos/service.war");
//		try {
//			 ftp = new FTPClient();
//			 ftp.enterLocalPassiveMode();
//			 ftp.setUseEPSVwithIPv4(true);
//			 ftp.connect("162.144.198.85",21);
//			 ftp.login("ebspos","@vihat2016");
//			 ftp.setFileType(FTP.BINARY_FILE_TYPE);
//			 
//			if(fileName!=null && !f.getParent().equals("/")  ){
//				String path=f.getParent().replace("\\", "/");
//				String[] dirs=path.split("\\/");
//				for(String dir:dirs){
//					if(!dir.isEmpty() && !ftp.changeWorkingDirectory(dir)){
//						ftp.makeDirectory(dir);
//						ftp.changeWorkingDirectory(dir); 
//					}
//				} 
//			 }
//			
//			
//			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File ("D:/ebspos/service.war")));
//			
//			
//			OutputStream outftp = ftp.storeFileStream(f.getName());
//			outftp.write(fileData);
//			outftp.close();
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			
//			
//			if(ftp!=null){
//				ftp.completePendingCommand();
//				ftp.logout();
//				ftp.disconnect();
//			}
//		}
	}*/
}