package br.com.pamcary.gps.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * http://localhost:8080/h2-console
 */
@Data
@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATA_NASCIMENTO")
    private Date dataNascimento;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "SEXO")
    private String sexo;

}
