package br.com.pamcary.gps.controller;

import br.com.pamcary.gps.model.Cliente;
import br.com.pamcary.gps.dto.ClienteDto;
import br.com.pamcary.gps.service.ClienteService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
@Api(value = "Clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @ApiOperation(value = "Recuperar todos os clientes (permitir filtro/ordenação por nome, utilizar paginação)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            @ApiResponse(code = 409, message = "409 – Conflict para dados obrigatórios e inválidos"),
            @ApiResponse(code = 204, message = "204 para não encontrado")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Página de resultados que você deseja recuperar (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Número de registros por página.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Critérios de classificação no formato: property(,asc|desc). " +
                            "A ordem de classificação padrão é ascending. " +
                            "Vários critérios de classificação são suportados.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<Cliente> recuperarTodos(@RequestParam(required = false) String nome, @ApiIgnore(
            "Ignorado porque swagger ui mostra os parâmetros errados, " +
                    "em vez disso, eles são explicados nos parâmetros implícitos") Pageable pageable) {
        return this.clienteService.recuperarTodos(nome, pageable);
    }

    @ApiOperation(value = "Recuperar um cliente por CPF (Retornar HttpStatusCode – 204 para não encontrado)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            @ApiResponse(code = 409, message = "409 – Conflict para dados obrigatórios e inválidos"),
            @ApiResponse(code = 204, message = "204 para não encontrado")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<?> recuperarPorCpf(@PathVariable("cpf") String cpf) {
        final Optional<ClienteDto> clienteDTO = this.clienteService.recuperarPorCpf(cpf);
        if (clienteDTO.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(clienteDTO.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Criar um novo Cliente validando os campos nome, data de nascimento, CPF e sexo (Utilizar BeanValidation). Não permitir CPF duplicado e não utilizar máscara na gravação dos dados do CPF")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente criado com sucesso"),
            @ApiResponse(code = 409, message = "409 – Conflict para dados obrigatórios e inválidos")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClienteDto criar(@Valid @RequestBody ClienteDto cliente) {
        log.info("ClienteController.criar \n" + cliente.toString());
        return this.clienteService.criar(cliente);
    }

    @ApiOperation(value = "Atualizar um Cliente (validar se o mesmo existe)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            @ApiResponse(code = 409, message = "409 – Conflict para dados obrigatórios e inválidos"),
            @ApiResponse(code = 204, message = "204 para não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ClienteDto atualizarCliente(@Valid @RequestBody ClienteDto cliente, @PathVariable("id") Long id) {
        return this.clienteService.atualizarCliente(id, cliente);
    }

    @ApiOperation(value = "Remover um Cliente (validar se o mesmo existe)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            @ApiResponse(code = 409, message = "409 – Conflict para dados obrigatórios e inválidos"),
            @ApiResponse(code = 204, message = "204 para não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void removerCliente(@PathVariable("id") Long id) {
        this.clienteService.removerCliente(id);
    }

}
