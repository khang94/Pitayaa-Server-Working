package pitayaa.nail.msg.core.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.product.ProductModel;
import pitayaa.nail.msg.core.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepository repo;

	@Override
	public Optional<ProductModel> findOne(UUID id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repo.findOne(id)) ;
	}

	@Override
	public List<ProductModel> findAll(String salonId) {
		// TODO Auto-generated method stub
		return repo.findAll(salonId);
	}

	@Override
	public ProductModel save(ProductModel model) throws Exception {
		// TODO Auto-generated method stub
		return repo.save( model);
	}

}