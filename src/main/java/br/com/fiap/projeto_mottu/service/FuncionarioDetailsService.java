package br.com.fiap.projeto_mottu.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.projeto_mottu.model.Funcionario;
import br.com.fiap.projeto_mottu.repository.FuncionarioRepository;

@Service
public class FuncionarioDetailsService implements UserDetailsService {
	
	@Autowired
	private FuncionarioRepository repF;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Funcionario funcionario = repF.findByUsername(username)
				.orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado") );
		
		return new User(funcionario.getNm_email_corporativo(), funcionario.getNm_senha(),
				funcionario.getFuncoes().stream()
				.map(funcao -> new SimpleGrantedAuthority(funcao.getNm_funcao().toString()))
				.collect(Collectors.toList()));
	}
	
	
	
	

}