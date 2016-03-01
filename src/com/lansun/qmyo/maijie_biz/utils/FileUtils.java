package com.lansun.qmyo.maijie_biz.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.util.EncodingUtils;
import org.w3c.dom.Document;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.lansun.qmyo.maijie_biz.dirmanager.DirType;
import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class FileUtils {
	private static final String TAG = "FileUtils";
	
	/**
	 * 创建空文件
	 * @param path 待创建的文件路径
	 * @param size	空文件大小
	 * @return	创建是否成功
	 * @throws IOException
	 */
	public static boolean createEmptyFile(String path, long size) throws IOException{
	    File file = new File(path);
	    File parent = file.getParentFile();
	    parent.mkdirs();	    
    	RandomAccessFile raf = null;    	   	
		raf = new RandomAccessFile(file, "rw");
		raf.setLength(size);
		raf.close();		
    	return true;
    }
	
	public static String getFilePathByUrl(String url, String rootPath){
		String path = null;
		if (!TextUtils.isEmpty(url)) {
			path = rootPath + File.separator + String.valueOf(url.hashCode());
		}
		
		FrameLog.d(TAG, path);
		return path;
	}
	
	/**
	 * 删除文件或者目录
	 * @param path 指定路径的文件或目录
	 * @return 返回操作结果
	 */
	public static boolean deleteFile(String path) {
		if(TextUtils.isEmpty(path))
			return true;
		
		File file = new File(path);
		if (!file.exists())
			return true;
		
		if (file.isDirectory()) {
			String[] subPaths = file.list();
			for (int i = 0; i < subPaths.length; i++) {
				if (!deleteFile(path)) {
					return false;
				}
			}
		} 
		FrameLog.d(TAG, "deleteFile: " + path);
		return file.delete();
	}
	/**
	 * 创建目录，包括必要的父目录的创建，如果未创建
	 * @param path 待创建的目录路径
	 * @return 返回操作结果
	 */
	public static boolean mkdir(String path) {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return true;
		}
		
		file.mkdirs();
		return true;
	}
	
	/**
	 * 创建文件，包括必要的父目录的创建，如果未创建
	 * @param file 待创建的文件
	 * @return 返回操作结果
	 * @throws IOException 创建失败，将抛出该异常
	 */
	public static boolean create(File file) throws IOException {
		try {
			if (file.exists()) {
				return true;
			}

			File parent = file.getParentFile();
			parent.mkdirs();

			return file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * 获取指定文件的xml Document 对象
	 * 
	 * @param filePath 文件路径
	 * @return Document对象
	 */
	public static Document getXmlDocument(String filePath) throws Exception {
		return getXmlDocument(new File(filePath));
	}
	/**
	 * 获取指定文件的xml Document 对象
	 * 
	 * @param file 文件句柄 
	 * @return Document对象
	 * @throws ParserConfigurationException 
	 */
	public static Document getXmlDocument(File file) throws Exception {
		DocumentBuilderFactory docBuilderFactory = null;
		DocumentBuilder db = null;
		Document document = null;
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		db = docBuilderFactory.newDocumentBuilder();
		
		if (file.exists()) {			
			FileInputStream in = new FileInputStream(file);
			document = db.parse(in);
			in.close();			
		}
		db = null;
		docBuilderFactory = null;
		return document;
	}
	
	/**
	 * 检查当前sdcard剩余空间大小
	 */
	public static long getAvailableExternalMemorySize() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			long availableSize = availableBlocks * blockSize;
			return availableSize - 5 * 1024 * 1024;//预留5M的空间
		}
		return -1;
	}
	
	/**
     * 复制文件
     * @param srcPath 源文件路径
     * @param destPath 目标文件路径
     * @return 返回操作结果
     */
	public static boolean copyFile(String srcPath, String destPath) {
	    return copyFile(new File(srcPath), new File(destPath));
	}
	
	/**
     * 复制文件
     * @param src 源文件
     * @param dest 目标文件
     * @return 返回操作结果
	 * @throws FileNotFoundException 
     */
	public static boolean copyFile(File src, File dest) {
	    if (!src.exists())
	        return false;
	    
	    FileInputStream fis = null;
	    FileOutputStream fos = null;
	    try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            
            byte[] buffer = new byte[2048];
            int bytesread = 0;
            while ((bytesread = fis.read(buffer)) != -1) {
                if (bytesread > 0) 
                    fos.write(buffer, 0, bytesread);
            }
            
            return true;
            
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e2) {
                }
            }
            
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e2) {
                }
            }
        }	    
	}
	
	/**
	 * 移动文件
	 * @param srcPath 源文件路径
	 * @param destPath 目标文件路径
	 * @return 返回操作结果
	 */
	public static boolean moveFile(String srcPath, String destPath) {	  
	    File src = new File(srcPath);
	    File dest = new File(destPath);
	    
	    boolean ret = copyFile(src, dest);
	    if (ret) {
	        deleteFile(srcPath);
	    }
	    
	    return ret;
	}
	
	/**
     * 移动文件
     * @param src 源文件
     * @param dest 目标文件
     * @return 返回操作结果
     */
	public static boolean moveFile(File src, File dest) {
	    boolean ret = copyFile(src, dest);
        if (ret) {
            ret = deleteFile(src.getAbsolutePath());
        }
        
        return ret;
	}
	
	public static File writeBytesToFile(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			if(file.exists())
				file.delete();
			
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
					stream = null;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}
	
	
	public static String readFile(String fileName) {
		File file = new File(fileName);
		return readFile(file);
	}
	
	public static String readFile(File file){
		String res = null;
		FileInputStream fis = null;
		int length = 0;
		byte[] buffer = null;
		
		try {
			fis = new FileInputStream(file);
			length = fis.available();
			buffer = new byte[length];
			fis.read(buffer);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		res = EncodingUtils.getString(buffer, "UTF-8");
		return res;
	}
	
	
	/**
	 * 获取文件的类型
	 * 
	 * @param file
	 *            文件路径
	 * @return
	 */
	public static String getFileType(String file) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (inputStream == null) {
			return null;
		}
		byte[] buffer = new byte[2];
		// 文件类型代码
		String filecode = "";
		// 文件类型
		String fileType = "";
		// 通过读取出来的前两个字节来判断文件类型
		try {
			if (inputStream.read(buffer) != -1) {
				for (int i = 0; i < buffer.length; i++) {
					// 获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数
					// 并转换成字符串
					filecode += Integer.toString((buffer[i] & 0xFF));
				}
				// 把字符串再转换成Integer进行类型判断
				switch (Integer.parseInt(filecode)) {
				case 7790:
					fileType = "exe";
					break;
				case 7784:
					fileType = "midi";
					break;
				case 8297:
					fileType = "rar";
					break;
				case 8075:
					fileType = "zip";
					break;
				case 255216:
					fileType = "jpg";
					break;
				case 7173:
					fileType = "gif";
					break;
				case 6677:
					fileType = "bmp";
					break;
				case 13780:
					fileType = "png";
					break;
				default:
					fileType = "unknown type: " + filecode;
				}
			}
		} catch (Exception e) {
			return fileType;
		}
		return fileType;
	}
	
	/**
	 * 通过使用自带的文件管理器选中文件，解析它的路径
	 * 
	 * @param mContext
	 * @param uri
	 * @return
	 * @throws URISyntaxException
	 */
	public static String getPath(Context mContext, Uri uri)
			throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = mContext.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}
	
	/**
	 * 检查文件是否过期 <br />
	 * 若文件不存在，则直接返回true <br />
	 * time指定过期的秒数
	 * 
	 * @param context
	 * @param file
	 * @param time
	 *            单位：秒
	 * @return
	 */
	public static boolean expire(String file, int time) {
		if (!exists(file)) {
			return true;
		}
		File f = new File(file);
		if (Calendar.getInstance().getTimeInMillis() - f.lastModified() > time * 1000) {
			FrameLog.d("file", "file expired");
			return true;
		}
		return false;
	}
	
	/**
	 * 根据文件判断该文件是否存在
	 * 
	 * @param context
	 * @param file
	 * @return
	 */
	public static boolean exists(String file) {
		return new File(file).exists();
	}
	
	public static int getFileSize(String path) {
		if (!exists(path)) {
			return -1;
		}
		File f = new File(path);
		return (int) f.length();
	}
	
	public static void saveBitmapToFile(String path, Bitmap bmp) {
		File f = new File(path);
		
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			
			fOut.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
//	public static String getOutputMediaFilePath(String name) {
//		return AppContext.getDirectoryManager().getDirPath(DirType.VEDIO) + File.separator + name + ".mp4";
//	}
	public static Uri getOutputPicUri(String name) {
		String fullPath = AppContext.getDirManager().getDirPath(DirType.CACHE) + File.separator + name + ".jpg";
		
		return Uri.fromFile(new File(fullPath));
	}
	
//	public static Uri getOutputVideoUri(String name) {
//		String fullPath = AppContext.getDirectoryManager().getDirPath(DirType.VEDIO) + File.separator + name + ".mp4";
//		
//		return Uri.fromFile(new File(fullPath));
//	}
	public static String getPicPath(String title) {
		return AppContext.getDirManager().getDirPath(DirType.CACHE) + File.separator + title + ".jpg";
	}
	
	public static String getAudioPath(String title) {
		return AppContext.getDirManager().getDirPath(DirType.CACHE) + File.separator + title + ".3gpp";
	}
	
	public static String getVideoPath(String title) {
		return AppContext.getDirManager().getDirPath(DirType.CACHE) + File.separator + title + ".mp4";
	}
	
	public static boolean reNameFile(String srcPath, String destPath) {
		File srcFile = new File(srcPath);
		if(srcFile.exists()) {
			return srcFile.renameTo(new File(destPath));
		}
		return false;
	}
	
	
	
}
