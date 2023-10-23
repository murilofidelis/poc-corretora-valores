package br.com.github.homebroker.api.service;

import br.com.github.homebroker.api.domain.Share;
import br.com.github.homebroker.api.repoistory.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepository repository;

    public List<Share> gteAll() {
        return repository.findAll();
    }
}
