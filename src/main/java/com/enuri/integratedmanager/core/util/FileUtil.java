package com.enuri.integratedmanager.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : 파일에 대한 기능 모음
  * @Since : 2020. 9. 9
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
public class FileUtil {

	// 디렉토리 생성
	public static void mkdir(String path) {
		File file = new File(path); // 폴더 생성을 위한 객체 하나 더 생성

		if (!file.exists()) {
			file.mkdirs(); // 폴더 생성
		}
	}

	@SuppressWarnings("resource")
	public static boolean copyFileFileChannel(String src, String dest) throws IOException {
		boolean ret = true;

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(new File(src)).getChannel();
			destination = new FileOutputStream(new File(dest)).getChannel();

			// This fails with Map Failed exception on large files
			// destination.transferFrom(source, 0, source.size());

			ByteBuffer buf = ByteBuffer.allocateDirect(1024 * 8);
			while ((source.read(buf)) != -1) {
				buf.flip();
				destination.write(buf);
				buf.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
		return ret;
	}

	// windows에서는 이미 쓰고있는 파일 lock처리 되기에 IOException 날수있다.
	public static boolean copyFileBufferedReader(String src, String dest) throws IOException {
		boolean result = false;
		BufferedInputStream source = new BufferedInputStream(new FileInputStream(new File(src)));
		BufferedOutputStream destination = new BufferedOutputStream(new FileOutputStream(new File(dest)));
		byte[] buffer = new byte[1024 * 8];

		try {
			int n = 0;
			while (-1 != (n = source.read(buffer))) {
				destination.write(buffer, 0, n);
			}
			destination.flush();
			result = true;
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}

		return result;
	}

	public static void oldFileDelete(String path, int cutDay) {
		File directory = new File(path);

		if (directory.exists()) {
			File[] listFiles = directory.listFiles();
			long purgeTime = System.currentTimeMillis() - (cutDay * 24 * 60 * 60 * 1000);

			for (File file : listFiles) {
				if (file.lastModified() < purgeTime) {
					if (!file.delete()) {
						log.error("delete fail: {} ", file);
					}
				}
			}
		}
	}
}
