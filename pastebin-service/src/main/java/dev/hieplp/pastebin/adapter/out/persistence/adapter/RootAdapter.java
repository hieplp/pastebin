package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.mapper.RootMapper;
import dev.hieplp.pastebin.adapter.out.persistence.repository.RootRepository;
import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import dev.hieplp.pastebin.application.port.out.root.GetRootPort;
import dev.hieplp.pastebin.application.port.out.root.SaveRootPort;
import dev.hieplp.pastebin.domain.model.Root;
import dev.hieplp.pastebin.domain.vo.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RootAdapter implements SaveRootPort, ExistRootPort, GetRootPort {

    private final RootMapper rootMapper;
    private final RootRepository rootRepo;

    @Override
    public Root save(Root root) {
        return rootMapper.toModel(
                rootRepo.save(
                        rootMapper.toEntity(root)
                )
        );
    }

    @Override
    public boolean exists() {
        return rootRepo.count() > 0;
    }

    @Override
    public Optional<Root> findByUsername(Username username) {
        if (username == null || username.value() == null || username.value().isBlank()) {
            return Optional.empty();
        }
        return rootRepo.findByUsername(username.value()).map(rootMapper::toModel);
    }


}
