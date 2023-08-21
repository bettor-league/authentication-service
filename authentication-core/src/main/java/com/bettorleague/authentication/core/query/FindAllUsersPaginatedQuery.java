package com.bettorleague.authentication.core.query;

import com.bettorleague.microservice.cqrs.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllUsersPaginatedQuery extends BaseQuery {
    private Pageable pageable;
}
