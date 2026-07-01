package br.com.sysgese.services;

import br.com.sysgese.dtos.RelatorioDashboardDTO;
import br.com.sysgese.dtos.RelatorioDashboardFiltroDTO;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    @Autowired
    private SpringTemplateEngine templateEngine;
    public String gerarHtmlRelatorio(RelatorioDashboardDTO relatorio,
                                     RelatorioDashboardFiltroDTO filtro) {

        Context context = new Context();
        context.setVariable("relatorio", relatorio);
        context.setVariable("filtro", filtro);

        String html = templateEngine.process("relatorios/dashboard-pdf", context);

        // LOG para verificar o HTML gerado
        System.out.println("HTML gerado: " + html.substring(0, Math.min(html.length(), 500)));

        return html;
    }

    public byte[] converterHtmlParaPdf(String html) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, "file:/");
            builder.toStream(os);
            builder.useFastMode();

            builder.run();

            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

}