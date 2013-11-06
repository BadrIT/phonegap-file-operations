/**
 * 
 * Phonegap FileOperations plugin
 * 
 * Version 1.0
 * 
 * Hazem Hagrass 2013
 *
 */

package com.badrit.FileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class FileOperationsPlugin extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if ("copy".equals(action)) {

			try {
				JSONObject parameters = args.getJSONObject(0);
				if (parameters != null) {
					Boolean result = copy(parameters
							.getString("from"), parameters
							.getString("to"));
					callbackContext.success(result + "");
				}
			} catch (Exception e) {

			}

			return true;
		}
		return false;
	}

	public Boolean copy(String from, String to) {
		try {
			File src;
			// Handle the special case where you get an Android content:// uri.
			if(from.contains("content://")){
				Cursor cursor = this.cordova.getActivity().managedQuery(Uri.parse(from), new String[] { MediaStore.Images.Media.DATA }, null, null, null);
	            // Note: MediaStore.Images/Audio/Video.Media.DATA is always "_data"
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	            src = new File(cursor.getString(column_index));
			}else{
				Uri fromUri = Uri.parse(from);
				src = new File(fromUri.getEncodedPath());
			}
			
			Uri toUri = Uri.parse(to);
			File dst = new File(toUri.getEncodedPath());
			
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String formatPath(String path){
		return path.replace("content:/", "").replace("file://", "");
	}

}

