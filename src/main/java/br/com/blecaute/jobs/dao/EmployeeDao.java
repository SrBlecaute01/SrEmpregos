package br.com.blecaute.jobs.dao;

import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.abstracts.Dao;
import br.com.blecaute.jobs.process.EmployeeProcess;
import lombok.Getter;

public class EmployeeDao extends Dao<String, Employee> {

    @Getter private static final EmployeeDao instance = new EmployeeDao();

    @Override
    public void add(Employee value) {
        add(value.getName(), value);
    }

    @Override
    public void remove(String key) {
        Employee employee = delete(key);
        if(employee != null) {
            EmployeeProcess.delete(employee);
        }
    }
}