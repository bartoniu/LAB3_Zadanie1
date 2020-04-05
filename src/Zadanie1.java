import com.google.gson.Gson;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Zadanie1 {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        String tempWord = "";
        Word currentWord;

        long start = System.currentTimeMillis();
        double points = 0;

        ArrayList<Word> words = deserialize();
        ArrayList<Answer> answers2 = new ArrayList<>();

        for(int i = 0; i < 10; i++)
        {
            Random random = new Random();
            currentWord = words.remove(random.nextInt(words.size()));
            do
            {
                tempWord = JOptionPane.showInputDialog(frame, "Question "+(i+1)+" : "+currentWord.polish);
                tempWord = tempWord.toLowerCase();
            }
            while(isWordValid(tempWord));

            if (findMinLev(tempWord, currentWord.english) == 0)
            {
                points += 1;
            }
            else if (findMinLev(tempWord, currentWord.english) == 1)
            {
                points += 0.5;
            }
            answers2.add(new Answer(currentWord.polish, tempWord));
            tempWord = "";
        }

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        float elapsedTimeSec = elapsedTimeMillis/1000F;

        try
        {
            Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
            String jsonArray = gson.toJson(answers2);
            FileWriter fileWriter = new FileWriter("C:\\Users\\bdebs\\Desktop\\ZPO\\Lab3\\Zadanie1\\src\\imie_nazwisko.json");
            gson.toJson(jsonArray, fileWriter);
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(frame, "Czas twojego testu wyniosl: " + (Math.floor(elapsedTimeSec * 1e2) / 1e2) +"\nIlosc zdobytych punktow: " + points + "/10.");
        System.exit(0);
    }

    static boolean isWordValid(String s1)
    {
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s1);

        boolean check = m.find();

        if(check)
        {
            return true;
        }
        return false;
    }

    static double LevQWERTY(String s1, String s2)
    {
        double[][] matrix = new double[s1.length() + 1][s2.length() + 1];
        double cost;

        for (int i = 0; i < s1.length(); i++) {
            matrix[i][0] = i;
        }

        for (int j = 1; j < s2.length(); j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                matrix[i][j] = Math.min(matrix[i - 1][j] + 1,
                        Math.min(matrix[i][j - 1] + 1,
                                matrix[i - 1][j - 1] + cost));
            }
        }
        return matrix[s1.length()][s2.length()];
    }

    public static ArrayList<Word> deserialize()
    {
        ArrayList<Word> words = new ArrayList<>();

        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().create();

        String fileName = "C:\\Users\\bdebs\\Desktop\\ZPO\\Lab3\\Zadanie1\\src\\PolEngTest.json";
        Path path = new File(fileName).toPath();

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
        {
            Word[] wordshelp = gson.fromJson(reader, Word[].class);
            Collections.addAll(words, wordshelp);
        }
        catch (IOException IOExp)
        {
            IOExp.printStackTrace();
        }

        return words;
    }

    public static double findMinLev(String actual, ArrayList<String> anticipated)
    {
        double min = 9999.0;
        if (anticipated.size() == 1)
        {
            return LevQWERTY(actual, anticipated.get(0));
        }
        else
        {
            for (String s : anticipated)
            {
                double help = LevQWERTY(actual, s);
                if (help < min)
                    min = help;
            }
            return min;
        }
    }
}