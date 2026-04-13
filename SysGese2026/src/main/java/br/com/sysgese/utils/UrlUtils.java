package br.com.sysgese.utils;

import org.springframework.stereotype.Component;

import br.com.sysgese.dtos.AdolescenteDTO;

@Component
public class UrlUtils {
	public String adolescenteQuery(AdolescenteDTO filtro, int page) {
		 return String.format(
			        "filtroNome=%s&filtroApelido=%s&filtroCidade=%s&filtroCpf=%s&filtroUnidadeId=%s&idadeMin=%s&idadeMax=%s&filtroStatus=%s&size=%s&page=%d",
			        filtro.getFiltroNome() != null ? filtro.getFiltroNome() : "",
			        filtro.getFiltroApelido() != null ? filtro.getFiltroApelido() : "",
			        filtro.getFiltroCidade() != null ? filtro.getFiltroCidade() : "",
			    	filtro.getFiltroCpf() != null ? filtro.getFiltroCpf() : "",
			        filtro.getFiltroUnidadeId() != null ? filtro.getFiltroUnidadeId() : "",
			        filtro.getIdadeMin() != null ? filtro.getIdadeMin() : "",
			        filtro.getIdadeMax() != null ? filtro.getIdadeMax() : "",
			        filtro.getFiltroStatus() != null ? filtro.getFiltroStatus() : "ATIVO",
			        filtro.getSize() != null ? filtro.getSize() : 10,
			        page
			    );
    }


}