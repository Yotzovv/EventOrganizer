package com.event.organizer.api.search;
/**Search function.*/
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class Search<T> {
    
    List<SearchCriteria> criterias;

    public Search(List<SearchCriteria> criterias) {
        this.criterias = criterias;
    }

    public Specification<T> getSpecificationList() {
        Specification<T> specifications = Specification.where(
            new BaseSpecification<T>(criterias.get(0))
        );

        for (int i = 1; i < criterias.size(); i++) {
            specifications = specifications.and(new BaseSpecification<T>(criterias.get(i)));
        }

        return specifications;
    }

}
