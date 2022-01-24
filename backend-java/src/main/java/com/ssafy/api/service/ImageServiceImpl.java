package com.ssafy.api.service;

import com.ssafy.api.dto.ImageDto;
import com.ssafy.db.entity.Image;
import com.ssafy.db.repository.ImageRepository;
import com.ssafy.db.repository.ImageRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("imageService")
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageRepositorySupport imageRepositorySupport;

    @Override
    @Transactional
    public Image addImage(ImageDto imageDto){
        return imageRepository.save(imageDto.toEntity());
    }

//    @Override
//    @Transactional
//    public ImageDto getImage(Long id){
//        Image image = imageRepository.findById(id).get();
//
//        ImageDto imageDto = ImageDto.builder()
//                .id
//    }
}
