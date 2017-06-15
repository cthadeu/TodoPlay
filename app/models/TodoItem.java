package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoItem {
    private final Integer id;
    private final String descricao;
    private final Boolean pronto;

    @JsonCreator
    public TodoItem(@JsonProperty("id") Integer id,
                    @JsonProperty("descricao") String descricao,
                    @JsonProperty("pronto") Boolean pronto) {
        this.id = id;
        this.descricao = descricao;
        this.pronto = pronto;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getPronto() {
        return pronto;
    }
}