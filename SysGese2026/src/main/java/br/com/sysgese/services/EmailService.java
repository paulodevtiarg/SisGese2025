package br.com.sysgese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailService {

	private final RestClient restClient;

	private final String apiKey;
	private final String remetente;

	public EmailService(
			@Value("${resend.api.key}") String apiKey,
			@Value("${resend.from}") String remetente) {

		this.apiKey = apiKey;
		this.remetente = remetente;

		this.restClient = RestClient.builder()
				.baseUrl("https://api.resend.com")
				.build();
	}

    public void enviarCodigo(String email, String codigo, Boolean primeiroAcesso) {

        	
    	 try {

    	        // 🔥 Título do email (assunto)

			 String assunto = Boolean.TRUE.equals(primeiroAcesso)
							 ? "Primeiro Acesso - SYSGESE"
							 : "Recuperação de Senha - SYSGESE";


    	        // 🔥 Conteúdo dinâmico
    	        String titulo = Boolean.TRUE.equals(primeiroAcesso)
    	                ? "Bem-vindo ao SYSGESE"
    	                : "Código de Recuperação de Senha";

    	        String mensagem = Boolean.TRUE.equals(primeiroAcesso)
    	                ? "Utilize o código abaixo para criar sua senha de acesso:"
    	                : "Utilize o código abaixo para redefinir sua senha:";
    	        String html = """
    	        		<div style="font-family: Arial, sans-serif; background-color:#f4f6f9; padding:20px;">

    	        		    <div style="max-width:600px; margin:0 auto; background:white; border-radius:12px; overflow:hidden; box-shadow:0 4px 20px rgba(0,0,0,0.1);">
    	        		    
    	        		        <!-- HEADER -->
    	        		        <div style="background: linear-gradient(135deg, #1E2A5A, #2F3B73); padding:20px; text-align:center;">
    	        		            <img src="https://res.cloudinary.com/dyl8tkovm/image/upload/v1776106490/unidades/logo_transp_ctaphd.png" 
    	        		                 alt="Brasão" 
    	        		                 style="width:70px; margin-bottom:10px;">
    	        		            
    	        		            <h2 style="color:#F2C230; margin:0;">SYSGESE</h2>
    	        		            <p style="color:white; margin:5px 0 0 0;">Sistema de Gestão Socioeducativo</p>
    	        		        </div>

    	        		        <!-- BODY -->
    	        		        <div style="padding:30px; text-align:center;">
    	        		        
    	        		            <h3 style="color:#1E2A5A;">%s</h3>
    	        		            
    	        		            <p style="font-size:15px; color:#555;">
    	        		                %s
    	        		            </p>

    	        		            <!-- CODE BOX -->
    	        		            <div style="
    	        		                margin:20px auto;
    	        		                font-size:28px;
    	        		                font-weight:bold;
    	        		                letter-spacing:6px;
    	        		                color:#1E2A5A;
    	        		                background:#f1f3f7;
    	        		                padding:15px;
    	        		                border-radius:10px;
    	        		                display:inline-block;
    	        		                border:2px dashed #F2C230;">
    	        		                %s
    	        		            </div>

    	        		            <p style="font-size:13px; color:#777; margin-top:20px;">
    	        		                Este código é válido por <b>15 minutos</b>.<br>
    	        		                Se você não solicitou isso, ignore este e-mail.
    	        		            </p>

    	        		        </div>

    	        		        <!-- FOOTER -->
    	        		        <div style="background:#1E2A5A; color:white; text-align:center; padding:12px; font-size:12px;">
    	        		            © SYSGESE - Sistema Socioeducativo
    	        		        </div>

    	        		    </div>
    	        		</div>
    	        		""".formatted(titulo, mensagem, codigo);

			 Map<String, Object> body = Map.of(
					 "from", remetente,
					 "to", List.of(email),
					 "subject", assunto,
					 "html", html
			 );

			 restClient
					 .post()
					 .uri("/emails")
					 .header(
							 "Authorization",
							 "Bearer " + apiKey
					 )
					 .body(body)
					 .retrieve()
					 .toBodilessEntity();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
}
