package br.com.pamcary.gps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto {

    private Long id;

    @ApiModelProperty(value = "Nome do cliente")
    @NotEmpty(message = "O campo nome e obrigatorio!")
    @Size(min = 1, max = 40, message = "O campo nome deve ter o tamanho minimo de 3 e máximo de 40 posições")
    private String nome;

    @ApiModelProperty(value = "Data de Nascimento do cliente")
    @NotEmpty(message = "O campo dataNascimento e obrigatorio!")
    private String dataNascimento;

    @ApiModelProperty(value = "CPF do cliente")
    @CPF(message = "CPF invalido!")
    @NotEmpty(message = "O campo cpf e obrigatorio!")
    private String cpf;

    @ApiModelProperty(value = "Sexo do cliente, valores dieponiveis M para masculino ou F para Feminino")
    @NotEmpty(message = "O campo sexo e obrigatorio!")
    @Pattern(regexp = "[M,F]", message = "Valores aceitos apenas M ou F!")
    private String sexo;

}
