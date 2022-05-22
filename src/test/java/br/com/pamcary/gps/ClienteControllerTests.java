package br.com.pamcary.gps;

import br.com.pamcary.gps.dto.ClienteDto;
import br.com.pamcary.gps.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ClienteService service;

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoNomeForVazio() {
		ClienteDto cliente = ClienteDto
				.builder()
				.sexo("M")
				.cpf("39805106039")
				.dataNascimento("12/06/1983")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("O campo nome e obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoNomeForMaiorQueQuarentaCaracteres() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. ajsdlkjasd lkjasl akljsd lakjsdlk askjld alskjd aslkjda  aslkjdasd")
				.sexo("M")
				.cpf("39805106039")
				.dataNascimento("12/06/1983")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("O campo nome deve ter o tamanho minimo de 3 e máximo de 40 posições")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoDataNascimentoForVazio() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. silva")
				.sexo("M")
				.cpf("39805106039")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("O campo dataNascimento e obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoCpfForVazio() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. silva")
				.sexo("M")
				.dataNascimento("12/06/1983")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("O campo cpf e obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoSexoVazio() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. silva")
				.cpf("39805106039")
				.dataNascimento("12/06/1983")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("O campo sexo e obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoSexoForDiferenteDeMouFVazio() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. silva")
				.cpf("39805106039")
				.sexo("masculino")
				.dataNascimento("12/06/1983")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("Valores aceitos apenas M ou F!")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoCpfForInvalido() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. silva")
				.cpf("23898271823")
				.sexo("M")
				.dataNascimento("12/06/1983")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("CPF invalido!")));
	}

	@SneakyThrows
	@Test
	void deveRetornarErroParaCriarQuandoCpfForForDuplicado() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("Eduardo W. silva")
				.cpf("59695829058")
				.sexo("M")
				.dataNascimento("12/06/1983")
				.build();

		this.service.criar(cliente);

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message", is("Este CPF Ja foi cadastrado!")));
	}

}
