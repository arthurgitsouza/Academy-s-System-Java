package com.wintech.portal.service;

import com.wintech.portal.domain.Questao;
import com.wintech.portal.repository.QuestaoRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestaoService {

    private final QuestaoRepository repository;
    public QuestaoService(QuestaoRepository repository) {
        this.repository = repository;
    }

    public Questao salvar(Questao novaQuestao)  {
        return repository.save(novaQuestao);
    }

}
