package com.worldline.tvv;

import java.math.BigInteger;

public class TvvACompleter {


	public static void main(String[] args) {

		TvvACompleter tvv = new TvvACompleter();

		// 1.1 - Dérivation des clés :
		String[] IMK_TVV_1_1 = {"9E15204313F7318ACB79B90BD986AD29",
							"036BD4728609D4100A18FE3F99B6589F"};
		String[] PAN_1_1 = {"0123456789874", "11234567898764", "212345678987655", "3123456789876547", "5364140000000367",
						"01234567898765437", "012345678987654326", "9123456789876543214"};
		String[] PSN_1_1 = {"00", "12"};
		String[] EXP_1_1 = {"0215", "0216", "0316", "1220"};
		String[] SC_1_1 = {"000", "123", "755", "999"};
		String[] vecteurs_1_1 = {"000", "001", "010", "011", "020", "021", "030", "031", "040", "041", "050",
										   "051", "060", "061", "070", "071",
										   "100", "101", "110", "111", "120", "121", "130", "131", "140", "141", "150",
										   "151", "160", "161", "170", "171"};

		System.out.println("   ----------------- Résultats clé de dérivation -----------------");
		System.out.println("  | Vecteur |   Diversifiant   |              MK_TVV              |");
		for(String v: vecteurs_1_1) {
			System.out.print("  |   " + v + "   |");
			String imk_TVV = IMK_TVV_1_1[v.charAt(0) - 48];
			String pan = PAN_1_1[v.charAt(1) - 48];
			String psn = PSN_1_1[v.charAt(2) - 48];
			String mk_tvv = tvv.keyDerivation(imk_TVV, pan, psn, "Q1.1");
			System.out.println(mk_tvv + " |");
		}
		System.out.println("   ---------------------------------------------------------------\n");

		// 1.1 - FIN !

		String IMK_TVV_1_2 = "9E15204313F7318ACB79B90BD986AD29";
		String EXP_1_2 = "1216";
		String[] SC_1_2 = {"000", "612"};
		String PSN_1_2 = "00";
		String[] PAN_1_2 = {"1234567890123", "12345678901234", "123456789012345", "1234567890123456",
				"12345678901234567", "123456789012345678", "1234567890123456789"};

		// 1.2.1 - Calcul du TVV
		String[] CTC_1_2_1 = {"020040", "363945"};
		String[] vecteurs_1_2_1 = {"030", "031", "130", "131", "060", "061", "160", "161", "090", "091", "190", "191"};

		String mk_tvv = "";
		String TVV = "";

		System.out.println("\n  ------------------------ Tableau de vérification --------------------------");
		System.out.println(" | Profil - CTC |         Donnée à chiffrer         |   Cryptogramme   | TVV |");
		for(String v: vecteurs_1_2_1) {
			String SC = SC_1_2[v.charAt(0) - 48];
			String PAN = PAN_1_2[v.charAt(1) - 3 - 48];
			String CTC = CTC_1_2_1[v.charAt(2) - 48];
			System.out.print(" | P"+v.substring(0,2) + " - " + CTC + " | ");
			mk_tvv = tvv.keyDerivation(IMK_TVV_1_2, PAN, PSN_1_2, "Q1.2.1");
			TVV = tvv.getTVV(mk_tvv, PAN, EXP_1_2, SC, CTC, "Q1.2.1");
			System.out.println(TVV + " |");
		}
		System.out.println("  ---------------------------------------------------------------------------\n");
		// 1.2.1 - FIN !

		/*
		// Test unitaire
		mk_tvv = tvv.keyDerivation(IMK_TVV_1_2, "123456789012345", PSN_1_2, "");
		System.out.println("P15 - 589611 => " + tvv.getTVV(mk_tvv, "123456789012345", EXP_1_2, "612",
				"589611", ""));
		System.out.println();
		*/

		// 1.2.2
		String[] CTC_1_2_2 = {"000000", "100000", "020000", "003000", "000400", "000050", "000006", "123456", "234561",
				"363945", "589611", "999999"};
		System.out.println("\n  -------------------------------------- Tableau des TVV -----------------------------" +
				"-----------");
		System.out.println(" | CTC / Pij | P03 | P04 | P05 | P06 | P07 | P08 | P09 |" +
				" P13 | P14 | P15 | P16 | P17 | P18 | P19 |");
		for(String ctc: CTC_1_2_2){
			System.out.print(" |   " + ctc + "  | ");
			for(String sc: SC_1_2){
				for(String pan: PAN_1_2){
					mk_tvv = tvv.keyDerivation(IMK_TVV_1_2, pan, PSN_1_2, "Q1.2.2");
					TVV = tvv.getTVV(mk_tvv, pan, EXP_1_2, sc, ctc, "Q1.2.2");
					System.out.print(TVV + " | ");
					// System.out.println(verbose == "Q1.2.1");
				}
			}
			System.out.println();
		}
		System.out.println("  ----------------------------------------------------------------------------------" +
				"-------------\n");
	}


	/**
	 * 1.3	CALCUL DU CVV
	 * @param mk : clé Triple DES unique par carte, 128 bits.  MK = MKL || MKR
	 * @param pan : numéro de carte, 13 à 19 digits, codage BCD
	 * @param exp : date d'expiration, 4 digits sous la forme MMYY, codage BCD 
	 * @param SC : Service Code, 3 digits, codage BCD
	 * @param verbose : "Q1.1" pour activer l'affichage pour la Q1.1 ("Q1.2.1" pour la Q1.2.1 etc...),
	 *                  "DEBUG" pour tout afficher
	 * @return
	 */
	public String getCVV(String mk, String pan, String exp, String SC, String verbose) {
		if (verbose == "DEBUG"){ System.out.println("Get CVV ..."); }

		// m := [PAN || EXP || SC || '0'*]	// buffer de 16 octets, 
		// paddé à droite par des quartets à '0'
		String padding = "00000000000000000000000000000000";
		String m = pan + exp + SC + padding;
		m = m.substring(0, 32);

		if (verbose == "DEBUG") {
			System.out.println("m  = " + pan + exp + SC + padding);
			System.out.println("m  = " + m);
		}

		// Séparation m = m1 || m2, deux blocs de 8 octets (64 bits)
		String m1 = m.substring(0, 16);
		String m2 = m.substring(16);

		if (verbose == "DEBUG") {
			System.out.println("m1 = " + m1);
			System.out.println("m2 = " + m2);
		}

		// c1 := DES(MKL)[m1] 
		// DES sur m1 avec la clé mkL

		String mkL = mk.substring(0, 16);
		String c1 = DESencrypt(mkL, m1);

		if (verbose == "DEBUG") {
			System.out.println("mk  = " + mk);
			System.out.println("mkL = " + mkL);
			System.out.println("c1  = " + c1);
		}
		
		// c2 := TDES-EDE(MK)[c1 or m2]  	
		// hachage de m sur un bloc c2 de 8 octets 
		
		BigInteger bigint = new BigInteger(c1, 16);
		BigInteger bigm2 = new BigInteger(m2, 16);
		BigInteger bigxox = bigint.xor(bigm2);
		String xox = padding + bigxox.toString(16);
		xox = xox.substring(xox.length()-16);
		if (verbose == "DEBUG") { System.out.println("xox = " + xox); }
		String c2 = TDES(mk, xox);

		if (verbose == "DEBUG") { System.out.println("Cryptogramme : " + c2); }

		if (verbose == "Q1.2.1") {
			// "Donnée à chiffrer | Cryptogramme"
			System.out.print(" " + m + " | " + c2 + " | ");
		}

		// c2  = q1 || q2 || ... || q16
		// Découpage de c2 en 16 quartets q(1..16)
		// les quartets  0..9 sont rangés dans D
		// les quartets  'A'..'F' sont rangés dans H

		String D[] = new String[16];
		String H[] = new String[16];
		int i,j;
		for(i = 0, j = 0; i+j < 16;) {
			String quartet = c2.substring(i + j, i + j + 1);
			byte byte_value[] = HexString.parseHexString(quartet);
			if (byte_value[0] <= 0x09) {
				D[i] = quartet;
				i++;
			} else {
				H[j] = quartet;
				j++;
			}
		}

		if (verbose == "DEBUG") {
			System.out.print("D = ");
			for (String s: D){
				System.out.print(s + ", ");
			}
			System.out.print("\nH = ");
			for (String s: H){
				System.out.print(s + ", ");
			}
			System.out.print("\n");
			System.out.println("i = " + i);
			System.out.println("j = " + j);
		}

		// le CVV est constitué des 3 digits de gauche de D || H
		String CVV = "";
		for(int k = 0; k < 3; k++){
			if (k < i) {
				CVV += D[k];
			}
			else {
				CVV += H[k-i];
			}
		}

		if (verbose == "DEBUG") { System.out.println("CVV = " + CVV); }

		return CVV;
	}

	/**
	 * 1.1	Dérivation de clé d'émetteur en clé carte
	 * @param imk_TVV
	 * @param pan
	 * @param psn
	 * @param verbose : "Q1.1" pour activer l'affichage pour la Q1.1 ("Q1.2.1" pour la Q1.2.1 etc...),
	 *                  "DEBUG" pour tout afficher
	 * @return 
	 */
	public String keyDerivation(String imk_TVV, String pan, String psn, String verbose) {
		if (verbose == "DEBUG") { System.out.println("Key derivation ..."); }
		String zeros = "0000000000000000";
		String ffs = "FFFFFFFFFFFFFFFF";
		String zeros_pan_psn = zeros + pan + psn;
		String div = zeros_pan_psn.substring(zeros_pan_psn.length()-16);
		if(verbose == "DEBUG" || verbose == "Q1.1") { System.out.print(" " + div + " | "); }
		BigInteger int_div = new BigInteger(div, 16);
		BigInteger int_ff = new BigInteger(ffs, 16);
		BigInteger int_div_ff = int_div.xor(int_ff);
		String string_xor_div_ff = int_div_ff.toString(16);

		String mktvv_l = TDES(imk_TVV, div);
		String mktvv_r = TDES(imk_TVV, string_xor_div_ff);
		String mk_tvv = mktvv_l + mktvv_r;
		if (verbose == "DEBUG") { System.out.println("mk_tvv = " + mk_tvv); }
		return mk_tvv;
	}

	/**
	 * 1.2	Calcul cryptographique du TVV
	 * @param mk : clé Triple DES unique par carte, 128 bits
	 * @param pan : numéro de carte, 13 à 19 digits, codage BCD
	 * @param exp : date d'expiration, 4 digits sous la forme MMYY, codage BCD 
	 * @param sc : Service Code, 3 digits, codage BCD
	 * @param ctc : compteur de ticks, 6 digits, codage BCD pour ce calcul
	 * @param verbose : "Q1.1" pour activer l'affichage pour la Q1.1 ("Q1.2.1" pour la Q1.2.1 etc...),
	 *                  "DEBUG" pour tout afficher
	 * @return
	 */
	public String getTVV(String mk, String pan, String exp, String sc,
			String ctc, String verbose) {
		if (verbose == "DEBUG") { System.out.println("Get TVV ..."); }

		/* Modification TVV => CVV */

		// Écrasement des 4 premiers digit du PAN par les 4 derniers digit du CTC
		String pan_prime = ctc.substring(ctc.length() - 4) + pan.substring(4);
		// Écrasement des 2 derniers digits du SC par les 2 premiers digits du CTC.
		String sc_prime = sc.substring(0, sc.length()-2) + ctc.substring(0,2);

		if (verbose == "DEBUG") {
			System.out.println("CTC = " + ctc);
			System.out.println("PAN = " + pan);
			System.out.println("SC  = " + sc);
			System.out.println("pan_prime = " + pan_prime);
			System.out.println("sc_prime  = " + sc_prime);
		}

		String TVV = getCVV(mk, pan_prime, exp, sc_prime, verbose);

		return TVV;
	}

	/**
	 * DES pour le chiffrement 
	 * params : keyData : clé de chifement de 64 bits, clearData : le msg clair
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
	 * DES pour le déchiffrement
	 * params : keyData : clé de déchifement de 64 bits, clearData : le msg clair
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
	 * params : mk : clé de chifement de 64 bits, clearData : le msg clair
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
