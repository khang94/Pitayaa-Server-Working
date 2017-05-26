package pitayaa.nail.msg.core.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.product.ProductModel;

public interface ProductService {
	Optional<ProductModel> findOne(UUID id);

	List<ProductModel> findAll(String salonId);

	ProductModel save(ProductModel  model) throws Exception;

	void delete(ProductModel model);
}