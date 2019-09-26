import java.util.List;

public class Response {
    private String command;
    private String hostname;
    private List<String> result;

    public void setCommand(String command){
        this.command = command;
    }
    public void setHostname(String hostname){
        this.hostname = hostname;
    }
    public void setResult(List<String> result){
        this.result = result;
    }

    public String getCommand(){
        return this.command;
    }
    public String getHostname(){
        return this.hostname;
    }
    public List<String> getResult(){
        return this.result;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder("[");
        if (this.result.isEmpty()) result = null;
        else {
            for (String line : this.result){
                result.append("\"").append(line).append("\"").append(",");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1) + "]");
        }
        return "{hostname:\"" + hostname + "\",command:\"" + command + "\",result:" + result + "}";
    }
}
