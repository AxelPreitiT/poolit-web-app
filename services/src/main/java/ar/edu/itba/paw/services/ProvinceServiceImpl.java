package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProvinceDao;
import ar.edu.itba.paw.interfaces.services.ProvinceService;
import ar.edu.itba.paw.models.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceDao provinceDao;

    @Autowired
    public ProvinceServiceImpl(final ProvinceDao provinceDao){
        this.provinceDao = provinceDao;
    }

    @Override
    public Optional<Province> findProvinceById(long id) {
        return provinceDao.findProvinceById(id);
    }

    @Override
    public List<Province> getAllProvinces() {
        return provinceDao.getAllProvinces();
    }
}
