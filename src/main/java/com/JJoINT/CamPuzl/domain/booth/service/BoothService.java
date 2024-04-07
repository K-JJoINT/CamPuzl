package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;

import java.util.List;

public interface BoothService {
    Booth save(BoothDTO boothDTO);

    List<BoothDTO> findAll();

    BoothDTO findById(Long id);

    Booth update(Long boothID, BoothDTO boothDTO);

    void deleteById(Long id);

}
