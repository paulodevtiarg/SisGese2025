package br.com.sysgese.services;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.ServidorRepository;

@Service
public class CodigoSegurancaService {
	
	@Autowired
    private ServidorRepository usuarioRepository;

    @Autowired
    private EmailService emailService;
	
	public String gerarCodigo() {
	    SecureRandom random = new SecureRandom();
	    int codigo = 100000 + random.nextInt(900000);
	    return String.valueOf(codigo);
	}
	public void prepararPrimeiroAcesso(Servidor usuario) {

	    String codigo = gerarCodigo();

	    usuario.setPrimeiroAcesso(true);

	    usuario.setCodigoSeguranca(hash(codigo));

	    usuario.setDataExpiracao(
	        LocalDateTime.now().plusMinutes(15)
	    );

	    usuarioRepository.save(usuario);

	    emailService.enviarCodigo(usuario.getEmail(), codigo);
	}
	
	public String hash(String input) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hash = md.digest(input.getBytes());
	        StringBuilder sb = new StringBuilder();

	        for (byte b : hash) {
	            sb.append(String.format("%02x", b));
	        }

	        return sb.toString();

	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
