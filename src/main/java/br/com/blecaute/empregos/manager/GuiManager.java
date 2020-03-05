package br.com.blecaute.empregos.manager;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.apis.GlowAPI;
import br.com.blecaute.empregos.apis.SkullAPI;
import br.com.blecaute.empregos.enums.GuiType;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.GuiHolder;
import br.com.blecaute.empregos.objects.Quest;
import br.com.blecaute.empregos.utils.ItemBuilder;
import br.com.blecaute.empregos.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GuiManager {

    private String guiName;
    private String selectJob;
    private String selectedJob;

    private String jobStatus;
    private String itemInfoName;
    private List<String> itemInfoLore;

    private String questName;
    private List<String> questLore;

    private ItemStack miner;
    private ItemStack farmer;
    private ItemStack fisher;
    private ItemStack hunter;
    private ItemStack killer;
    private ItemStack lumber;
    private ItemStack digger;

    private ItemStack itemConfirm;
    private ItemStack itemCancel;
    private ItemStack quitJob;
    private ItemStack quests;

    private HashMap<JobType, String> type = new HashMap<>();

    public GuiManager() {
        load();
    }

    public void openJobsGui(Player p) {
        Inventory inv = Bukkit.createInventory(new GuiHolder(GuiType.JOBS), 5 * 9, this.guiName);
        JobType job = SrEmpregos.getEmployeeManager().getEmployeeJob(p.getName());

        inv.setItem(10, getJobItem(miner, JobType.MINER, job));
        inv.setItem(12, getJobItem(lumber, JobType.LUMBERJACK, job));
        inv.setItem(14, getJobItem(digger, JobType.DIGGER, job));
        inv.setItem(16, getJobItem(hunter, JobType.HUNTER, job));
        inv.setItem(20, getJobItem(fisher, JobType.FISHER, job));
        inv.setItem(22, getJobItem(farmer, JobType.FARMER, job));
        inv.setItem(24, getJobItem(killer, JobType.KILLER, job));

        String name = changePlayerValues(p, itemInfoName);
        List<String> lore = itemInfoLore.stream().map(m -> changePlayerValues(p, m.replace("&", "§"))).collect(Collectors.toList());
        inv.setItem(36, new ItemBuilder(SkullAPI.getByName(p.getName())).setName(name).setLore(lore).toItemStack());
        inv.setItem(37, quests);
        inv.setItem(44, quitJob);
        p.openInventory(inv);
    }

    public void openDismiss(Player p) {
        Inventory inv = Bukkit.createInventory(new GuiHolder(GuiType.CONFIRM), 3 * 9, "§8Sair do emprego ?");
        inv.setItem(11, itemCancel);
        inv.setItem(13, quitJob);
        inv.setItem(15, itemConfirm);
        p.openInventory(inv);
    }

    public void openQuests(Player p, int page, JobType job) {
        int start = 11, index = page * 15 - 15;

        Inventory inv = Bukkit.createInventory(new GuiHolder(GuiType.QUESTS, page, job), 6 * 9, "§8Suas conquistas");
        List<Quest> quests = SrEmpregos.getJobManager().getJobQuests(job);
        EmployeeManager employee = SrEmpregos.getEmployeeManager();

        for(; index < quests.size(); index++) {
            ItemStack item;
            Quest q = quests.get(index);

            String name = changeQuestValues(job, q, this.questName);
            List<String> lore = this.questLore.stream().map(m -> changeQuestValues(job, q, m)).collect(Collectors.toList());
            if(employee.isCompleted(p.getName(), q)) {
                lore.add("§aQuest concluida");
                item = GlowAPI.addGlow(new ItemBuilder(Material.BOOK).setName(name).setLore(lore).toItemStack());
            } else {
                lore.add("§cQuest não concluida");
                item = new ItemBuilder(Material.PAPER).setName(name).setLore(lore).toItemStack();
            }

            inv.setItem(start, item);
            start++;
            if(start == 16 || start == 25) {
                start += 4;
            } else if(start == 34) {
                break;
            }
        }

        inv.setItem(49, new ItemBuilder(Material.BOOK_AND_QUILL).setName("§aMenu princiapl").toItemStack());
        if(page > 1) inv.setItem(48, new ItemBuilder(Material.ARROW).setName("§7Voltar").toItemStack());
        if(page * 15 - 15 > quests.size()) inv.setItem(48, new ItemBuilder(Material.ARROW).setName("§7Próxima página").toItemStack());

        p.openInventory(inv);
    }

    public void load() {
        FileConfiguration config = SrEmpregos.getInstance().getConfig();
        guiName = config.getString("Empregos-Gui.Nome").replace("&", "§");
        selectJob = config.getString("Empregos-Gui.Seleionar-Emprego").replace("&", "§");
        selectedJob = config.getString("Empregos-Gui.Emprego-Selecionado").replace("&", "§");

        miner = getItemFromConfig(config, "Empregos-Gui.Minerador", JobType.MINER);
        farmer = getItemFromConfig(config, "Empregos-Gui.Fazendeiro", JobType.FARMER);
        fisher = getItemFromConfig(config, "Empregos-Gui.Pescador", JobType.FISHER);
        hunter = getItemFromConfig(config, "Empregos-Gui.Cacador", JobType.HUNTER);
        killer = getItemFromConfig(config, "Empregos-Gui.Assassino", JobType.KILLER);
        lumber = getItemFromConfig(config, "Empregos-Gui.Lenhador", JobType.LUMBERJACK);
        digger = getItemFromConfig(config, "Empregos-Gui.Escavador", JobType.DIGGER);

        itemConfirm = getItemFromConfig(config, "Item-Confirmar", null);
        itemCancel = getItemFromConfig(config, "Item-Cancelar", null);
        quitJob = getItemFromConfig(config, "Item-Pedir-As-Contas", null);
        quests = getItemFromConfig(config, "Item-Conquistas", null);

        jobStatus = config.getString("Empregos-Gui.Sem-Emprego").replace("&", "§");
        itemInfoName  = config.getString("Item-Info.Nome").replace("&", "§");
        itemInfoLore = config.getStringList("Item-Info.Lore").stream().map(m -> m.replace("&", "§")).collect(Collectors.toList());

        type.put(JobType.MINER, config.getString("Item-Quest.Tipos.Minerador"));
        type.put(JobType.KILLER, config.getString("Item-Quest.Tipos.Assassino"));
        type.put(JobType.FISHER, config.getString("Item-Quest.Tipos.Pescador"));
        type.put(JobType.FARMER, config.getString("Item-Quest.Tipos.Fazendeiro"));
        type.put(JobType.LUMBERJACK, config.getString("Item-Quest.Tipos.Lenhador"));
        type.put(JobType.DIGGER, config.getString("Item-Quest.Tipos.Escavador"));
        type.put(JobType.HUNTER, config.getString("Item-Quest.Tipos.Cacador"));

        questName = config.getString("Item-Quest.Nome").replace("&", "§");
        questLore = config.getStringList("Item-Quest.Lore").stream().map(m -> m.replace("&", "§")).collect(Collectors.toList());
    }

    private String getAction(JobType job, int type) {
        String[] value = this.type.get(job).split("-");
        if(value.length >= 2) {
            return type == 1 ? value[0] : value[1];
        }
        return "";
    }

    private ItemStack getJobItem(ItemStack item, JobType job, JobType playerJob) {
        String toAdd = job.equals(playerJob) ? selectedJob : selectJob;
        List<String> lore = item.getItemMeta().getLore();
        lore.add(toAdd);

        ItemStack toReturn = new ItemBuilder(item.clone()).setLore(lore).toItemStack();
        if(job.equals(playerJob)) {
            toReturn = GlowAPI.addGlow(toReturn);
        }

        return toReturn;
    }

    private ItemStack getItemFromConfig(FileConfiguration config, String node, JobType job) {
        ItemStack items = config.getBoolean( node + ".Skull.Ativado") ?
                SkullAPI.getByUrl(config.getString(node + ".Skull.Url")) :
                getItemDeserializer(config.getString(node + ".ID-Data"));

        if(items != null) {
            ItemBuilder ib = new ItemBuilder(items);
            ib.setName(changeValues(job, config.getString(node + ".Nome").replace("&", "§")));
            ib.setLore(config.getStringList(node + ".Lore").stream().map(s -> changeValues(job, s.replace("&", "§"))).collect(Collectors.toList()));
            return ib.toItemStack();
        }
        return new ItemStack(Material.BARRIER);
    }

    @SuppressWarnings("deprecation")
    private ItemStack getItemDeserializer(String serialized) {
        String[] item = serialized.split(":");
        int id = 0, data = 0;
        try {
            id = Integer.parseInt(item[0]);
            if(item.length == 2) {
                data = Integer.parseInt(item[1]);
            }
        } catch (NumberFormatException ignored) {}
        return new ItemStack(Material.getMaterial(id), 1, (short) data);
    }

    private String changePlayerValues(Player p, String msg) {
        EmployeeManager employee = SrEmpregos.getEmployeeManager();
        JobManager manager = SrEmpregos.getJobManager();

        String meta = employee.hasJob(p.getName()) ? "" + manager.getJobMeta(employee.getEmployeeJob(p.getName())) : "0";
        String job = employee.hasJob(p.getName()) ? manager.getJobName(employee.getEmployeeJob(p.getName())) : jobStatus;

        return msg
                .replace("@quests", "" + employee.getEmployeeQuests(p.getName()).size())
                .replace("@salario", Utils.getNumberFormatted(employee.getEmployeeSalary(p.getName()).doubleValue()))
                .replace("@total",  "" + employee.getEmployeeMeta(p.getName()))
                .replace("@meta", meta)
                .replace("@current", "" + employee.getEmployeeCurrentMeta(p.getName()))
                .replace("@tag", manager.getJobTag(employee.getEmployeeJob(p.getName())).replace("&", "§"))
                .replace("@emprego", job.replace("&", "§"))
                .replace("@player", p.getName());
    }

    private String changeQuestValues(JobType job, Quest q, String msg) {
        return msg
                .replace("@tipo2", getAction(job, 2))
                .replace("@tipo1", getAction(job, 1))
                .replace("@quantia", "" + q.getMeta())
                .replace("@emprego", JobType.getName(job.name()))
                .replace("&", "§");
    }

    private String changeValues(JobType job, String msg) {
        if(job == null) return msg;
        JobManager manager = SrEmpregos.getJobManager();
        return msg
                .replace("@emprego", JobType.getName(job.name()).replace("&", "§"))
                .replace("@tag", manager.getJobTag(job).replace("&", "§"))
                .replace("@meta", "" + manager.getJobMeta(job))
                .replace("@salario", Utils.getNumberFormatted(manager.getJobSalary(job).doubleValue()))
                .replace("@quests", "" + manager.getJobQuests(job).size());
    }
}