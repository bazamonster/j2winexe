import java.io.IOException;
import java.util.List;

public class TestWinexe {
    public static void main(String[] args) throws IOException {
        Winexe winexe = Winexe
                .initCredentialWhere()
                .usernameIs("user")
                .passwordIs("12345")
                .domainIs("example")
                .init();
        List<Response> result = winexe
                .execute()
                .command("ping localhost")
                .inHost("127.0.0.1")
                .run();
        result.forEach(System.out::println);
    }
}
