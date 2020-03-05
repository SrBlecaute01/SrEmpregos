package br.com.blecaute.empregos.manager;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.apis.events.JobContractEvent;
import br.com.blecaute.empregos.apis.events.JobDismissEvent;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.objects.Quest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    public Boolean hasJob(String p) {
        return SrEmpregos.getEmployees().stream().anyMatch(m -> m.getName().equalsIgnoreCase(p));
    }

    public Employee getEmployeeAccount(String p) {
        return SrEmpregos.getEmployees().stream().filter(f -> f.getName().equalsIgnoreCase(p)).findFirst().orElse(null);
    }

    public Boolean isInJob(String p, JobType job) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            return employee.getJob().equals(job);
        }
        return false;
    }

    public JobType getEmployeeJob(String p) {
        return SrEmpregos.getEmployees().stream().filter(f -> f.getName().equalsIgnoreCase(p)).map(Employee::getJob).findFirst().orElse(null);
    }

    public BigDecimal getEmployeeSalary(String p) {
        return SrEmpregos.getEmployees().stream().filter(f -> f.getName().equalsIgnoreCase(p)).map(Employee::getSalary).findFirst().orElse(new BigDecimal("0"));
    }

    public Boolean setEmployeeSalary(String p, BigDecimal salary) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            employee.setSalary(salary);
            return true;
        }
        return false;
    }

    public Boolean addEmployeeSalary(String p, BigDecimal salary) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            employee.addSalary(salary);
            return true;
        }
        return false;
    }

    public Boolean removeEmployeeSalary(String p, BigDecimal salary) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            employee.removeSalary(salary);
            return true;
        }
        return false;
    }

    public Integer getEmployeeCurrentMeta(String p) {
        return SrEmpregos.getEmployees().stream().filter(f -> f.getName().equalsIgnoreCase(p)).map(Employee::getCurrentMeta).findFirst().orElse(0);
    }

    public Boolean setEmployeeCurrentMeta(String p, Integer value) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            employee.setCurrentMeta(value);
            return true;
        }
        return false;
    }

    public Integer getEmployeeMeta(String p) {
        return SrEmpregos.getEmployees().stream().filter(f -> f.getName().equalsIgnoreCase(p)).map(Employee::getCompletedMeta).findFirst().orElse(0);
    }

    public Boolean setEmployeeMeta(String p, Integer value) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            employee.setCompletedMeta(value);
            return true;
        }
        return false;
    }

    public Boolean contractPlayer(Player p, JobType job) {
        if(!hasJob(p.getName())) {
            SrEmpregos.getEmployees().add(new Employee(p.getName(), job, SrEmpregos.getJobManager().getJobSalary(job)));
            JobContractEvent event = new JobContractEvent(p, job);
            Bukkit.getPluginManager().callEvent(event);
            return true;
        }
        return false;
    }

    public Boolean dismissPlayer(String p) {
        if(hasJob(p)) {
            Employee employee = getEmployeeAccount(p);
            SrEmpregos.getEmployees().remove(employee);
            SrEmpregos.getSqlManager().deleteEmployee(employee);

            JobDismissEvent event = new JobDismissEvent(employee, employee.getJob());
            Bukkit.getPluginManager().callEvent(event);
            return true;
        }
        return false;
    }

    public Boolean setEmployeeJob(String p, JobType job) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            employee.setJob(job);
        }
        return false;
    }

    public List<String> getEmployeeQuests(String p) {
        return SrEmpregos.getEmployees().stream().filter(f -> f.getName().equalsIgnoreCase(p)).map(Employee::getQuestsComplete).findFirst().orElse(new ArrayList<>());
    }

    public Boolean addEmployeeQuests(String p, Quest q) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            if(q.getJob().equals(employee.getJob()) && employee.getQuestsComplete().contains(q.getId())) {
                employee.getQuestsComplete().add(q.getId());
                return true;
            }
        }
        return false;
    }

    public Boolean removeEmployeeQuest(String p, Quest q) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null) {
            if(q.getJob().equals(employee.getJob()) && employee.getQuestsComplete().contains(q.getId())) {
                employee.getQuestsComplete().removeIf(f -> q.getId().equals(f));
                return true;
            }
        }
        return false;
    }

    public Boolean isCompleted(String p, Quest q) {
        Employee employee = getEmployeeAccount(p);
        if(employee != null && q.getJob().equals(employee.getJob())) {
            return employee.getQuestsComplete().stream().anyMatch(m -> q.getId().equals(m));
        }
        return false;
    }
}