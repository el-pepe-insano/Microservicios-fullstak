ackage com.Mediexpress.CarritoDeCompras.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mediexpress.CarritoDeCompras.model.CarritoItem;

@Repository
public interface CarritoRepositorio extends JpaRepository<CarritoItem, Long> {
    
    List<CarritoItem> findByIdCliente(Long idCliente); 
    
    void deleteByIdCliente(Long idCliente);
}