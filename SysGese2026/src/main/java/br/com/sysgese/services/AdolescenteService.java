package br.com.sysgese.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sysgese.dtos.AdolescenteDTO;
import br.com.sysgese.mappers.AdolescenteMapper;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.repository.InternacaoRepository;
import br.com.sysgese.specifications.InternacaoSpecification;

@Service
public class AdolescenteService {

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private AdolescenteMapper mapper;

    @Autowired
    private LotacaoService lotacaoService;

    public Page<AdolescenteDTO> buscarAdolescentesInternadosNaUnidade(
            Long idUnidadeServidor,
            String nome,
            String apelido,
            String cidade,
            String cpf,
            Integer idadeMin,
            Integer idadeMax,
            Integer[] status,
            Pageable pageable
    ) {

        LocalDate hoje = LocalDate.now();

        LocalDate dataInicioNascimento = LocalDate.of(1900, 1, 1);
        LocalDate dataFimNascimento = hoje;

        if (idadeMin != null) dataFimNascimento = hoje.minusYears(idadeMin);
        if (idadeMax != null) dataInicioNascimento = hoje.minusYears(idadeMax + 1).plusDays(1);

        Integer[] filtroStatus = status != null ? status : new Integer[]{1};

        Specification<Internacao> spec =
                Specification.where(InternacaoSpecification.unidadeId(idUnidadeServidor))
                        .and(InternacaoSpecification.statusInternacaoAtiva())
                        .and(InternacaoSpecification.nome(nome))
                        .and(InternacaoSpecification.apelido(apelido))
                        .and(InternacaoSpecification.cidade(cidade))
                        .and(InternacaoSpecification.cpf(cpf))
                        .and(InternacaoSpecification.nascimentoEntre(dataInicioNascimento, dataFimNascimento))
                        .and(InternacaoSpecification.statusCadastro(filtroStatus));

        Page<Internacao> internacoes = internacaoRepository.findAll(spec, pageable);

        List<AdolescenteDTO> adolescentesDTO = internacoes.stream()
                .map(Internacao::getAdolescente)
                .distinct()
                .map(this::mapToDTOComFoto)
                .toList();

        return new PageImpl<>(adolescentesDTO, pageable, internacoes.getTotalElements());
    }

    
    
    private AdolescenteDTO mapToDTOComFoto(Adolescente adolescente) {
        AdolescenteDTO dto = mapper.toDTO(adolescente);
        if (adolescente.getFotos() != null && !adolescente.getFotos().isEmpty()) {
            dto.setFotoPrincipal(adolescente.getFotos().get(0).getFoto());
        }
        return dto;
    }
}