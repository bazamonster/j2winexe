import winexe.Winexe;
import winexe.Wmic;

import java.util.Map;

public class TestWinexe {
    public static void main(String[] args) {
        Winexe winexe = Winexe
                .initCredentialWhere()
                .usernameIs("user")
                .passwordIs("pass")
                .domainIs("domain")
                .init();
        /*List<Response> result = winexe
                .execute()
                .command("ping localhost")
                .inHost("127.0.0.1")
                .run();
        result.forEach(System.out::println);*/
        Wmic wmic = new Wmic();
        Map<String, String> wmiParams = wmic.getWmiParam(winexe, "hostname", "computersystem", "username");
        for (Map.Entry<String,String> pair : wmiParams.entrySet()){
            System.out.println(pair.getKey()+":"+pair.getValue());
        }
    }
}
