package br.com.blecaute.empregos.objects;

import br.com.blecaute.empregos.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class Quest {

    @Getter private String id;
    @Getter private JobType job;
    @Getter @Setter private BigDecimal salary;
    @Getter @Setter private Integer meta;

    @Getter private Boolean increaseSalary;

    @Getter @Setter private List<String> messageComplete;
    @Getter @Setter private List<String> commands;
}