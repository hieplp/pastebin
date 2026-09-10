package dev.hieplp.pastebin.adapter.out.jpa.adapter;

import dev.hieplp.pastebin.adapter.out.jpa.mapper.RootMapper;
import dev.hieplp.pastebin.adapter.out.jpa.repository.RootRepository;
import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import dev.hieplp.pastebin.application.port.out.root.GetRootPort;
import dev.hieplp.pastebin.application.port.out.root.SaveRootPort;
import dev.hieplp.pastebin.domain.model.Root;
import dev.hieplp.pastebin.domain.vo.Username;
import dev.hieplp.pastebin.domain.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "pastebin.persistence.type", havingValue = "jpa", matchIfMissing = true)
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
        if (username == null) {
            return Optional.empty();
        }
        return Strings.trim(username.value())
                .flatMap(name -> rootRepo.findByUsername(name).map(rootMapper::toModel));
    }


}
