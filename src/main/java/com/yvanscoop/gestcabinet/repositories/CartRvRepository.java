package com.yvanscoop.gestcabinet.repositories;

import com.yvanscoop.gestcabinet.entities.CartRv;
import com.yvanscoop.gestcabinet.entities.Rv;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CartRvRepository extends CrudRepository<CartRv, Long> {

	@Query("select crv from CartRv crv where crv.client.id=?1  and crv.jour > NOW() order by crv.jour,crv.creneau.hdebut,crv.creneau.mdebut")
    List<CartRv> getCartRvByClient(Long idClient);

	@Query("select crv from CartRv crv left join fetch crv.client c left join fetch crv.creneau cr where cr.medecin.matricule=?1 and crv.jour=?2 and c.id=?3 and cr.id =?4")
	CartRv getCartRvMedecinJourByCreneau(String matricule, Date jour,Long idclient,Long idcreneau);

	@Query("select crv from CartRv crv left join fetch crv.client c left join fetch crv.creneau cr where cr.medecin.matricule=?1 and crv.jour=?2 and c.id=?3")
	List<CartRv> getCartRvMedecinJour(String matricule, Date jour,Long idclient);

	CartRv getCartRvById(Long id);
}
