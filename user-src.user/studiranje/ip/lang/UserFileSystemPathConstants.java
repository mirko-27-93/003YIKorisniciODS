package studiranje.ip.lang;

import java.io.File;
import java.io.FileOutputStream;

import com.google.gson.JsonObject;

/**
 * Константе на систему датотека, које се користе за смјештање и рад са 
 * сликама. 
 * @author mirko
 * @version 1.0
 */
public final class UserFileSystemPathConstants {
	private final static boolean ERROR_REMIX = true;
	
	private final static File FS_DIR = new File("C:\\Users\\MV\\Documents\\Eclipse\\eclipse-workspace-2\\003YIKorisniciODS\\filesystem");
	static {
		try{
			if(!FS_DIR.exists()) FS_DIR.mkdirs();
		}
		catch(Exception ex){
			if(ERROR_REMIX) ex.printStackTrace();
		}
	}
	
	public final static File PROFILE_IMAGES = new File(FS_DIR, File.separator+"images"+File.separator+"profile");	
	static {
		try{
			if(!PROFILE_IMAGES.exists()) PROFILE_IMAGES.mkdirs();
		}
		catch(Exception ex){
			if(ERROR_REMIX) ex.printStackTrace();
		}
	}
	
	public final static File USER_IMAGES = new File(FS_DIR, File.separator+"images"+File.separator+"user");	
	static {
		try{
			if(!USER_IMAGES.exists()) USER_IMAGES.mkdirs();
		}
		catch(Exception ex){
			if(ERROR_REMIX) ex.printStackTrace();
		}
	}
	
	public final static File COUNTRY_FLAG_IMAGES = new File(FS_DIR, File.separator+"images"+File.separator+"country_flag");
	static {
		try{
			if(!COUNTRY_FLAG_IMAGES.exists()) COUNTRY_FLAG_IMAGES.mkdirs();
		}
		catch(Exception ex){
			if(ERROR_REMIX) ex.printStackTrace();
		}
	}
	
	public final static File INFORMATION_SCHEMA_DIR = new File(FS_DIR, File.separator+"infoschema"); 
	static {
		try{
			if(!INFORMATION_SCHEMA_DIR.exists()) INFORMATION_SCHEMA_DIR.mkdirs();
		}
		catch(Exception ex){
			if(ERROR_REMIX) ex.printStackTrace();
		}
	}
	
	public final static File PROFILE_IMAGES_INFO = new File(INFORMATION_SCHEMA_DIR, "profile.info.json"); 
	static {
		if(!PROFILE_IMAGES_INFO.exists()) {
			JsonObject info = new JsonObject();
			info.addProperty("profile.images.size", 0);
			try(FileOutputStream fos = new FileOutputStream(PROFILE_IMAGES_INFO)){
				fos.write(info.toString().getBytes("UTF-8"));
			}catch(Exception ex){
				if(ERROR_REMIX) ex.printStackTrace();
			}
		}
	}
	
	public final static File USER_IMAGES_INFO = new File(INFORMATION_SCHEMA_DIR, "user.info.json"); 
	static {
		if(!USER_IMAGES_INFO.exists()) {
			JsonObject info = new JsonObject();
			info.addProperty("user.images.size", 0);
			try(FileOutputStream fos = new FileOutputStream(USER_IMAGES_INFO)){
				fos.write(info.toString().getBytes("UTF-8"));
			}catch(Exception ex){
				if(ERROR_REMIX) ex.printStackTrace();
			}
		}
	}
	
	public final static File COUNTRY_FLAG_IMAGES_INFO = new File(INFORMATION_SCHEMA_DIR, "country_flag.info.json"); 
	static {
		if(!COUNTRY_FLAG_IMAGES_INFO.exists()) {
			JsonObject info = new JsonObject();
			info.addProperty("country_falg.images.size", 0);
			try(FileOutputStream fos = new FileOutputStream(COUNTRY_FLAG_IMAGES_INFO)){
				fos.write(info.toString().getBytes("UTF-8"));
			}catch(Exception ex){
				if(ERROR_REMIX) ex.printStackTrace();
			}
		}
	}
}
