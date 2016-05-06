package gr.opengov.agora.search;

import org.hibernate.criterion.Criterion;

public interface ISearchParameter {
	public Criterion toHibernateCriterion();
}
