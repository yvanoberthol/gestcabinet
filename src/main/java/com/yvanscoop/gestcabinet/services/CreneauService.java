package com.yvanscoop.gestcabinet.services;


import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.repositories.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service 
@Transactional
public class CreneauService {
	
	@Autowired
	private CreneauRepository creneauRepository;
	
	public List<Creneau> getAllByMedecin(String mot){
		
		return creneauRepository.findWithMedecin(mot+"%");
		
	}
	
	public Page<Creneau> findPaginated(String matricule,Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Creneau> list;
 
        if (getAllByMedecin(matricule).size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, getAllByMedecin(matricule).size());
            list = getAllByMedecin(matricule).subList(startItem, toIndex);
        }
 
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), getAllByMedecin(matricule).size());
    }
	
	public Creneau getCrenauById(Long id) {
		return creneauRepository.getOne(id);
	}
	
	public boolean exists(Long id) {
		return creneauRepository.existsById(id);
	}
	
	public Creneau save(Creneau Creneau) {
		return creneauRepository.save(Creneau);
	}
	
	public void delete(Long id) {
		creneauRepository.deleteById(id);
	}
	
	//absolu
	public Creneau getCreneauByHdebutMedecin(int hdebut, int mdebut, String matricule) {
		return creneauRepository.findHdebutByMedecin(hdebut, mdebut, matricule);
	}
	
	public Creneau getCreneauByHfinMedecin(int hfin, int mfin, String matricule) {
		return creneauRepository.findHfinByMedecin(hfin, mfin, matricule);
	}

	//intervalle
	public List<Creneau> findHDMsupouegalHDA(int hde, int mde, String matricule){
		return creneauRepository.findHDMsupouegalHDA(hde,mde, matricule);
	}
	
	public List<Creneau> findHFMinfouegalHDAP(int hfe, String matricule){
		return creneauRepository.findHFMinfouegalHDAP(hfe, matricule);
	}

}
