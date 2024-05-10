package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.saveRequestBoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.updateRequestBoothDTO;

import java.util.List;

public interface BoothService {
    Booth save(saveRequestBoothDTO saveRequestBoothDTO);

    List<BoothDTO> findAll();

    BoothDTO findById(Long id);

    Booth update(Long boothID, updateRequestBoothDTO requestDTO);

    void deleteById(Long id);

}
