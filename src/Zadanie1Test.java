import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Collections;

class Zadanie1Test {

    @org.junit.jupiter.api.Test
    void isWordValid() {
        String s1 = "Ala";
        Assertions.assertEquals(false, Zadanie1.isWordValid(s1));
    }

    @org.junit.jupiter.api.Test
    void levQWERTY() {
        String s2 = "kot", s3 = "kita";
        Assertions.assertEquals(2.0, Zadanie1.LevQWERTY(s2, s3));
    }

}