package br.com.blecaute.jobs.process;

import br.com.blecaute.jobs.dao.EmployeeDao;
import br.com.blecaute.jobs.dao.JobDao;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.interfaces.Job;
import br.com.blecaute.jobs.utils.NumberUtils;
import br.com.blecaute.jobs.utils.StringUtils;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PlaceholderProcess {

    @Getter
    private static final Map<String, Function<OfflinePlayer, String>> placeholders = new HashMap<>();
    private static final EmployeeDao EMPLOYEE_DAO = EmployeeDao.getInstance();
    private static final JobDao JOB_DAO = JobDao.getInstance();

    public static void process() {
        placeholders.put("jobs_player_job", player -> {
            Employee account = EMPLOYEE_DAO.get(player.getName());
            return account == null ? Config.PLACEHOLDER_WITHOUT_JOB.string : account.getJob().getName();
        });

        placeholders.put("jobs_player_meta", player -> {
            Employee account = EMPLOYEE_DAO.get(player.getName());
            return account == null ? "0" : NumberUtils.format(account.getTotalMeta());
        });

        placeholders.put("jobs_player_current", player -> {
            Employee account = EMPLOYEE_DAO.get(player.getName());
            return account == null ? "0" : NumberUtils.format(account.getCurrentMeta());
        });

        placeholders.put("jobs_player_salary", player -> {
            Employee account = EMPLOYEE_DAO.get(player.getName());
            double value = account != null ? account.getSalary().doubleValue() : 0D;
            return NumberUtils.format(value);
        });

        placeholders.put("jobs_player_quests", player -> {
            Employee account = EMPLOYEE_DAO.get(player.getName());
            return account == null ? "0" : "" + account.getQuestsCompleted().size();
        });

        placeholders.put("jobs_player_progress", player -> {
            String symbol = Config.PLACEHOLDER_PROGRESS_BARS_SYMBOL.string;
            String color1 = Config.PLACEHOLDER_PROGRESS_BARS_COLOR1.string;
            String color2 = Config.PLACEHOLDER_PROGRESS_BARS_COLOR2.string;
            String percent = Config.PLACEHOLDER_PROGRESS_BARS_COLOR_PERCENT.string;
            int amount =  Config.PLACEHOLDER_PROGRESS_BARS_AMOUNT.number;

            Employee account = EMPLOYEE_DAO.get(player.getName());
            if(account == null || !JOB_DAO.has(account.getJob())) {
                return StringUtils.getProgressBar(0, 100, amount, symbol, color1, color2, percent);
            }

            Job job = JOB_DAO.get(account.getJob());
            int current = account.getCurrentMeta();
            int meta = job.getMeta();

            return StringUtils.getProgressBar(current, meta, amount, symbol, color1, color2, percent);
        });
    }

    public static String getValue(String placeholder, OfflinePlayer offlinePlayer) {
        if(!placeholders.containsKey(placeholder)) return null;

        return placeholders.get(placeholder).apply(offlinePlayer);
    }
}