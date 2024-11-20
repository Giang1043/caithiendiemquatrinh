package vn.lightforknight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.lightforknight.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
