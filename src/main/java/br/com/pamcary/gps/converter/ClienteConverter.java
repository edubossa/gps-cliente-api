package br.com.pamcary.gps.converter;

import br.com.pamcary.gps.dto.ClienteDto;
import br.com.pamcary.gps.model.Cliente;
import org.springframework.stereotype.Component;

import static br.com.pamcary.gps.utils.DateUtils.dateToString;
import static br.com.pamcary.gps.utils.DateUtils.stringToDate;
import static br.com.pamcary.gps.utils.MascaraUtils.removeMascaraCpf;

@Component
public class ClienteConverter implements Converter<ClienteDto, Cliente> {

    @Override
    public ClienteDto toDto(Cliente model) {
        return ClienteDto
                .builder()
                .id(model.getId())
                .nome(model.getNome())
                .dataNascimento(dateToString(model.getDataNascimento()))
                .cpf(model.getCpf())
                .sexo(model.getSexo())
                .build();
    }

    @Override
    public Cliente toModel(ClienteDto dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setDataNascimento(stringToDate(dto.getDataNascimento()));
        cliente.setCpf(removeMascaraCpf(dto.getCpf()));
        cliente.setSexo(dto.getSexo());
        return cliente;
    }

}
