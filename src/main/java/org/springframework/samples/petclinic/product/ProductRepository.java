package org.springframework.samples.petclinic.product;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends Repository<Product, Integer> {
    @Transactional(readOnly = true)
    Collection<Product> findAll() throws DataAccessException;
    Product findById(Integer id);
    void save(Product product);
    void delete(Product product);
}
