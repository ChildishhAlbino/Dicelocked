/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Identification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author conno
 */
public class ID {

    private static final List<String> ids_in_use = new ArrayList<>();

    public static String GenerateID(int length) {
        Random rand = new Random();
        int i = rand.nextInt(10);
        String rStr = "_";
        for (int j = 0; j < length; j++) {
            rStr += i;
            i = rand.nextInt(10);
        }
        if (ids_in_use.contains(rStr)) {
            return GenerateID(length);
        } else {
            return rStr;
        }
    }
}
