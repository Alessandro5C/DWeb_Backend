package com.neoadventura.services.impl;

import com.neoadventura.dtos.CrReviewDto;
import com.neoadventura.dtos.VwReviewDto;
import com.neoadventura.entities.*;
import com.neoadventura.exceptions.InternalServerErrorException;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.repositories.*;
import com.neoadventura.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    private static final ModelMapper modelmapper= new ModelMapper();

    @Override
    public List<VwReviewDto> getReviews() throws NeoAdventuraException {
        List<Review> reviewsEntity=reviewRepository.findAll();
        List<VwReviewDto> vwReviewDtos = reviewsEntity.stream().map(x->modelmapper.map(x, VwReviewDto.class))
                .collect(Collectors.toList());
        for (int i = 0; i< vwReviewDtos.size(); ++i) {
            vwReviewDtos.get(i).setServicio_id(reviewsEntity.get(i).getId().getServicioId());
            vwReviewDtos.get(i).setUsuario_id(reviewsEntity.get(i).getId().getUsuarioId());
        }
        return vwReviewDtos;
    }

    @Transactional
    @Override
    public VwReviewDto CreateReview(CrReviewDto crReviewDto) throws NeoAdventuraException {
        Servicio servicio = getServicioEntity(crReviewDto.getServicio_id());

        Usuario usuario = getUsuarioEntity(crReviewDto.getUsuario_id());


        Review review=new Review(
                new ReviewKey(
                        crReviewDto.getServicio_id(),
                        crReviewDto.getUsuario_id()
                ),
                usuario,
                servicio,
                crReviewDto.getScore(),
                crReviewDto.getDescription(),
                crReviewDto.getReported()
        );

        try {
            review=reviewRepository.save(review);
        }catch (Exception ex) {
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }
        return modelmapper.map(getReviewEntity(review.getUsuario().getId(), review.getServicio().getId()), VwReviewDto.class);
    }

    private Usuario getUsuarioEntity(Long id) throws NeoAdventuraException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "USUARIO_NOTFOUND-404"));
    }

    private Servicio getServicioEntity(Long id) throws NeoAdventuraException {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NOTFOUND-404", "SERVICIO_NOTFOUND-404"));
    }

    private Review getReviewEntity(Long usuarioId, Long servicioId) throws NeoAdventuraException{
        return reviewRepository.findByUsuarioAndServicio(getUsuarioEntity(usuarioId), getServicioEntity(servicioId))
                .orElseThrow(()-> new NotFoundException("NOTFOUND-404","REVIEW_NOTFOUND-404"));
    }
}
