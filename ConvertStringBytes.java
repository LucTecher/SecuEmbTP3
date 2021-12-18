package com.worldline.tvv;

public class ConvertStringBytes {

    public static byte[] asciiString2Bytes(String s) {      
    	byte b[] = new byte[s.length() / 2];
    	
    	for(int i=0 ; i<b.length ; i++) {
    		int j = i * 2;
    		b[i] = Integer.valueOf(s.substring(j, j + 2), 16).byteValue();
    		}
    	return b;
    }

    public static byte[] setAsciiString2Bytes(String s) throws Exception {
    	return asciiString2Bytes(s);
    }
    
	/**
	* @param b
	* @throws Exception
	*/
	private static String bytes2AsciiString(byte[] b) throws Exception {
		String result = "";
		
		for(int i=0 ; i<b.length ; i++){
			String s = Integer.toHexString(b[i]&(0xff));
			if(s.length()==1) s = "0" + s;
				result += s;
		}
		return result;
	}

	public static String setBytes2AsciiString(byte[] b) throws Exception {
    	return bytes2AsciiString(b);
    }
	
	/**
	* @param b
	* @throws Exception
	*/
	private static String bytes2BinaryString(byte[] b) throws Exception{
		String result = "";
		
		for(int i=0 ; i<b.length ; i++){
			String s = Integer.toBinaryString(b[i]&0xff);
			int l = s.length();
			if(l<8){          
				for(int j=0 ; j<8-l ; j++){
					s = "0" + s;
				}
			}
			result += s;
		}
		return result;
	}
	
	public static String setBytes2BinaryString(byte[] b) throws Exception {
    	return bytes2BinaryString(b);
    }
	
	public static String padString(String s){
		String pad="";
		
		if ((s.length()==0)|| s.equals("0"))
			return "0000";
		if ((s.length()%4)==0)
			return s;
		for(int i=0;i<(4-(s.length()%4));i++)
			pad=pad+"0";
		return (pad+s);
	}
}
