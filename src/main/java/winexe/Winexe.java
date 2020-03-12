package winexe;

import java.util.ArrayList;
import java.util.List;

public class Winexe {

    private Winexe(){}
    private String username;
    private String password;
    private String domain;
    private final Bash bash = new Bash();

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
            this.commands.add(command);
            return this;
        }

        public Executor commands(List<String> commands){
            this.commands.addAll(commands);
            return this;
        }

        public Executor inHost(String hostname){
            this.hostname = hostname;
            return this;
        }

        public List<Response> run(){
            String hostname = this.hostname;
            this.hostname = null;
            List<String> commands = new ArrayList<>(this.commands);
            this.commands.clear();
            if (hostname == null) throw new NullPointerException("Remote hostname is null");
            if (commands.isEmpty()) throw new NullPointerException("Command is null");
            List<Response> result = new ArrayList<>();
            for (String command : commands) {
                Response response = new Response();
                response.setCommand(command);
                response.setHostname(hostname);
                try {
                    response.setResult(execCommand(command, hostname));
                } catch (Exception e){
                    response.setResult(execCommand(command, hostname));
                }
                result.add(response);
            }
            return result;
        }
        private List<String> execCommand(String command, String hostname) {
            String[] winexeCommand = new String[] {
                    "winexe",
                    "//" + hostname,
                    command,
                    "--user=" + domain + "/" + username + "%" + password
            };
            return bash.execCommand(winexeCommand, "CP866");
        }

    }

}
