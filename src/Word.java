import java.util.ArrayList;

public class Word
{
    String polish;
    ArrayList<String> english = new ArrayList<>();

    public Word(String polish, ArrayList<String> english)
    {
            this.polish = polish;
            this.english = english;
    }
}