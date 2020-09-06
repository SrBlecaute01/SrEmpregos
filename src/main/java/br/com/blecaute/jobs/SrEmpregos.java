package br.com.blecaute.jobs;

import br.com.blecaute.jobs.apis.GlowAPI;
import br.com.blecaute.jobs.apis.SkullAPI;
import br.com.blecaute.jobs.command.JobCommand;
import br.com.blecaute.jobs.database.Database;
import br.com.blecaute.jobs.database.MySQL;
import br.com.blecaute.jobs.database.SQLite;
import br.com.blecaute.jobs.inventory.DismissInventory;
import br.com.blecaute.jobs.inventory.JobInventory;
import br.com.blecaute.jobs.inventory.QuestInventory;
import br.com.blecaute.jobs.listeners.InventoryClickListener;
import br.com.blecaute.jobs.hook.LegendChatHook;
import br.com.blecaute.jobs.hook.MVdWPlaceholderHook;
import br.com.blecaute.jobs.hook.PlaceholderHook;
import br.com.blecaute.jobs.listeners.BlockBreakListener;
import br.com.blecaute.jobs.listeners.EntityDeathListener;
import br.com.blecaute.jobs.listeners.PlayerFishListener;
import br.com.blecaute.jobs.manager.*;
import br.com.blecaute.jobs.model.interfaces.Hook;
import br.com.blecaute.jobs.process.ConfigProcess;
import br.com.blecaute.jobs.process.EmployeeProcess;
import br.com.blecaute.jobs.process.JobProcess;
import br.com.blecaute.jobs.process.PlaceholderProcess;
import br.com.blecaute.jobs.task.EmployeeSaveTask;
import br.com.blecaute.jobs.utils.FileUtils;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SrEmpregos extends JavaPlugin {

    @Getter private static SrEmpregos instance;
    @Getter private Economy economy;
    @Getter private Database db;

    @Getter private EmployeeManager employeeManager;

    @Getter private final Executor executor = ForkJoinPool.commonPool();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        FileUtils.createFolder("empregos");

        if(setupEconomy()) {
            loadDatabase();
            loadAPIs();
            loadProcess();
            loadManagers();
            loadInventories();
            loadHooks();

            registerEvents();
            registerCommands();

            checkUpdate();

            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage("§b _____     ");
            Bukkit.getConsoleSender().sendMessage("§b|   __|___ ");
            Bukkit.getConsoleSender().sendMessage("§b|__   |  _| §fSrEmpregos");
            Bukkit.getConsoleSender().sendMessage("§b|_____|_|   §fSrBlecaute#3731");
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage("");

            new EmployeeSaveTask();

        } else {
            getLogger().severe("Não foi possível se conectar com o Vault. Certifique-se que você");
            getLogger().severe("tenha o vault e um plugin de economia instalados em seu servidor.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        EmployeeProcess.save();
        db.close();
    }

    private void loadDatabase() {
        FileConfiguration config = SrEmpregos.getInstance().getConfig();
        if(config.getBoolean("MySQL.ativado")) {
            String host = config.getString("MySQL.host");
            String port = config.getString("MySQL.porta");
            String database = config.getString("MySQL.database");
            String user = config.getString("MySQL.usuario");
            String password = config.getString("MySQL.senha");
            db = new MySQL(host, port, database, user, password);
        } else {
            db = new SQLite();
        }
    }

    private void loadAPIs() {
        GlowAPI.load();
        SkullAPI.load();
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    private void loadManagers() {
        employeeManager = new EmployeeManager();
    }

    private void loadHooks() {
        List<Hook> hooks = new ArrayList<>();
        hooks.add(new LegendChatHook());
        hooks.add(new MVdWPlaceholderHook());
        hooks.add(new PlaceholderHook());

        hooks.stream().filter(Hook::hasSupport).forEach(Hook::enable);
    }

    private void loadInventories() {
        JobInventory.load();
        DismissInventory.load();
        QuestInventory.load();
    }

    private void loadProcess() {
        ConfigProcess.process();
        PlaceholderProcess.process();
        JobProcess.process();
        EmployeeProcess.init();
        EmployeeProcess.process();
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(), getInstance());
        pluginManager.registerEvents(new InventoryClickListener(), getInstance());
        pluginManager.registerEvents(new EntityDeathListener(), getInstance());
        pluginManager.registerEvents(new PlayerFishListener(), getInstance());
    }

    private void registerCommands() {
        getCommand("emprego").setExecutor(new JobCommand());
    }


    /**
     *  Agradecimentos ao VitorBlog pelo seu tutorial
     *  de como fazer as verificações de atualizações ;)
     */
    private void checkUpdate() {
        CompletableFuture.runAsync(() -> {
            try {
                String link = "https://api.github.com/repos/SrBlecaute01/SrEmpregos/releases/latest";
                String version = this.getDescription().getVersion();

                URL url = new URL(link);
                URLConnection connection = url.openConnection();
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = reader.lines().collect(Collectors.joining("\n"));

                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
                    String latestVersion = (String) jsonObject.get("tag_name");
                    String download = (String) jsonObject.get("html_url");
                    if (!version.equals(latestVersion)){
                        getLogger().info("");
                        getLogger().info(" Uma nova versão está disponível!");
                        getLogger().info(" Versão atual: " + version);
                        getLogger().info(" Nova versão: " + latestVersion);
                        getLogger().info("");
                        getLogger().info(" Para baixar a versão mais recente abra o link abaixo:");
                        getLogger().info(" " + download);
                        getLogger().info("");

                    } else {
                        getLogger().info("Você está na versão mais recente do plugin");
                    }
                }
            } catch (Exception e) {
                getLogger().severe("Ocorreu um erro ao tentar verificar as atualizações: " + e.getMessage());
            }

        }, executor);
    }
}