package br.com.blecaute.jobs.hook;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.dao.EmployeeDao;
import br.com.blecaute.jobs.dao.JobDao;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.interfaces.Hook;
import br.com.blecaute.jobs.model.interfaces.Job;
import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LegendChatHook implements Listener, Hook {

    private final EmployeeDao EMPLOYEE_DAO = EmployeeDao.getInstance();
    private final JobDao JOB_DAO = JobDao.getInstance();

    @Override
    public void enable() {
        SrEmpregos.getInstance().getLogger().info(getPluginName() + " encontrado... ativando implementação!");
        Bukkit.getServer().getPluginManager().registerEvents(this, SrEmpregos.getInstance());
    }

    @Override
    public boolean hasSupport() {
        return Bukkit.getPluginManager().getPlugin(getPluginName()) != null;
    }

    @Override
    public String getPluginName() {
        return "Legendchat";
    }

    @EventHandler
    public void onChat(ChatMessageEvent event) {
        if(!Config.CHAT_TAG_ENABLED_SETTING.value || !event.getTags().contains("empregos")) return;

        Player sender =event.getSender();
        Employee employee = EMPLOYEE_DAO.get(sender.getName());
        if(employee != null && JOB_DAO.has(employee.getJob())) {
            Job job = JOB_DAO.get(employee.getJob());
            event.setTagValue("empregos", job.getTag());
        }
    }
}