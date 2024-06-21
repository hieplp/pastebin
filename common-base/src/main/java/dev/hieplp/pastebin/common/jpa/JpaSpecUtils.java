package dev.hieplp.pastebin.common.jpa;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class JpaSpecUtils {
    public static <T> Specification<T> equal(String field, Object value) {
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    public static <T> Specification<T> notEqual(String field, Object value) {
        return (root, query, cb) -> cb.notEqual(root.get(field), value);
    }

    public static <T> Specification<T> like(String field, String value) {
        return (root, query, cb) -> cb.like(root.get(field), "%" + value + "%");
    }

    public static <T> Specification<T> where(List<Specification<T>> specifications) {
        var where = Specification.where(specifications.removeFirst());
        for (var specification : specifications) {
            where = where.and(specification);
        }
        return where;
    }

}
