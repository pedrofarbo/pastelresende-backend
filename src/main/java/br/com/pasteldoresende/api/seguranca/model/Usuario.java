package br.com.pasteldoresende.api.seguranca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    public String nome;

    @Column(name = "email")
    public String email;

    @Column(name = "senha")
    public String senha;

    @Column(name = "ativo")
    public Boolean ativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ultimo_reset")
    public Date ultimoReset;

    @Column(name = "data_token_exp")
    private Date tokenExp;

    @Column(name = "reset_token")
    public String resetToken;
}
