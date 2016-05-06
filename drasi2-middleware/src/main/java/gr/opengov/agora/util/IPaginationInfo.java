package gr.opengov.agora.util;

public interface IPaginationInfo {

	public abstract Integer getFrom();

	public abstract void setFrom(Integer from);

	public abstract Integer getLimit();

	public abstract void setLimit(Integer limit);

	public abstract Integer getTotal();

	public abstract void setTotal(Integer total);

}