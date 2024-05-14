package com.JJoINT.CamPuzl.domain.booth.service;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import com.JJoINT.CamPuzl.domain.booth.dto.BoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.request.SaveRequestBoothDTO;
import com.JJoINT.CamPuzl.domain.booth.dto.request.UpdateRequestBoothDTO;

import java.util.List;

public interface BoothService {
    Booth save(SaveRequestBoothDTO saveRequestBoothDTO);

    List<BoothDTO> findAll();

    BoothDTO findById(Long id);

    Booth update(Long boothID, UpdateRequestBoothDTO requestDTO);

    void deleteById(Long id);

}
