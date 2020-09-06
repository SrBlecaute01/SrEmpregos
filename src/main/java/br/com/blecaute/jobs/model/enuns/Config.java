package br.com.blecaute.jobs.model.enuns;

import lombok.Getter;
import org.bukkit.Sound;

import java.util.List;

public enum Config {

    PLACEHOLDER_PROGRESS_BARS_AMOUNT("placeholders.progresso.quantidade"),
    PLACEHOLDER_PROGRESS_BARS_COLOR1("placeholders.progresso.cor-completa"),
    PLACEHOLDER_PROGRESS_BARS_COLOR2("placeholders.progresso.cor-incompleta"),
    PLACEHOLDER_PROGRESS_BARS_COLOR_PERCENT("placeholders.progresso.cor-porcentagem"),
    PLACEHOLDER_PROGRESS_BARS_SYMBOL("placeholders.progresso.simbolo"),
    PLACEHOLDER_WITHOUT_JOB("placeholders.desempregado"),
    PLACEHOLDER_SELECT_JOB("placeholders.selecionar-emorego"),
    PLACEHOLDER_JOB_SELECTED("placeholders.emprego-selecionado"),

    PAYMENT_MESSAGE("mensagens.pagamento"),
    WITHOUT_PERMISSION_MESSAGE("mensagens.sem-permissao"),
    WITHOUT_JOB_MESSAGE("mensagens.sem-emprego"),
    DISMISS_MESSAGE("mensagens.pediu-demissao"),
    HAS_JOB_MESSAGE("mensagens.tem-emprego"),
    CONTRACT_PLAYER_MESSAGE("mensagens.contratado"),

    SOUND_SUCCESS("sons.sucesso"),
    SOUND_ERROR("sons.erro"),
    SOUND_GENERAL("sons.geral"),

    QUEST_COMPLETED("desafio-completado"),
    QUEST_STARTED("desafio-iniciado"),
    QUEST_BLOCKED("desafio-bloqueado"),

    CHAT_TAG_ENABLED_SETTING("tag"),

    JOB_INVENTORY_NAME("inventarios.empregos.nome"),
    JOB_INVENTORY_SIZE("inventarios.empregos.linhas"),
    DISMISS_INVENTORY_NAME("inventarios.demissao"),
    QUEST_INVENTORY_NAME("inventarios.desafios");

    public String string;
    public Sound sound;
    public Integer number;
    public List<String> list;
    public Boolean value;

    @Getter private String path;

    Config(String path) {
        this.path = path;
    }
}
