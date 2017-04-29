package pitayaa.nail.msg.business.util.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FileHelper {
	public static void zipFile(String inputFileName, String outputFileName,
			String realFile) throws Exception {
		ZipOutputStream out = null;
		BufferedInputStream in = null;

		try {
			in = new BufferedInputStream(new FileInputStream(inputFileName));
			byte[] data = new byte[1000];
			
			File ofile=new File(outputFileName);
			
			
			File path=ofile.getParentFile();
			if(!path.exists()){
					path.mkdirs();
			}
			out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(ofile)));
			int count;
			out.putNextEntry(new ZipEntry(realFile));
			while ((count = in.read(data, 0, 1000)) != -1) {
				out.write(data, 0, count);
			}
		} catch (Exception e) {
			throw new Exception(inputFileName+"=>"+outputFileName,e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void zipFileFTP(String inputFileName, String outputFileName,
			String realFile) throws Exception {
		ZipOutputStream out = null;
		BufferedInputStream in = null;
		FTPClient ftp=null;
		try {
			 ftp = new FTPClient();
			 ftp.enterLocalPassiveMode();
			 ftp.setUseEPSVwithIPv4(true);
			 ftp.connect("103.27.237.40",21);
			 ftp.login("tomcat","1234567809");
/*			 ftp.connect("162.144.198.85",21);
			 ftp.login("ebspos","vihat@2016");
*/			 
			 
			 
			 ftp.setFileType(FTP.BINARY_FILE_TYPE);
			 File f=new File(outputFileName);
			 
			 
			
			if(outputFileName!=null && !f.getParent().equals("/")  ){
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
			out = new ZipOutputStream(new BufferedOutputStream(outftp));
			out.putNextEntry(new ZipEntry(realFile));
			
			byte[] data = new byte[1024];
			in = new BufferedInputStream(new FileInputStream(inputFileName));
			int count;

			while ((count = in.read(data)) != -1) {
				out.write(data, 0, count);
			}
			out.closeEntry();
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
					new File(inputFileName).delete();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(ftp!=null){
				ftp.completePendingCommand();
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
	public static boolean deleteFile(String filePath, String fileName,
			boolean checkExist) throws Exception {
		File orgFile = new File(filePath + File.separator + fileName);

		try {
			if (checkExist) {
				if (checkFileExist(filePath, fileName)) {
					return orgFile.delete();
				} else {
					return true;
				}
			} else {
				return orgFile.delete();
			}

		} catch (Exception ex) {
			return true;
		}
	}

	public static boolean checkFileExist(String filePath, String fileName)
			throws Exception {
		File orgFile = new File(filePath);

		if (!orgFile.exists()) {
			throw new Exception("File path '" + filePath + "' not exist.");
		}
		if (!orgFile.isDirectory()) {
			throw new Exception("File path '" + filePath + "' isn't a folder.");
		}

		for (String item : orgFile.list()) {
			if (fileName.equals(item)) {
				return true;
			}
		}

		return false;
	}
/*	public static void main(String[] args) throws Exception {
		System.out.println("1/2/3/4\\//".split("\\/").length);
		
		//zipFileFTP("/Users/quang/Desktop/KTCT.mpp", "/db/20150917/test.zip", "db");
	}*/
}
