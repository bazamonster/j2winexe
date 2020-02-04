package winexe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Wmic {

    public Map<String, String> getWmiParam(Winexe winexe, String address, String namespace, String param){
        String command = "wmic " + namespace + " get " + param + " /format:list";
        List<String> lines = getParams(winexe, address, command);
        Map<String,String> map = getWmiParamsFromLines(lines);
        if (map.isEmpty()) return null;
        return map;
    }

    public Map<String, String> getWmiParams(Winexe winexe, String address, String namespace) {
        String command = "wmic " + namespace + " get /format:list";
        List<String> lines = getParams(winexe, address, command);
        Map<String,String> map = getWmiParamsFromLines(lines);
        if (map.isEmpty()) return null;
        return map;
    }

    private Map<String, String> getWmiParamsFromLines(List<String> lines) {
        Map<String, String> map = new TreeMap<>();
        if (lines != null) {
            for (String line : lines) {
                if (line.contains("=")){
                    String value = line
                            .substring(line.indexOf("=") + 1)
                            .trim();
                    String key = line
                            .substring(0, line.indexOf("="))
                            .trim();
                    String resultKey = key;
                    for (int i = 0; map.containsKey(resultKey); i++){
                        resultKey = i + "_" +key;
                    }
                    if (!value.equals(""))
                        map.put(resultKey, value);
                }
            }
        }
        return map;
    }
    private List<String> getParams(Winexe winexe, String address, String command){
        return winexe
                .execute()
                .command(command)
                .inHost(address)
                .run()
                .get(0)
                .getResult();
    }
}
