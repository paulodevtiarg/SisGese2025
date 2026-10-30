package br.com.sysgese.controllers;

import br.com.sysgese.dtos.ServidorDTO;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.services.ServidorService;
import br.com.sysgese.services.UnidadeService;
import br.com.sysgese.utils.AuthUtil;
import br.com.sysgese.utils.UrlUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/servidores")
public class ServidorController {

    @Autowired
    private ServidorService servidorService;

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private UrlUtils urlUtils;

    @GetMapping
    public String index(
            @ModelAttribute("filtro") ServidorDTO filtro,
            @RequestParam(defaultValue = "0") int page,
            HttpSession session,
            Model model
    ) {
        boolean isMaster = AuthUtil.isMaster(session);
        Lotacao lotacaoAtiva = (Lotacao) session.getAttribute("lotacaoUsuarioLogado");

        Long unidadeFiltro;
        if (isMaster) {
            // MASTER: Se escolheu uma unidade no filtro, usa ela.
            // Se não escolheu (null), passa null para buscar TODOS.
            unidadeFiltro = filtro.getFiltroUnidadeId();
        } else {
            // NÃO MASTER: Sempre usa a unidade da sessão
            unidadeFiltro = lotacaoAtiva.getUnidade().getId();
        }

        // Busca os servidores
        Page<ServidorDTO> pagina = servidorService.buscarServidoresDaUnidade(unidadeFiltro, filtro, page);

        // Popula o Model
        model.addAttribute("lista", pagina.getContent());
        model.addAttribute("pagina", pagina);
        model.addAttribute("isMaster", isMaster);
        model.addAttribute("activeMenu", "pessoal");
        model.addAttribute("pageTitle", "Servidores");
        model.addAttribute("queryParams", urlUtils.servidorQuery(filtro, page));

        // Se for master, envia a lista de unidades para o select do filtro
        if (isMaster) {
            model.addAttribute("unidades", unidadeService.listarTodas());
        }

        return "servidor/index";
    }
}