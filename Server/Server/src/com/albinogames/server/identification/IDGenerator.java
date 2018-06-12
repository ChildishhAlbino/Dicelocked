/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albinogames.server.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author conno
 */
public class IDGenerator {

    private static final List<String> ids_in_use = new ArrayList<>();

    private static String GenerateID(int length, String identifier) {
        Random rand = new Random();
        int i = rand.nextInt(10);
        String rStr = identifier + "_";
        for (int j = 0; j < length; j++) {
            rStr += i;
            i = rand.nextInt(10);
        }
        if (ids_in_use.contains(rStr)) {
            return GenerateID(length, identifier);
        } else {
            ids_in_use.add(rStr);
            return rStr;
        }
    }

    public static String GenerateID_Name(int length, String identifier) {
        return GenerateID(length, identifier);
    }

    public static String GenerateID_Number(int length) {
        return GenerateID(length, "").substring(1);
    }

    public static int GenerateID_Int(int length) {
        String s = GenerateID_Number(length);
        int i = 0;
        try {
            i = Integer.parseInt(s.substring(1));
        } catch (NumberFormatException e) {
            System.out.println("Error parsing ID");
            RemoveID(s);
            i = GenerateID_Int(length);
        }
        return i;
    }

    public static void RemoveID(String ID) {
        ids_in_use.remove(ID);
    }
}
