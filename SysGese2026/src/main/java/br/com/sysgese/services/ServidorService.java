package br.com.sysgese.services;

import br.com.sysgese.dtos.ServidorDTO;
import br.com.sysgese.mappers.ServidorMapper;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.LotacaoRepository;
import br.com.sysgese.repository.ServidorRepository;
import br.com.sysgese.specifications.ServidorSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServidorService {

    @Autowired
    private ServidorRepository servidorRepository;

    @Autowired
    private LotacaoRepository lotacaoRepository;

    @Autowired
    private ServidorMapper servidorMapper;

    public Optional<Servidor> buscarPorId(Long id) {
        return servidorRepository.findById(id);
    }

    public Optional<Servidor> buscarPorLoginCpfOuMatricula(String login, String cpf, String matricula) {
        return servidorRepository.findByLoginOrCpfOrMatricula(login, cpf, matricula);
    }

    @Transactional(readOnly = true)
    public Page<ServidorDTO> buscarServidoresDaUnidade(
            Long idUnidadeServidor,
            ServidorDTO filtro,
            int page
    ) {
        int size = filtro.getSize() != null ? filtro.getSize() : 10;

        // Ordenação padrão: mais recentes primeiro, depois por nome
        Sort sort = Sort.by(Sort.Direction.DESC, "dataCad").and(Sort.by("nome"));
        Pageable pageable = PageRequest.of(page, size, sort);

        // 🔥 Construindo a Specification
        Specification<Servidor> spec = Specification.allOf();

        // Se veio uma unidade (mesmo que seja do usuário logado), aplica
        if (idUnidadeServidor != null) {
            spec = spec.and(ServidorSpecification.unidade(idUnidadeServidor));
        }

        spec = spec.and(ServidorSpecification.nome(filtro.getFiltroNome()));
        spec = spec.and(ServidorSpecification.matricula(filtro.getFiltroMatricula()));
        spec = spec.and(ServidorSpecification.cidade(filtro.getFiltroCidade()));
        spec = spec.and(ServidorSpecification.cpf(filtro.getFiltroCpf()));
        spec = spec.and(ServidorSpecification.statusCadastro(filtro.getFiltroStatus()));

        // Busca no banco
        Page<Servidor> servidores = servidorRepository.findAll(spec, pageable);

        // Pega os IDs dos servidores da página atual
        Set<Long> idsServidores = servidores.getContent().stream()
                .map(Servidor::getId)
                .collect(Collectors.toSet());

        // Busca as lotações ativas desses servidores
        // 🔥 Corrigido o nome do método para bater com o Repository
        List<Lotacao> lotacoesAtivas = lotacaoRepository.findByServidorIdInAndStatusAtivo(idsServidores);

        // Cria um mapa para buscar o nome da unidade rapidamente: ID do Servidor -> Nome da Unidade
        Map<Long, String> mapaUnidadeLotacao = lotacoesAtivas.stream()
                .collect(Collectors.toMap(
                        l -> l.getServidor().getId(),
                        l -> l.getUnidade().getNome(),
                        (a, b) -> a // Se houver duplicata, mantém o primeiro
                ));

        // 🔥 Mapeia para DTO preenchendo a unidade e retorna (o return antigo foi removido)
        return servidores.map(servidor -> {
            ServidorDTO dto = servidorMapper.toDTO(servidor);
            dto.setNomeUnidadeLotacao(mapaUnidadeLotacao.get(servidor.getId()));
            return dto;
        });
    }

    @Transactional
    public Servidor salvar(ServidorDTO dto) {
        Servidor entity;

        if (dto.getId() != null) {
            entity = servidorRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Servidor não encontrado."));

            if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
                dto.setSenha(entity.getSenha());
            }

            servidorMapper.updateEntityFromDTO(dto, entity);

        } else {
            if (dto.getCpf() != null) {
                Optional<Servidor> servidorExistente = servidorRepository.findByLoginOrCpfOrMatricula(
                        dto.getLogin(), dto.getCpf(), dto.getMatricula()
                );

                if (servidorExistente.isPresent()) {
                    throw new RuntimeException("Já existe um servidor cadastrado com este Login, CPF ou Matrícula.");
                }
            }

            entity = servidorMapper.toEntity(dto);

            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }
            if (entity.getPrimeiroAcesso() == null) {
                entity.setPrimeiroAcesso(true);
            }
        }

        return servidorRepository.save(entity);
    }
}