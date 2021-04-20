package org.univaq.oop.business;


import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Prescrizione;

import java.util.List;

public interface FarmacoService {

    List<Farmaco> trovaTuttiFarmaci() throws BusinessException;

    void aggiungiFarmaco(Farmaco farmaco) throws BusinessException;

    void aggiornaFarmaco(Farmaco farmaco) throws BusinessException;

    Farmaco trovaFarmacoDaId(Integer codice) throws BusinessException;

    void eliminaFarmaco(Long codice) throws BusinessException;

    void aggiornaQtaFarmaco(Prescrizione prescrizione) throws BusinessException;//, NonCiSonoAbbastanzaFarmaci, FarmacoNonTrovato;

    List<String> findAllFarmaciByNome() throws BusinessException;

    Farmaco findFarmacoByName(String string) throws BusinessException;

    List<Farmaco> trovaFarmaciInEsaurimento() throws BusinessException;
}
