package escola.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O código da sala é obrigatório")
    @Size(max = 30, message = "O código deve ter no máximo 30 caracteres")
    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @NotBlank(message = "O nome da sala é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "A capacidade da sala é obrigatória")
    @Positive(message = "A capacidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer capacidade;

    @NotNull(message = "O tipo da sala é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoSala tipo = TipoSala.SALA_AULA;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusSala status = StatusSala.LIVRE;

    public Sala() {
    }

    public Sala(String codigo, String nome, Integer capacidade, TipoSala tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.capacidade = capacidade;
        this.tipo = tipo == null ? TipoSala.SALA_AULA : tipo;
        this.status = StatusSala.LIVRE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public TipoSala getTipo() {
        return tipo;
    }

    public void setTipo(TipoSala tipo) {
        this.tipo = tipo;
    }

    public StatusSala getStatus() {
        return status;
    }

    public void setStatus(StatusSala status) {
        this.status = status;
    }
}
