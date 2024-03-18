package rva.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

	List<T> getAll();
	
	boolean existsById(int id);
	
	Optional<T> findById(int id);
	
	T create (T t);
	
	Optional<T> update(T t, int id);
	
	void delete(int id);
}
