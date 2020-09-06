package br.com.blecaute.jobs.utils;

import br.com.blecaute.jobs.dao.EmployeeDao;
import br.com.blecaute.jobs.dao.JobDao;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.interfaces.Job;
import com.google.common.base.Strings;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringUtils {

    private static final EmployeeDao EMPLOYEE_DAO = EmployeeDao.getInstance();
    private static final JobDao JOB_DAO = JobDao.getInstance();

    public static String replaceEmployeePlaceholders(String message, Player player) {
        Employee employee = EMPLOYEE_DAO.get(player.getName());

        String meta = "0";
        String job = Config.PLACEHOLDER_WITHOUT_JOB.string;
        String quests = "0";
        String salary = "0";
        String total = "0";
        String current = "0";
        if(employee != null) {
            meta = NumberUtils.format(JOB_DAO.get(employee.getJob()).getMeta());
            job = employee.getJob().getName();
            if(job == null) job = Config.PLACEHOLDER_WITHOUT_JOB.string;

            quests = NumberUtils.format(employee.getQuestsCompleted().size());
            salary = NumberUtils.format(employee.getSalary().doubleValue());
            total = NumberUtils.format(employee.getTotalMeta());
            current = NumberUtils.format(employee.getCurrentMeta());
        }

        return message
                .replace("@quests", quests)
                .replace("@salary", salary)
                .replace("@total",  total)
                .replace("@meta", meta)
                .replace("@current", current)
                .replace("@job", job)
                .replace("@player", player.getName());
    }

    public static String replaceJobPlaceholders(String message, JobType type) {
        Job job = JOB_DAO.get(type);
        return message
                .replace("@job", job.getName())
                .replace("@tag", job.getTag())
                .replace("@meta", NumberUtils.format(job.getMeta()))
                .replace("@salary", NumberUtils.format(job.getSalary().doubleValue()))
                .replace("@quests", NumberUtils.format(job.getQuests().size()));
    }

    public static String toString(List<String> list) {
        return list.isEmpty() ? "[]" : String.join(";", list);
    }

    public static List<String> fromString(String serialized) {
        List<String> list = new ArrayList<>();
        if(serialized.equals("[]")) return list;
        Collections.addAll(list, serialized.split(";"));
        return list;
    }

    public static String getProgressBar(double current, double max, int bars, String symbol, String color1, String color2, String color3) {
        float percent = (float) Math.min(1F, current / max);
        int progress = (int) (bars * percent);

        return color1 + Strings.repeat(symbol, progress)
                + color2 + Strings.repeat(symbol, bars - progress)
                + " " + color3 + NumberUtils.format(percent * 100) + "%";
    }
}