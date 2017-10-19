/* NFCard is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.

NFCard is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Wget.  If not, see <http://www.gnu.org/licenses/>.

Additional permission under GNU GPL version 3 section 7 */

package com.aotu.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public final class Util {
	private final static char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private Util() {
	}

	public static byte[] toBytes(int a) {
		return new byte[] { (byte) (0x000000ff & (a >>> 24)),
				(byte) (0x000000ff & (a >>> 16)),
				(byte) (0x000000ff & (a >>> 8)), (byte) (0x000000ff & (a)) };
	}

	public static int toInt(byte[] b, int s, int n) {
		int ret = 0;

		final int e = s + n;
		for (int i = s; i < e; ++i) {
			ret <<= 8;
			ret |= b[i] & 0xFF;
		}
		return ret;
	}

	public static int toIntR(byte[] b, int s, int n) {
		int ret = 0;

		for (int i = s; (i >= 0 && n > 0); --i, --n) {
			ret <<= 8;
			ret |= b[i] & 0xFF;
		}
		return ret;
	}

	public static int toInt(byte... b) {
		int ret = 0;
		for (final byte a : b) {
			ret <<= 8;
			ret |= a & 0xFF;
		}
		return ret;
	}

	public static String toHexString(byte[] d, int s, int n) {
		final char[] ret = new char[n * 2];
		final int e = s + n;

		int x = 0;
		for (int i = s; i < e; ++i) {
			final byte v = d[i];
			ret[x++] = HEX[0x0F & (v >> 4)];
			ret[x++] = HEX[0x0F & v];
		}
		return new String(ret);
	}

	public static String toHexStringR(byte[] d, int s, int n) {
		final char[] ret = new char[n * 2];

		int x = 0;
		for (int i = s + n - 1; i >= s; --i) {
			final byte v = d[i];
			ret[x++] = HEX[0x0F & (v >> 4)];
			ret[x++] = HEX[0x0F & v];
		}
		return new String(ret);
	}

	public static int parseInt(String txt, int radix, int def) {
		int ret;
		try {
			ret = Integer.valueOf(txt, radix);
		} catch (Exception e) {
			ret = def;
		}

		return ret;
	}
	
	public static String toAmountString(float value) {
		return String.format("%.2f", value);
	}
	
	
	
	public static String getStrTime(long cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd  HH:mm");
		re_StrTime = sdf.format(new Date(cc_time));
		return re_StrTime;
	}
	
	public static String bytesToHexString(byte[] src) {
	    StringBuilder stringBuilder = new StringBuilder("0x");
	    if (src == null || src.length <= 0) {
	        return null;
	    }
	    char[] buffer = new char[2];
	    for (int i = 0; i < src.length; i++) {
	        buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
	        buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
	        System.out.println(buffer);
	        stringBuilder.append(buffer);
	    }
	    return stringBuilder.toString();
	}
	
	public static String silentInstall(String apkAbsolutePath) {  
        String[] args = { "pm", "install", "-r",apkAbsolutePath };  
        String result = "";  
        ProcessBuilder processBuilder = new ProcessBuilder(args);  
        Process process = null;  
        InputStream errIs = null;  
        InputStream inIs = null;  
        try {  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int read = -1;  
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {  
                baos.write(read);
            }
            baos.write("/n".getBytes());  
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {  
                baos.write(read);
            }
            byte[] data = baos.toByteArray();  
            result = new String(data);  
        } catch (IOException e) {  
            e.printStackTrace();
        } catch (Exception e) {  
            e.printStackTrace();
        } finally {  
            try {  
                if (errIs != null) {  
                    errIs.close();
                }
                if (inIs != null) {  
                    inIs.close();
                }
            } catch (IOException e) {  
                e.printStackTrace();
            }
            if (process != null) {  
                process.destroy();
            }
        }
        return result;  
    }
	
	  public static boolean clientInstall(String apkPath){
	        PrintWriter PrintWriter = null;
	        Process process = null;
	        try {
	            process = Runtime.getRuntime().exec("su");
	            PrintWriter = new PrintWriter(process.getOutputStream());
	            PrintWriter.println("chmod 777 "+apkPath);
	            PrintWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
	            PrintWriter.println("pm install -r "+apkPath);
//	          PrintWriter.println("exit");
	            PrintWriter.flush();
	            PrintWriter.close();
	            int value = process.waitFor();  
	            return returnResult(value);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            if(process!=null){
	                process.destroy();
	            }
	        }
	        return false;
	    }
	  
	  private static boolean returnResult(int value){
	        // 代表成功  
	        if (value == 0) {
	            return true;
	        } else if (value == 1) { // 失败
	            return false;
	        } else { // 未知情况
	            return false;
	        }  
	    }
	
	/**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean upgradeRootPermission(String pkgCodePath) {
	    Process process = null;
	    DataOutputStream os = null;
	    try {
	        String cmd="chmod 777 " + pkgCodePath;
	        process = Runtime.getRuntime().exec("su"); //切换到root帐号
	        os = new DataOutputStream(process.getOutputStream());
	        os.writeBytes(cmd + "\n");
	        os.writeBytes("exit\n");
	        os.flush();
	        process.waitFor();
	    } catch (Exception e) {
	        return false;
	    } finally {
	        try {
	            if (os != null) {
	                os.close();
	            }
	            process.destroy();
	        } catch (Exception e) {
	        }
	    }
	    return true;
	}
	
	public static boolean isServiceWorked(Context context, String serviceName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
	
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
		String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles)) return false;
		else return mobiles.matches(telRegex);
    }
	
	
}
