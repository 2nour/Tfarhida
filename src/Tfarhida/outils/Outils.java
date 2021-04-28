/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.outils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Nour
 */
public class Outils {
    
    public static boolean containsBadWords(String body){
        File file = new File("src/Tfarhida/outils/badWords.txt");
        List<String> bodyList=Arrays.asList(body.toLowerCase().split(" "));
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(bodyList.contains(line)) { 
                    return true;
                }
            }
        } catch(FileNotFoundException e) { 
            e.printStackTrace();
        }
        
        return false;
    }
    
}