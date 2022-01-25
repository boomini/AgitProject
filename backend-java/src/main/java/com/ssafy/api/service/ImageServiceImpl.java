package com.ssafy.api.service;

import com.ssafy.api.dto.ImageDto;
import com.ssafy.db.entity.Image;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ImageRepository;
import com.ssafy.db.repository.ImageRepositorySupport;
import com.ssafy.db.repository.TeamRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("imageService")
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageRepositorySupport imageRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Override
    @Transactional
    public Image addImage(ImageDto imageDto, String userId, Long teamId){
        Image image = imageDto.toEntity();
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);

        image.setTeam(team.get());
        image.setUser(user.get());
        return imageRepository.save(image);
    }

    @Override
    public List<ImageDto> getImageListById(Long teamId) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();
        List<Image> images = team.getImages();
        for(Image image : images){
            ImageDto imageDto = new ImageDto(image);
            imageDtoList.add(imageDto);
        }
        return imageDtoList;
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
