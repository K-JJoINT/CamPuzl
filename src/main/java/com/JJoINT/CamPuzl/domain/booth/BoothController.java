package com.JJoINT.CamPuzl.domain.booth;

import com.JJoINT.CamPuzl.domain.booth.domain.Booth;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoothController {

    @PatchMapping("/booth/{id}")
    public ResponseEntity<String> updateBooth(@PathVariable("id") Long boothID, @RequestBody BoothDTO boothDTO) {
        Booth booth = boothService.update(boothID, boothDTO);
        return ResponseEntity.ok().build();
    }

}
