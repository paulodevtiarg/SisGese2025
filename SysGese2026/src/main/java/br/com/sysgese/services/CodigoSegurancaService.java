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

	


}
