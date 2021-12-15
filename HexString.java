//package com.worldline.tvv;
//
///**
// * Small utility class to hexify bytes and shorts.
// * 
// */
//public class HexString {
//
//  /** Auxillary string array. */
//  protected static final String[] HEXCHARS = { "0", "1", "2", "3", "4", "5",
//      "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
//
//  /**
//   * Hex-dump a byte array (offset and printable ASCII included)
//   * <p>
//   * 
//   * @param data
//   *            Byte array to convert to HexString
//   * 
//   * @return HexString
//   */
//  public static String dump(byte[] data) {
//    return dump(data, 0, data.length);
//  }
//
//  public static String hexToASCII(String hexValue)
//  {
//      StringBuilder output = new StringBuilder("");
//      for (int i = 0; i < hexValue.length(); i += 2)
//      {
//          String str = hexValue.substring(i, i + 2);
//          output.append((char) Integer.parseInt(str, 16));
//      }
//      return output.toString();
//  }
//  
//  /**
//   * Hex-dump a byte array (offset and printable ASCII included)
//   * <p>
//   * 
//   * @param data
//   *            Byte array to convert to HexString
//   * @param offset
//   *            Start dump here
//   * @param len
//   *            Number of bytes to be dumped.
//   * @return HexString
//   */
//  public static String dump(byte[] data, int offset, int len) {
//    if (data == null)
//      return "null";
//
//    char[] ascii = new char[16];
//
//    StringBuffer out = new StringBuffer(256);
//
//    for (int i = offset; i < offset + len;) {
//      // offset
//      out.append(hexify((i >>> 8) & 0xff));
//      out.append(hexify(i & 0xff));
//      out.append(":  ");
//
//      // hexbytes
//      for (int j = 0; j < 16; j++, i++) {
//        if (i < data.length) {
//          int b = data[i] & 0xff;
//          out.append(hexify(b)).append(' ');
//          ascii[j] = (b >= 32 && b < 127) ? (char) b : '.';
//        } else {
//          out.append("   ");
//          ascii[j] = ' ';
//        }
//      }
//
//      // ASCII
//      out.append(' ').append(ascii).append('\n');
//    }
//
//    return out.toString();
//  }
//
//  /**
//   * Hexify a byte value.
//   * <p>
//   * 
//   * @param val
//   *            Byte value to be displayed as a HexString.
//   * 
//   * @return HexString
//   */
//  public static String hexify(int val) {
//    return HEXCHARS[((val & 0xff) & 0xf0) >>> 4] + HEXCHARS[val & 0x0f];
//  }
//
//  /**
//   * Hexify short value encoded in two bytes.
//   * <p>
//   * 
//   * @param a
//   *            High byte of short value to be hexified
//   * @param b
//   *            Low byte of short value to be hexified
//   * 
//   * @return HexString
//   */
//  public static String hexifyShort(byte a, byte b) {
//    return hexifyShort(a & 0xff, b & 0xff);
//  }
//
//  /**
//   * Hexify a short value.
//   * <p>
//   * 
//   * @param val
//   *            Short value to be displayed as a HexString.
//   * 
//   * @return HexString
//   */
//  public static String hexifyShort(int val) {
//    return HEXCHARS[((val & 0xffff) & 0xf000) >>> 12]
//        + HEXCHARS[((val & 0xfff) & 0xf00) >>> 8]
//        + HEXCHARS[((val & 0xff) & 0xf0) >>> 4] + HEXCHARS[val & 0x0f];
//  }
//
//  /**
//   * Hexify short value encoded in two (int-encoded)bytes.
//   * <p>
//   * 
//   * @param a
//   *            High byte of short value to be hexified
//   * @param b
//   *            Low byte of short value to be hexified
//   * 
//   * @return HexString
//   */
//  public static String hexifyShort(int a, int b) {
//    return hexifyShort(((a & 0xff) << 8) + (b & 0xff));
//  }
//
//  /**
//   * Parse bytes encoded as Hexadecimals into a byte array.
//   * <p>
//   * Si la longueur est impair, on ajoute un z?ro ? gauche.
//   * 
//   * @param byteString
//   *            String containing HexBytes.
//   * 
//   * @return byte array containing the parsed values of the given string.
//   */
//  public static byte[] parseHexString(final String byteStringIn) {
//    String byteString = byteStringIn;
//    Integer length = byteString.length();
//    if (length % 2 != 0) {
//      /* cas d'un nombre impair */
//      byteString = "0".concat(byteString);
//      length = byteString.length();
//    }
//
//    byte[] result = new byte[length / 2];
//
//    for (int i = 0; i < length; i += 2) {
//
//      final String toParse = byteString.substring(i, i + 2);
//      result[i / 2] = (byte) Integer.parseInt(toParse, 16);
//    }
//    return result;
//  }
//
//  /**
//   * Convert a byte array into its hexadecimal string representation. A space
//   * character is added in between each byte value. Example: byte [] b =
//   * {0x00,0x11,0x22,0xAA}, the returned String will be "00 11 22 AA"
//   * 
//   * @param b
//   *            the byte array to convert into a String
//   * @return The String representation of the byte array
//   */
//  public static String toHexString(final byte[] b) {
//    if (b == null)
//      return "null";
//
//    StringBuffer sb = new StringBuffer(b.length * 2);
//    for (int i = 0; i < b.length; i++) {
//      // look up high nibble char
//      sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
//
//      // look up low nibble char
//      sb.append(hexChar[b[i] & 0x0f]);
//
//      sb.append(' ');
//    }
//    return sb.toString().toUpperCase();
//  }
//  
//    /**
//     * Convert a byte array to string
//     * @param array
//     * @return
//     */
//    public static String bytes2String(final byte array[]) {
//
//      final StringBuffer buffer = new StringBuffer();
//      final int length = array.length;
//        for(int i = 0; i < length; i++) {
//          String value = Integer.toHexString(array[i]).toUpperCase();
//          if(value.length() < 2){ value = "0".concat(value); }
//          buffer.append(value.substring(value.length() - 2));
//        }
//      return buffer.toString();
//    }
//
//  /**
//   * Parse string of Hexadecimals into a byte array suitable for unsigned
//   * BigInteger computations. Reverse the order of the parsed data on the fly
//   * (input data little endian).
//   * <p>
//   * 
//   * @param byteString
//   *            String containing HexBytes.
//   * 
//   * @return byte array containing the parsed values of the given string.
//   */
//  public static byte[] parseLittleEndianHexString(String byteString) {
//    byte[] result = new byte[byteString.length() / 2 + 1];
//    for (int i = 0; i < byteString.length(); i += 2) {
//      String toParse = byteString.substring(i, i + 2);
//      result[(byteString.length() - i) / 2] = (byte) Integer.parseInt(
//          toParse, 16);
//    }
//    result[0] = (byte) 0; // just to make it a positive value
//    return result;
//  }
//
//  /**
//   * Convert a byte array into its hexadecimal string representation. No space
//   * character is added in between each byte value. Example: byte b = 0xAA,
//   * the returned String will be "AA"
//   * 
//   * @param b
//   * @return The hexadecimal representation of the byte array
//   */
//  public static String hex2String(byte b) {
//    StringBuffer sb = new StringBuffer(2);
//
//    // look up high nibble char
//    sb.append(hexChar[(b & 0xf0) >>> 4]);
//    // look up low nibble char
//    sb.append(hexChar[b & 0x0f]);
//
//    return sb.toString().toUpperCase();
//  }
//  
//  // table to convert a nibble to a hex char.
//  private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
//      '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//
//  /**
//   * Convert from hexadecimal base to decimal base.
//   * 
//   * @param hex
//   * @return The integer representation of the hexadecimal number
//   */
//  public static int hexToDec(int hex) {
//    return Integer.valueOf(Integer.toString(hex), 16).intValue();
//  }
//
//  private static String hexits = "0123456789abcdef";
//
//  /**
//   * Convert a String (hexadecimal representation of a bytes) into a byte
//   * array. Example: "112233AA", the returned byte[] will be:
//   * {0x11,0x22,0x33,0xAA}
//   * 
//   * @param s
//   *            the String to convert
//   * @return The String representation of the bytes
//   */
//  public static byte[] toStringHex(final String sIn) {
//    String s = sIn.toLowerCase();
//
//    if ((s.length() % 2) != 0) {
//      s = "0".concat(s);
//    }
//
//    byte[] b = new byte[s.length() / 2];
//    int j = 0;
//    int h;
//    int nybble = -1;
//    for (int i = 0; i < s.length(); ++i) {
//      h = hexits.indexOf(s.charAt(i));
//      if (h >= 0) {
//        if (nybble < 0) {
//          nybble = h;
//        } else {
//          b[j++] = (byte) ((nybble << 4) + h);
//          nybble = -1;
//        }
//      }
//    }
//    if (nybble >= 0) {
//      b[j++] = (byte) (nybble << 4);
//    }
//    if (j < b.length) {
//      byte[] b2 = new byte[j];
//      System.arraycopy(b, 0, b2, 0, j);
//      b = b2;
//    }
//    return b;
//  }
//
//  /**
//   * Convert an integer into a byte array. The returned byte array is 4 byte
//   * length.
//   * 
//   * @param inCode
//   * @return A byte array
//   */
//  public static byte[] int2ByteArray(int inCode) {
//    byte[] byteArrayVal = new byte[4];
//
//    byteArrayVal[0] = (byte) ((inCode & 0xFF000000) >> 24);
//    byteArrayVal[1] = (byte) ((inCode & 0x00FF0000) >> 16);
//    byteArrayVal[2] = (byte) ((inCode & 0x0000FF00) >> 8);
//    byteArrayVal[3] = (byte) ((inCode & 0x000000FF));
//
//    return byteArrayVal;
//  }
//
////  /**
////   * Convert an integer into its hexadecimal String representation. Example:
////   * in = 0xAA11EECC, the returned String will be "AA11EECC"
////   * 
////   * @param in
////   * @return The String version of the integer
////   */
////  public static String int2HexString(int in) {
////    return bytes2String(int2ByteArray(in));
////  }
//
//  /**
//   * Get the unsigned value of a byte.
//   * 
//   * @param b
//   * @return An unsigned integer of the value
//   */
//  public static int getUnsignedValue(byte b) {
//    return (int) (b & 0x00FF);
//  }
//
//  public static final long byteArrayToUnsignedLong(byte[] b) {
//    long result = 0;
//    for (byte value : b) {
//      result <<= 8; // On d?cale de 8 bits vers la gauche
//      result += value & 0xff;
//    }
//    return result;
//  }
//}
