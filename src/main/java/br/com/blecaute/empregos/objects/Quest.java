package br.com.blecaute.empregos.objects;

import br.com.blecaute.empregos.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor @Getter
public class Quest {

    private String id;
    private JobType job;
    @Setter private BigDecimal salary;
    @Setter private Integer meta;
    private Boolean increaseSalary;
    @Setter private List<String> messageComplete;
    @Setter private List<String> commands;
}