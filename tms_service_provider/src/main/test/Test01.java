import org.junit.Test;


public class Test01 {
    @Test
    public void test001() {
        String str = "410304199807210017";
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.delete(0,12);
        String s = stringBuilder.toString();
        System.out.println(s);
    }
}
