package com.wande.ssy.utils;

import com.ynm3k.mvc.model.RespException;
import com.ynm3k.utils.crypto.DESCoding;
import com.ynm3k.utils.crypto.HexUtil;

import java.nio.ByteBuffer;

/**
 * Sid工具类
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月13日上午10:13:12
 */
public class SidUtil {
    private static final byte[] keys = "(&*%^#5@!~PgUj83Nh6o90l)".getBytes();
    private static DESCoding des = null; 
    static
    {
    	try {
    		des = new DESCoding(keys);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static class SidInfo{
    	public long uin;
    	public int skey;
    	public long time;
    	
    	public String toString(){
    		return "{uin="+uin+",skey="+skey+",time="+time+"}";
    	}
    }
    
    public static String createSid( long uin, int skey, long now ){
		ByteBuffer buf = ByteBuffer.allocate(20);
		buf.putLong( uin );
		buf.putInt( skey );
		buf.putLong( now );
		buf.flip();
		byte[] encodeBytes = des.encode(buf.array());
		return HexUtil.bytes2HexStr(encodeBytes);		//不用base64了，增强一些性能
    }
    
    public static SidInfo decodeSid( String sid  ){
    	byte[] bs = HexUtil.hexStr2Bytes( sid );
    	byte[] bs2 = des.decode( bs );
    	if( bs2.length != 20 )
    		throw new RespException(1001, "Error Sid Invalid");
    	ByteBuffer buf = ByteBuffer.wrap( bs2 );
    	SidInfo info = new SidInfo();
    	info.uin = buf.getLong();
    	info.skey = buf.getInt();
    	info.time = buf.getLong();
    	return info;
    }
}
