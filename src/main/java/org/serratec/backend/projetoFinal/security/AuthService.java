package org.serratec.backend.projetoFinal.security;

import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.serratec.backend.projetoFinal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
	
	@Autowired
	ClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClienteEntity cliente = repository.findByUsername(username);
		if(cliente == null) {
			System.out.println("Usuario não existe");
			return null;
		}
		
		return new UserSS(cliente.getId(),cliente.getUsername(),cliente.getSenha());
	}
	

}