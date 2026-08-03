package com.ait.web.util;

public class GetPassword {

   
    public static String getEncodePasswd(String newPasswd) throws Exception {

        String sEncode = "";
        char[] lc_2 = new char[10];
        char[] li_3 = new char[10];

        li_3[0] = 9;
        li_3[1] = 5;
        li_3[2] = 1;
        li_3[3] = 0;
        li_3[4] = 9;
        li_3[5] = 5;
        li_3[6] = 3;
        li_3[7] = 8;
        li_3[8] = 7;
        li_3[9] = 1;

        char[] charTemp = newPasswd.toCharArray();

        for (int i = 0; i < charTemp.length; i++) {

            if (i == 0 || i == charTemp.length - 1) {

                charTemp[i] = (char) ((int) charTemp[i] + 1);

            } else {
                charTemp[i] = (char) ((int) charTemp[i] - li_3[i]);
            }
            lc_2[i] = charTemp[i];

        }
        sEncode = new String(lc_2);
        return sEncode.trim();
    }

    
    public static String getDecodePasswd(String password) throws Exception {

        String sEncode = "";
        char[] lc_2 = new char[10];
        char[] li_3 = new char[10];

        li_3[0] = 9;
        li_3[1] = 5;
        li_3[2] = 1;
        li_3[3] = 0;
        li_3[4] = 9;
        li_3[5] = 5;
        li_3[6] = 3;
        li_3[7] = 8;
        li_3[8] = 7;
        li_3[9] = 1;

        char[] charTemp = password.toCharArray();

        for (int i = 0; i < charTemp.length; i++) {

            if (i == 0 || i == charTemp.length - 1) {

                charTemp[i] = (char) ((int) charTemp[i] - 1);

            } else {
                charTemp[i] = (char) ((int) charTemp[i] + li_3[i]);
            }
            lc_2[i] = charTemp[i];

        }
        sEncode = new String(lc_2);

        return sEncode.trim();
    }


    public static String getDecodePasswdErp(String password) throws Exception {

        String sEncode = "";
        char[] lc_2 = new char[16];
        char[] li_3 = new char[16];

        li_3[0] = 3;
        li_3[1] = 5;
        li_3[2] = 1;
        li_3[3] = 0;
        li_3[4] = 9;
        li_3[5] = 5;
        li_3[6] = 3;
        li_3[7] = 8;
        li_3[8] = 7;
        li_3[9] = 1;
        li_3[10] = 4;
        li_3[11] = 9;
        li_3[12] = 8;
        li_3[13] = 3;
        li_3[14] = 5;
        li_3[15] = 7;
        char[] charTemp = password.toCharArray();
        for (int i = 0; i < charTemp.length; i++) {
            if (i == 0 || i == charTemp.length - 1) {
                if (((int) charTemp[i]) == 32) {
                    charTemp[i] = (char) (43 - 1);
                } else {
                    charTemp[i] = (char) ((int) charTemp[i] - 1);
                }
            } else {
                if (((int) charTemp[i]) == 32) {
                    charTemp[i] = (char) (43 + li_3[i]);
                } else {
                    charTemp[i] = (char) ((int) charTemp[i] + li_3[i]);
                }
            }
            lc_2[i] = charTemp[i];
        }
        sEncode = new String(lc_2);
        return sEncode.trim();
    }

    public static void main(String[] args) {
        try {
            String a = GetPassword.getEncodePasswd("CH258402");
            String b = GetPassword.getDecodePasswd("Z>G40,105");
            System.out.println(a);
            System.out.println(b);
            System.out.println(GetPassword.getDecodePasswdErp(a));
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

}