package br.com.blecaute.empregos.manager;

import br.com.blecaute.empregos.SrEmpregos;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MessagesManager {

    private static HashMap<String, String> map = new HashMap<>();
    private static HashMap<String, List<String>> list = new HashMap<>();

    public MessagesManager() {
        load();
    }

    public void load() {
        FileConfiguration config = SrEmpregos.getInstance().getConfig();
        map.put("sem.emprego", config.getString("Mensagens.Sem-Emprego"));
        map.put("com.emprego", config.getString("Mensagens.Com-Emprego"));
        map.put("contratado", config.getString("Mensagens.Foi-Contratado"));
        map.put("demissao", config.getString("Mensagens.Pediu-Demissao"));
        list.put("pagamento", config.getStringList("Mensagens.Recebeu-Pagamento"));
    }

    public String getMessage(String key) {
        return map.getOrDefault(key, "").replace("&", "§");
    }

    public List<String> getMessageList(String key) {
        return list.getOrDefault(key, new ArrayList<>()).stream().map(m -> m.replace("&", "§")).collect(Collectors.toList());
    }
}