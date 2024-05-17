package dev.hieplp.pastebin.common.listener;

import dev.hieplp.pastebin.common.entity.AbstractAuditEntity;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Configurable
public class CustomAuditingEntityListener extends AuditingEntityListener {
    @Override
    public void touchForCreate(Object target) {
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
    public void touchForUpdate(Object target) {
        var entity = (AbstractAuditEntity) target;
        if (entity.getLastModifiedBy() == null) {
            super.touchForUpdate(target);
        }
    }
}