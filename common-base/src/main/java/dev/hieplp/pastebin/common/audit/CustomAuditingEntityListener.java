package dev.hieplp.pastebin.common.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

@Slf4j
@Configurable
public class CustomAuditingEntityListener extends AuditingEntityListener {
    @Override
    public void touchForCreate(@NonNull Object target) {
        var entity = (AbstractAuditEntity) target;

        if (entity.getCreatedBy() == null) {
            super.touchForCreate(target);
            return;
        }

        if (entity.getLastModifiedBy() == null) {
            entity.setLastModifiedBy(entity.getCreatedBy());
        }
    }

    @Override
    public void touchForUpdate(@NonNull Object target) {
        log.warn("touchForUpdate");
        var entity = (AbstractAuditEntity) target;
        if (entity.getLastModifiedBy() == null) {
            super.touchForUpdate(target);
        }
    }
}