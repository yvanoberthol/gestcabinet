package com.yvanscoop.gestcabinet.repositories;

import com.yvanscoop.gestcabinet.entities.Rv;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RvRepository extends CrudRepository<Rv, Long> {
	@Query("select rv from Rv rv left join fetch rv.client c left join fetch rv.creneau cr where cr.medecin.matricule=?1 and rv.jour=?2")
	List<Rv> getRvMedecinJour(String matricule, Date jour);

	@Query("select rv from Rv rv where rv.client in (select c from Client c where c.id = ?1) and rv.annule = false and rv.jour > NOW() order by rv.jour,rv.creneau.hdebut,rv.creneau.mdebut")
    List<Rv> getRvByClient(Long idClient);

	Rv getById(Long id);
	@Query("select rv from Rv rv left join fetch rv.client c left join fetch rv.creneau cr where cr.medecin.matricule=?1 and rv.jour=?2 and rv.annule = false")
	List<Rv> getRvMedecinJourPris(String matricule, Date date);

	@Query("select rv from Rv rv where rv.client in (select c from Client c where c.username = ?1) and rv.annule = false and rv.jour > NOW() order by rv.jour,rv.creneau.hdebut,rv.creneau.mdebut")
    List<Rv> getRvByClientName(String name);
}
