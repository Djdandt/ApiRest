package br.com.everis.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.everis.model.Aluno;
import br.com.everis.repository.Alunos;

//Adiciona o annotation controller e responsebody por default e converte a resposta recebida para um JSON/XML
@RestController
//Especifica o valor do caminho
@RequestMapping("/alunos")
public class AlunosResource {

	//Spring configura automaticamente o que precisa ser injetado
	@Autowired
	private Alunos alunos;

	//Quando alguém fizer uma requisição POST irá acionar esse metodo
	@PostMapping
	//Valid só deixa o metodo ser executado se passar nas validações
	//RequestBody pega o que está vindo no corpo da requisição e joga em aluno
	public Aluno adicionar(@Valid @RequestBody Aluno aluno) {
		//irá adicionar o aluno passado pelo parametro na lista de alunos
		return alunos.save(aluno);
	}
	
	@GetMapping
	//Quando alguém fizer uma requisição GET irá acionar esse metodo
	public List<Aluno> listar(){
		return alunos.findAll();
	}
	
	@GetMapping("/{id}") // Quando chamar /contatos/numero do id ele irá retornar esse metódo
	//PathVariable avisa que o valor do parametro será o id passado
	public ResponseEntity<Aluno> buscar(@PathVariable Long id){
		Aluno aluno = alunos.getOne(id);
		
		//Retorna um erro 404 se a resposta vier vazia
		if(aluno == null) {
			return ResponseEntity.notFound().build();
		}
		
		//Se não for nulo ele retorna um 200 pegando o aluno com o id informado
		return ResponseEntity.ok(aluno);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @Valid @RequestBody Aluno aluno){
		Aluno existente =alunos.getOne(id);
		
		if(existente == null) {
			return ResponseEntity.notFound().build();
		}
		
		//irá copiar o que está no existente para aluno
		BeanUtils.copyProperties(aluno, existente, "id");
		
		existente = alunos.save(existente);
		return ResponseEntity.ok(existente);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id){
		Aluno aluno = alunos.getOne(id);
		
		if(aluno == null) {
			return ResponseEntity.notFound().build();
		}
		
		//deleta o aluno passado como parametro da lista alunos
		alunos.delete(aluno);
		
		//irá retornar um 204 página sem conteudo
		return ResponseEntity.noContent().build();
	}
	
}
