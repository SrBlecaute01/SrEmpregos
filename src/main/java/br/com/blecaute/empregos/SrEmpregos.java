package br.com.blecaute.empregos;

import br.com.blecaute.empregos.apis.GlowAPI;
import br.com.blecaute.empregos.apis.SkullAPI;
import br.com.blecaute.empregos.database.Database;
import br.com.blecaute.empregos.database.MySQL;
import br.com.blecaute.empregos.database.SQLite;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.hooks.LegendHook;
import br.com.blecaute.empregos.hooks.MvdwHook;
import br.com.blecaute.empregos.hooks.PlaceholderHook;
import br.com.blecaute.empregos.jobs.*;
import br.com.blecaute.empregos.listeners.BreakEvent;
import br.com.blecaute.empregos.listeners.ClickEvent;
import br.com.blecaute.empregos.listeners.EntityEvent;
import br.com.blecaute.empregos.listeners.FishEvent;
import br.com.blecaute.empregos.manager.*;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.objects.Quest;
import br.com.blecaute.empregos.utils.FileUtils;
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
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class SrEmpregos extends JavaPlugin {

    @Getter private static SrEmpregos instance;
    @Getter private static Economy economy;
    @Getter private static Database db;

    @Getter private static MessagesManager messagesManager;
    @Getter private static GuiManager guiManager;
    @Getter private static JobManager jobManager;
    @Getter private static EmployeeManager employeeManager;
    @Getter private static SQLManager sqlManager;

    @Getter private static List<Job> jobs = new ArrayList<>();
    @Getter private static List<Employee> employees = new ArrayList<>();
    @Getter private static final Executor executor = ForkJoinPool.commonPool();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadFiles();

        if(setupEconomy()) {

            loadDatabase();
            loadAPIs();
            loadJobs();
            loadHooks();
            loadManagers();
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

            Bukkit.getScheduler().runTaskTimerAsynchronously(this, sqlManager::saveEmployees, 20 * 60, 10 * 20 * 60);

        } else {
            info("§cNão foi possível se conectar com o Vault. Certifique-se que você");
            info("§ctenha o vault e um plugin de economia instalados em seu servidor.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        sqlManager.saveEmployees();
        db.close();
    }

    public static void info(String msg) {
        Bukkit.getConsoleSender().sendMessage("[SrEmpregos] " + msg);
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

        sqlManager = new SQLManager();
        sqlManager.createTable("`SrEmpregos`", "`name` VARCHAR(36) NOT NULL, `job` TEXT NOT NULL, `salary` TEXT NOT NULL, `current` INTEGER NOT NULL, `total` INTEGER NOT NULL, `quests` TEXT, PRIMARY KEY (`name`)");
        employees = sqlManager.getEmployees();
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
        messagesManager = new MessagesManager();
        jobManager = new JobManager();
        employeeManager = new EmployeeManager();
        guiManager = new GuiManager();
    }

    private void loadHooks() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if(pluginManager.getPlugin("Legendchat") != null) {
            new LegendHook();
        }

        if(pluginManager.getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
        }

        if(pluginManager.getPlugin("MVdWPlaceholderAPI") != null) {
            new MvdwHook();
        }
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BreakEvent(), getInstance());
        pluginManager.registerEvents(new ClickEvent(), getInstance());
        pluginManager.registerEvents(new EntityEvent(), getInstance());
        pluginManager.registerEvents(new FishEvent(), getInstance());
    }

    private void registerCommands() {
        getCommand("emprego").setExecutor(new JobCmd());
    }

    private void loadFiles() {
        FileUtils.createFolder("empregos");
        FileUtils.createConfig("assassino", "empregos");
        FileUtils.createConfig("cacador", "empregos");
        FileUtils.createConfig("escavador", "empregos");
        FileUtils.createConfig("fazendeiro", "empregos");
        FileUtils.createConfig("lenhador", "empregos");
        FileUtils.createConfig("minerador", "empregos");
        FileUtils.createConfig("pescador", "empregos");
    }

    private void loadJobs() {
        jobs.add(loadJob("assassino", JobType.KILLER));
        jobs.add(loadJob("cacador", JobType.HUNTER));
        jobs.add(loadJob("escavador", JobType.DIGGER));
        jobs.add(loadJob("fazendeiro", JobType.FARMER));
        jobs.add(loadJob("lenhador", JobType.LUMBERJACK));
        jobs.add(loadJob("minerador", JobType.MINER));
        jobs.add(loadJob("pescador", JobType.FISHER));
    }

    private Job loadJob(String file, JobType job) {
        FileConfiguration config = FileUtils.getConfiguration(FileUtils.getFile(file, "empregos"));
        String name = config.getString("Nome").replace("&", "§");
        String tag = config.getString("Tag").replace("&", "§");
        int meta = config.getInt("Meta");
        BigDecimal salary = new BigDecimal("" + config.getDouble("Salario"));
        List<String> worlds =  config.getStringList("Mundos");

        List<Quest> quests = new ArrayList<>();
        for(String section : config.getConfigurationSection("Quests").getKeys(false)) {
            boolean increaseSalary = config.getBoolean("Quests." + section + ".Aumentar-Salario.ativado");
            BigDecimal questSalary = new BigDecimal("" + config.getDouble("Quests." + section + ".Aumentar-Salario.valor"));
            int questMeta = config.getInt("Quests." + section + ".Meta");
            List<String> msg = config.getStringList("Quests." + section + ".Mensagem-Completou-Quest").stream().map(m -> m.replace("&", "§")).collect(Collectors.toList());
            List<String> cmd = config.getStringList("Quests." + section + ".Comandos");
            quests.add(new Quest(section, job, questSalary, questMeta, increaseSalary, msg, cmd));
        }

        switch (job) {

            case MINER:
                return new Miner(name, tag, salary, meta, worlds, quests);
            case FARMER:
                return new Farmer(name, tag, salary, meta, worlds, quests);
            case DIGGER:
                return new Digger(name, tag, salary, meta, worlds, quests);
            case FISHER:
                return new Fisher(name, tag, salary, meta, worlds, quests);
            case HUNTER:
                return new Hunter(name, tag, salary, meta, worlds, quests);
            case KILLER:
                return new Killer(name, tag, salary, meta, worlds, quests);
            case LUMBERJACK:
                return new Lumberjack(name, tag, salary, meta, worlds, quests);
            default:
                break;
        }
        return null;
    }

    /**
     *  Agradecimentos ao VitorBlog pelo seu tutorial
     *  de como fazer a verificação de atualizações ;)
     */

    private void checkUpdate() {
        CompletableFuture.runAsync(() -> {
            String link = "https://api.github.com/repos/SrBlecaute01/SrEmpregos/releases/latest";
            String version = this.getDescription().getVersion();

            try {
                URL url = new URL(link);
                URLConnection connection = url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.lines().collect(Collectors.joining("\n"));

                JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
                String latestVersion = (String) jsonObject.get("tag_name");
                String download = (String) jsonObject.get("html_url");

                if (!version.equals(latestVersion)){
                    info("");
                    info(" §cUma nova versão está disponível!");
                    info(" §cVersão atual: §7" + version);
                    info(" §cNova versão: §7" + latestVersion);
                    info("");
                    info(" §cPara baixar a versão mais recente abra o link abaixo:");
                    info(" §7" + download);
                    info("");

                } else {
                    info("§aVocê está na versão mais recente do plugin");
                }

            } catch (Exception e) {
                info("§cOcorreu um erro ao tentar verificar as atualizações: " + e.getMessage());
            }
        }, getExecutor());
    }
}