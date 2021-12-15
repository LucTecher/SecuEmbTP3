package com.worldline.tvv;

import java.math.BigInteger;

public class TvvACompleter {


	public static void main(String[] args) {

		TvvACompleter tvv = new TvvACompleter();

		/* Test 3 */
		// PSN		:	num�ro de s�quence du PAN, 2 digits en codage BCD, par d�faut '00'. 	
		String panSequenceNumber0 = "00";
		
		// IMK_TVV	:	cl� Triple DES d'�metteur, 128 bits
		String imk_TVV0 = "9E15204313F7318ACB79B90BD986AD29";

		// PAN		:	num�ro de carte, 13 � 19 digits, codage BCD 
		String pan0 = "0123456789874";
		
		console.log(tvv.keyDerivation(imk_TVV0, pan0, panSequenceNumber0));

	}


	/**
	 * 1.3	CALCUL DU CVV
	 * @param mk : cl� Triple DES unique par carte, 128 bits.  MK = MKL || MKR
	 * @param pan : num�ro de carte, 13 � 19 digits, codage BCD
	 * @param exp : date d'expiration, 4 digits sous la forme MMYY, codage BCD 
	 * @param SC : Service Code, 3 digits, codage BCD
	 * @return
	 */
	public String getCVV(String mk, String pan, String exp, String SC) {

		// m := [PAN || EXP || SC || '0'*]	// buffer de 16 octets, 
		// padd� � droite par des quartets  � '0'


		// s�paration m = m1 || m2, deux blocs de 8 octets (64 bits)

		
		// c1 := DES(MKL)[m1] 
		// DES sur m1 avec la cl� mkL

		//String c1 = DESencrypt(mkL, m1);
		
		// c2 := TDES-EDE(MK)[c1 or m2]  	
		// hachage de m sur un bloc c2 de 8 octets 
		
		//BigInteger bigint = new BigInteger(c1, 16);
		//BigInteger bigm2 = new BigInteger(m2, 16);
		//BigInteger bigxox = bigint.xor(bigm2);
		//String xox = bigxox.toString(16);
		//String c2 = TDES(mk, xox);
		//System.out.println("Cryptogramme : " + c2);

		// c2  = q1 || q2 || � || q16		
		// d�coupage de c2 en 16 quartets q(1..16)

		
		//les quartets  0..9 sont rang�s dans D
		//les quartets  'A'..'F' sont rang�s dans H

		
		// le CVV est constitu� des 3 digits de gauche de D || H	

		return null;
	}

	/**
	 * 1.1	D�rivation de cl� d'�metteur en cl� carte
	 * @param imk_TVV
	 * @param pan
	 * @param psn
	 * @return 
	 */
	public String keyDerivation(String imk_TVV, String pan, String psn) {

		// diversifiant de 16 quartets (64 bits)
		// DIV := ['0'16 || PAN || PSN ] (-16 .. -1)

		// Concat�nation du pan et du psn


		// Padd� � gauche par des quartets � '0'
		// Prendre uniquement les 16 derniers caract�res


		// DES sur DIV avec la cl� imk_TVV
		// MKTVV_L := TDES_EDE(IMK_TVV)[DIV]
		
		//String zl = TDES(imk_TVV, div);


		// xox = [DIV or 'FF'8]
		
		//BigInteger bigDiv = new BigInteger(div, 16);
		//BigInteger bigff = new BigInteger("ffffffffffffffff", 16);
		//BigInteger bigxox = bigDiv.xor(bigff);
		//String xox = bigxox.toString(16);

		// DES sur xox avec la cl� imk_TVV
		// MKTVV_R := TDES_EDE(IMK_TVV)[DIV or 'FF'8]
		//String zr = TDES(imk_TVV, xox);

		// MK_TVV := MKTVV_L || MKTVV_R
		public String keyDerivation(String imk_TVV, String pan, String psn) {
			   String zeros = "0000000000000000";
			   String ffs = "FFFFFFFFFFFFFFFF";
			   String zeros_pan_psn = zeros + pan + psn;
			   String div = zeros_pan_psn.substring(zeros_pan_psn.length()-16, zeros_pan_psn.length());
			   System.out.println("DIV = " + div);
			   BigInteger int_div = new BigInteger(div, 16);
			   BigInteger int_ff = new BigInteger(ffs, 16);
			   BigInteger int_div_ff = int_div.xor(int_ff);
			   String string_xor_div_ff = int_div_ff.toString(16);

			   String mktvv_l = TDES(imk_TVV, div);
			   String mktvv_r = TDES(imk_TVV, string_xor_div_ff);
			   String mk_tvv = mktvv_l + mktvv_r;
			   return mk_tvv;
			};
		
	}

	/**
	 * 1.2	Calcul cryptographique du TVV
	 * @param mk : cl� Triple DES unique par carte, 128 bits
	 * @param pan : num�ro de carte, 13 � 19 digits, codage BCD
	 * @param exp : date d'expiration, 4 digits sous la forme MMYY, codage BCD 
	 * @param sc : Service Code, 3 digits, codage BCD
	 * @param ctc : compteur de ticks, 6 digits, codage BCD pour ce calcul
	 * @return
	 */
	public String getTVV(String mk, String pan, String exp, String sc,
			String ctc) {

		/* Modification TVV => CVV */



		return null;
	}

	/**
	 * DES pour le chiffrement 
	 * params : keyData : cl� de chifement de 64 bits, clearData : le msg clair
	 */
	public String DESencrypt(String keyData, String clearData) {
		DESEngine d = new DESEngine();
		byte[] key = ConvertStringBytes.asciiString2Bytes(keyData);
		byte[] clrData = ConvertStringBytes.asciiString2Bytes(clearData);

		d.init(true, key);
		int[] wkey = d.generateWorkingKey(true, key);

		byte[] encryptText = new byte[clrData.length];
		for (int i = 0; i < (clrData.length / 8); i++) {
			d.desFunc(wkey, clrData, i * 8, encryptText, i * 8);
		}
		String crypted = null;
		try {
			crypted = ConvertStringBytes.setBytes2AsciiString(encryptText);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return crypted;

	}

	/**
	 * DES pour le d�chiffrement 
	 * params : keyData : cl� de d�chifement de 64 bits, clearData : le msg clair
	 */
	public String DESdecrypt(String keyData, String encryptedData) {
		byte[] key = ConvertStringBytes.asciiString2Bytes(keyData);
		byte[] encrData = ConvertStringBytes.asciiString2Bytes(encryptedData);
		DESEngine d = new DESEngine();

		d.init(false, key);
		int[] wkey = d.generateWorkingKey(false, key);

		byte[] decryptText = new byte[encrData.length];
		for (int i = 0; i < (encrData.length / 8); i++) {
			d.desFunc(wkey, encrData, i * 8, decryptText, i * 8);
		}
		String decrypted = null;
		try {
			decrypted = ConvertStringBytes.setBytes2AsciiString(decryptText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decrypted;
	}

	/**
	 * Triple DES
	 * params : mk : cl� de chifement de 64 bits, clearData : le msg clair
	 */
	public String TDES(String mk, String clearData) {
		String mkL = mk.substring(0, 64 / 4);
		String mkR = mk.substring(64 / 4);
		clearData = DESencrypt(mkL, clearData);
		clearData = DESdecrypt(mkR, clearData);
		clearData = DESencrypt(mkL, clearData);
		return clearData;
	}

}
