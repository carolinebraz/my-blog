package com.generation.myblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.myblog.model.Usuario;
import com.generation.myblog.repository.UsuarioRepository;
import com.generation.myblog.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "-"));

	}

	@Test
	@DisplayName("Cadastrar um usuário")
	public void deveCriarUmUsuario() {

		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Clarice Tenesso", "ctenesso@email.com.br", "16267162", "-"));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

	}

	@Test
	@DisplayName("Impedir o cadastro de usuários duplicados")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Ivete Astato", "iastato@email.com.br", "15185151", "-"));

		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Ivete Astato", "iastato@email.com.br", "15185151", "-"));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar dados do usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCadastrado = usuarioService
				.cadastrarUsuario(new Usuario(0L, "Marie Polônio", "mpolonio@email.com.br", "16384163", "-"));

		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Marie C. Polônio",
				"mpolonio@email.com.br", "16384163", "-");

		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

	}

	@Test
	@DisplayName("Listar todos os usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Brooks Radônio", "bradonio@email.com.br", "17386173", "-"));

		usuarioService
				.cadastrarUsuario(new Usuario(0L, "Hoffman Seabórgio", "hseaborgio@email.com.br", "17360173", "-"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}

}
