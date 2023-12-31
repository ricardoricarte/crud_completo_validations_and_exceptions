package br.com.cliente.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.cliente.domain.exceptions.NegocioException;
import br.com.cliente.domain.model.Cliente;
import br.com.cliente.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClienteService {

	private ClienteRepository clienteRepository;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
						.stream()
						.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		if (emailEmUso) {
			throw new NegocioException("Ja existe um cliente cadastrado com este e-mail");
		}
		return clienteRepository.save(cliente);

	}

	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}