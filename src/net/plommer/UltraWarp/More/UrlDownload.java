package net.plommer.UltraWarp.More;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import net.plommer.UltraWarp.UltraWarp;

public class UrlDownload {

	public UrlDownload(String url, UltraWarp plugin, String name) {
		try {
			InputStream is = new URL(url).openStream();
			File finaldest = new File(plugin.getDataFolder().getAbsolutePath() + "/" + name);
			finaldest.getParentFile().mkdir();
			finaldest.createNewFile();
			OutputStream os = new FileOutputStream(finaldest);
            byte data[] = new byte[1024];
            int count;
            while ((count = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, count);
            }
            os.flush();
            is.close();
            os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
	
}
