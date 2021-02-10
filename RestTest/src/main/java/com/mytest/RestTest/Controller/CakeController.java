package com.mytest.RestTest.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mytest.RestTest.Exceptions.cakeNotFoundException;
import com.mytest.RestTest.Model.Cake;
import com.mytest.RestTest.Repository.CakeRepo;

@RestController
public class CakeController {
	
	@Autowired
	private CakeRepo cakerepository;
	
	
	@GetMapping(path= "/cakes", produces = "application/json")
	 public List <Cake> retrieveAllCakes(){
		return cakerepository.findAll();
	}

	@GetMapping(path="/cakes/{id}",produces = "application/json")
	public Cake retrieveCake(@PathVariable Long id) {
		Optional < Cake> cake = cakerepository.findById(id);
		
		if (!cake.isPresent())
			throw new cakeNotFoundException("id-" + id);

		return cake.get();
	}
	@PostMapping(path="/cakes", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addCake(@RequestBody Cake cake) {
		Cake savedcake = cakerepository.save(cake);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedcake.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	  @PutMapping(path="/cakes/{id}")
	  public Cake updateCake(@RequestBody Cake cake , @PathVariable Long id) {
		  return cakerepository.findById(id)
	      .map(Cake -> {
	          cake.setName(cake.getName());
	          cake.setType(cake.getType());
	          return cakerepository.save(cake);
	        })
	        .orElseGet(() -> {
	          cake.setId(id);
	          return cakerepository.save(cake);
	        });
		  
	  }
	  @DeleteMapping("/cakes/{id}")
	  void deleteEmployee(@PathVariable Long id) {
	    cakerepository.deleteById(id);
	  }
}
