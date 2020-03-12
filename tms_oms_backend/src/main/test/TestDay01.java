import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class TestDay01 {

    @Test
    public void testIm(){
        String s = "1-2";
        StringBuffer sb = new StringBuffer(s);
        String substring = sb.substring(0, 1);
        System.out.println(substring);
    }
}
