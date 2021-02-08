package com.mytest.RestTest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytest.RestTest.Model.Cake;

public interface CakeRepo extends JpaRepository<Cake,Long> {

}
