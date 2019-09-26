import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Winexe {

    private Winexe(){}
    private String username;
    private String password;
    private String domain;
    private final ProcessBuilder processBuilder = new ProcessBuilder();

    public static Builder initCredentialWhere(){
        return new Winexe().new Builder();
    }

    public Executor execute(){
        return new Executor();
    }

    public class Builder{

        private Builder(){}

        public Builder usernameIs(String username) {
            Winexe.this.username = username;
            return this;
        }

        public Builder passwordIs(String password){
            Winexe.this.password = password;
            return this;
        }

        public Builder domainIs(String domain) {
            Winexe.this.domain = domain;
            return this;
        }

        public Winexe init() {
            if (username == null) throw new NullPointerException("Domain username is null");
            if (password == null) throw new NullPointerException("Username password is null");
            if (domain == null) throw new NullPointerException("Domain is null");
            return Winexe.this;
        }
    }

    public class Executor{

        private String hostname;
        private List<String> commands = new ArrayList<>();

        private Executor(){}

        public Executor command(String command){
            commands.add(command);
            return this;
        }

        public Executor inHost(String hostname){
            this.hostname = hostname;
            return this;
        }

        public List<Response> run() throws IOException {
            String hostname = this.hostname;
            this.hostname = null;
            List<String> commands = new ArrayList<>(this.commands);
            this.commands.clear();
            if (hostname == null) throw new NullPointerException("Remote hostname is null");
            if (commands.isEmpty()) throw new NullPointerException("Command is null");
            List<Response> result = new ArrayList<>();
            List<Thread> threads = new ArrayList<>();
            for (String command : commands) {
                Response response = new Response();
                response.setCommand(command);
                response.setHostname(hostname);
                threads.add(new Thread(()->{
                    try {
                        response.setResult(execCommand(command, hostname));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    result.add(response);
                }));
            }
            threads.forEach(Thread::start);
            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return result;
        }
        private List<String> execCommand(String command, String hostname) throws IOException {
            String[] winexeCommand = new String[] {
                    "winexe",
                    "//" + hostname,
                    command,
                    "--user=" + domain + "/" + username + "%" + password
            };
            return IOUtils
                    .readLines(
                            processBuilder
                                    .command(winexeCommand)
                                    .start()
                                    .getInputStream(), "CP866"
                    );
        }

    }

}
