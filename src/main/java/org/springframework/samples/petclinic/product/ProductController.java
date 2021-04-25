package org.springframework.samples.petclinic.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private final ProductRepository products;

    public ProductController(ProductRepository products) {
        this.products = products;
    }

    @GetMapping({"/API/productsJSON"})
    @CrossOrigin(origins ="*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    public @ResponseBody ProductsJSON showResourceProductListJSON() {
        ProductsJSON products = new ProductsJSON();

        products.getProductList().addAll(this.products.findAll());
        return products;
    }

}
