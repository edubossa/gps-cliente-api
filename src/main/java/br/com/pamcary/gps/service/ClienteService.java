package br.com.pamcary.gps.service;

import br.com.pamcary.gps.converter.ClienteConverter;
import br.com.pamcary.gps.dto.ClienteDto;
import br.com.pamcary.gps.handler.ClienteNaoLocalizadoHandlerException;
import br.com.pamcary.gps.handler.CpfDuplicadoHandlerException;
import br.com.pamcary.gps.model.Cliente;
import br.com.pamcary.gps.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static br.com.pamcary.gps.utils.MascaraUtils.removeMascaraCpf;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteConverter converter;

    /**
     * Recuperar todos os clientes (permitir filtro/ordenação por nome, utilizar paginação)
     * @return lista de clientes filtrados
     */
    public Page<Cliente> recuperarTodos(String nome, Pageable pageable) {
        log.info("/clientes?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort());
        return StringUtils.hasText(nome) ?
                this.repository.findClienteByNomeContainingIgnoreCase(nome, pageable) :
                this.repository.findAll(pageable);
    }

    /**
     * Recuperar um cliente por CPF (Retornar HttpStatusCode – 204 para não encontrado)
     * @param cpf
     * @return cliente de acordo com o cpf passado como parametro
     */
    public Optional<ClienteDto> recuperarPorCpf(String cpf) {
        return this.repository.findClienteByCpf(cpf).map(this.converter::toDto);
    }

    /**
     * Criar um novo Cliente validando os campos nome, data de nascimento, CPF e sexo (Utilizar BeanValidation).
     * Não permitir CPF duplicado e não utilizar máscara na gravação dos dados do CPF.
     * @param cliente
     * @return cliente criado na base de dados
     */
    public ClienteDto criar(ClienteDto cliente) {
        Optional<Cliente> clienteByCpf = this.repository.findClienteByCpf(removeMascaraCpf(cliente.getCpf()));
        if (clienteByCpf.isPresent()) {
            throw new CpfDuplicadoHandlerException();
        }
        final Cliente novoCliente = this.repository.save(this.converter.toModel(cliente));
        log.info("salvando novo cliente: " + novoCliente.toString());
        return this.converter.toDto(novoCliente);
    }

    /**
     * Atualizar um Cliente (validar se o mesmo existe)
     * @param id
     * @param cliente
     * @return cliente atualizado na base de dados
     */
    public ClienteDto atualizarCliente(Long id, ClienteDto cliente) {
        verificaSeClienteExiste(id);
        cliente.setId(id);
        final Cliente clienteAtualizado = this.repository.save(this.converter.toModel(cliente));
        log.info("atualizando o cliente: " + clienteAtualizado.toString());
        return converter.toDto(clienteAtualizado);
    }

    /**
     * Remover um Cliente (validar se o mesmo existe)
     * @param id
     */
    public void removerCliente(Long id) {
        verificaSeClienteExiste(id);
        this.repository.deleteById(id);
    }

    /**
     * Verifica se determinado cliente existe na base de dados
     * @param id
     */
    private void verificaSeClienteExiste(Long id) {
        if (this.repository.findById(id).isEmpty()) {
            throw new ClienteNaoLocalizadoHandlerException();
        }
    }

}
