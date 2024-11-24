package src;

import java.time.Year;
import java.util.Date;

public class Main {
    public static void main(String[] args){

        StreamingPlatform sp1 = new StreamingPlatform("WBBT");
        sp1.setup();
        //sp1.userLoginOrRegister();
        System.out.println(sp1.getMedias());
    }
}
