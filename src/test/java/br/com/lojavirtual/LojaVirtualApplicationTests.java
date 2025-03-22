package br.com.lojavirtual;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lojavirtual.controller.AcessoController;
import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.repository.AcessoRepository;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LojaVirtualApplication.class)
@AutoConfigureMockMvc
public class LojaVirtualApplicationTests extends TestCase{

	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private WebApplicationContext wac;
	
    	
	/*Teste do end-point salvar*/
	@Test
	public void testRestApiCadastroACesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper objectMapper = new ObjectMapper(); // Json
		
		ResultActions retornoApi = mockMvc
						.perform(MockMvcRequestBuilders.post("/salvarAcesso") //indica se é post ou get
						.content(objectMapper.writeValueAsString(acesso)) // conteudo
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)); 
		
		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		
		/*Converter o retorno da API em um objeto Acesso*/
		
		Acesso acessoRetorno = objectMapper.
					        readValue(retornoApi.andReturn().getResponse().getContentAsString(), 
							Acesso.class);
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
	}
	
	/*Teste end-point deletar*/
	@Test
	public void testRestApiDeleteACesso() throws JsonProcessingException, Exception {
	    
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
				
		// Criar um novo acesso para deletar
	    Acesso acesso = new Acesso();
	    acesso.setDescricao("ROLE_TESTE_DELETAR");

	    // Salvar o objeto no banco
	    acesso = acessoRepository.save(acesso);

	    // Verificar se foi salvo corretamente para evitar NullPointerException
	    assertNotNull("O acesso não foi salvo corretamente.", acesso);
	    assertNotNull("O ID do acesso salvo está nulo.", acesso.getId());

	    // Criar um objeto JSON a partir da entidade
	    ObjectMapper objectMapper = new ObjectMapper(); // Json

	    // Executar a API para deletar o acesso
	    ResultActions retornoApi = mockMvc
	            .perform(MockMvcRequestBuilders.post("/deleteAcesso")
	            .content(objectMapper.writeValueAsString(acesso))
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));

	    System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
	    System.out.println("Status da API: " + retornoApi.andReturn().getResponse().getStatus());

	    // Verificar se foi realmente deletado
	    assertEquals("Acesso Removido.", retornoApi.andReturn().getResponse().getContentAsString());
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}

	
	/*Teste end-point deletar por ID*/
	@Test
	public void testRestApiDeleteACessoPorId() throws JsonProcessingException, Exception {
	    
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
				
		// Criar um novo acesso para deletar
	    Acesso acesso = new Acesso();
	    acesso.setDescricao("ROLE_TESTE_DELETE_POR_ID");

	    // Salvar o objeto no banco
	    acesso = acessoRepository.save(acesso);

	    // Verificar se foi salvo corretamente para evitar NullPointerException
	    assertNotNull("O acesso não foi salvo corretamente.", acesso);
	    assertNotNull("O ID do acesso salvo está nulo.", acesso.getId());

	    // Criar um objeto JSON a partir da entidade
	    ObjectMapper objectMapper = new ObjectMapper(); // Json

	    // Executar a API para deletar o acesso
	    ResultActions retornoApi = mockMvc
	            .perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
	            .content(objectMapper.writeValueAsString(acesso))
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON));
	    
	    System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
	    System.out.println("Status da API: " + retornoApi.andReturn().getResponse().getStatus());

	    // Verificar se foi realmente deletado
	    assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	/*Teste end-point consultar por ID*/
	@Test
	public void testRestApiObterACessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
				
		
		// Criar um novo acesso para deletar
	    Acesso acesso = new Acesso();
	    acesso.setDescricao("ROLE_OBTER_ACESSO_POR_ID");

	    // Salvar o objeto no banco
	    acesso = acessoRepository.save(acesso);

	    // Criar um objeto JSON a partir da entidade
	    ObjectMapper objectMapper = new ObjectMapper(); // Json

	    // Executar a API para deletar o acesso
	    ResultActions retornoApi = mockMvc
	            		.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
	            		.content(objectMapper.writeValueAsString(acesso))
	    	            .accept(MediaType.APPLICATION_JSON)
	    	            .contentType(MediaType.APPLICATION_JSON));
	            
	    // Verificar se foi realmente deletado
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	    
	    Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
	    
	    assertEquals(acesso.getId(), acessoRetorno.getId());
	}


	/*Teste end-point consultar por descrição*/
	@Test
	public void testRestApiBuscarACessoPorDesc() throws JsonProcessingException, Exception {
	    
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
				
		// Criar um novo acesso
	    Acesso acesso = new Acesso();
	    acesso.setDescricao("ROLE_TEST_OBTER_LIST");

	    // Salvar o objeto no banco
	    acesso = acessoRepository.save(acesso);

	    // Criar um objeto JSON a partir da entidade
	    ObjectMapper objectMapper = new ObjectMapper(); // Json

	    // Executar a API para consultar o acesso
	    ResultActions retornoApi = mockMvc
	            		.perform(MockMvcRequestBuilders.get("/buscaPorDesc/OBTER_LIST")
	            		.content(objectMapper.writeValueAsString(acesso))
	    	            .accept(MediaType.APPLICATION_JSON)
	    	            .contentType(MediaType.APPLICATION_JSON));
	            
	    // Verificar se foi realmente consultado, status ok 200
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	    
	    List<Acesso> retornoApiList = objectMapper.
	    		readValue(retornoApi.andReturn().getResponse().getContentAsString(), 
	    		new TypeReference<List<Acesso>>() {});
	    
	    assertEquals(1, retornoApiList.size());
	    
	    assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
	    
	    acessoRepository.deleteById(acesso.getId());
	}


	
	
	@Test
	public void testeCadastraAcesso() {
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_TEST");
		//acessoRepository.save(acesso);
		
		assertEquals(true, acesso.getId() == null); // Certifica que o objeto ainda não foi salvo
		
		/*Gravou no banco de dados*/
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		assertEquals(true, acesso.getId() > 0);
		
		/*Validar dados salvo de forma correta*/
		assertEquals("ROLE_TEST", acesso.getDescricao());  
		
		/*Teste de carregamento*/
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		
		assertEquals(acesso.getId(), acesso2.getId());
		
		/*Teste de delete*/
		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush(); // roda esse SQl de delete no banco de dados
		
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(true, acesso3 == null);
		
		
		/*Teste de Query*/
		
		acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
		
		assertEquals(1, acessos.size());
		
		acessoRepository.deleteById(acesso.getId());
		
	}

}
