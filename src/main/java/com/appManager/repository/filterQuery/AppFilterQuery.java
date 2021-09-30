package com.appManager.repository.filterQuery;

import com.appManager.controller.filter.AppFilter;
import com.appManager.model.AppModel;
import com.appManager.model.AppModel_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class AppFilterQuery {

    private AppFilter filter;

    public Specification<AppModel> buildFilter() {
        return Specification
                .where(enableFetchOrderByPriority())
                .and(matchAppName())
                .and(matchType());
    }

    private Specification<AppModel> matchAppName() {
        return (root, query, cb) -> {
            if (filter.getName() == null || filter.getName().isEmpty()) {
                return null;
            }

            return cb.like(cb.lower(root.get(AppModel_.name)), "%" + filter.getName().toLowerCase() + "%");
        };
    }

    private Specification<AppModel> matchType() {
        return (root, query, cb) -> {
            if (filter.getType() == null || filter.getType().isEmpty()) {
                return null;
            }
            return cb.like(root.join(AppModel_.type).get("name"), filter.getType());
        };
    }

    private Specification<AppModel> enableFetchOrderByPriority() {
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(root.get(AppModel_.name)),
                    cb.asc(root.get(AppModel_.type.getName())),
                    cb.asc(root.get(AppModel_.price)));
            return null;
        };
    }
}
