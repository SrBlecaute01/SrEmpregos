package br.com.blecaute.jobs.task;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.process.EmployeeProcess;
import org.bukkit.Bukkit;

public class EmployeeSaveTask implements Runnable{

    public EmployeeSaveTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(SrEmpregos.getInstance(), this, 20 * 60 * 10, 20 * 60 * 10);
    }

    @Override
    public void run() {
        EmployeeProcess.saveAsync();
    }
}