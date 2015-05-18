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
import android.os.Environment;
import android.provider.MediaStore;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class FileOperationsPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args,
                           CallbackContext callbackContext) throws JSONException {
        JSONObject parameters = args.getJSONObject(0);
        if ("copy".equals(action)) {
            try {
                if (parameters != null) {
                    String to = Environment.getExternalStorageDirectory() + parameters
                            .getString("to");

                    Boolean status = copy(parameters
                            .getString("from"), to);

                    JSONObject response = new JSONObject();
                    response.put("from", parameters
                            .getString("from"));
                    response.put("to", to);
                    response.put("status", status);

                    callbackContext.success(response.toString());
                }
            } catch (Exception e) {

            }

            return true;
        } else if ("delete".equals(action)) {
            if (parameters != null) {
                Boolean result = delete(parameters
                        .getString("path"));
                callbackContext.success(result + "");
            }
        }
        return false;
    }

    public Boolean delete(String path) {
        File file = getFile(path);
        file.delete();
        return true;
    }

    public Boolean copy(String from, String to) {
        try {
            File src = getFile(from);
            File dst = getFile(to);

            if (!dst.exists())
                dst.createNewFile();

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

    private File getFile(String path) {
        File file;
        // Handle the special case where you get an Android content:// uri.
        if (path.contains("content://")) {
            Cursor cursor = this.cordova.getActivity().managedQuery(Uri.parse(path), new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            // Note: MediaStore.Images/Audio/Video.Media.DATA is always "_data"
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            file = new File(cursor.getString(column_index));
        } else {
            Uri fromUri = Uri.parse(path);
            file = new File(fromUri.getEncodedPath());
        }
        return file;
    }

}

