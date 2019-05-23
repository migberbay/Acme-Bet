package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Category;


@Service
@Transactional
public class CategoryService {

	//Managed Repository -----
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Category create(){
		Category res = new Category();
		
		return res;
	}
	
	public Collection<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findOne(int Id){
		return categoryRepository.findOne(Id);
	}
	
	public Category save(Category a){
		
		Category saved = categoryRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Category a){
		categoryRepository.delete(a);
	}
	
	//Other business methods -----

	public Collection<Category> getPoolCategories(){
		return categoryRepository.getPoolCategories();
	}
	
	public Collection<Category> getRequestCategories(){
		return categoryRepository.getRequestCategories();
	}
}