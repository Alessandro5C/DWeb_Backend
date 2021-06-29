package com.neoadventura.controllers;

import com.neoadventura.dtos.CrReviewDto;
import com.neoadventura.dtos.VwReviewDto;
import com.neoadventura.exceptions.NeoAdventuraException;
import com.neoadventura.responses.NeoAdventuraResponse;
import com.neoadventura.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/neo-adventura"+"/v1")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reviews")
    public NeoAdventuraResponse<VwReviewDto> createReview(@RequestBody CrReviewDto crReviewDto)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK),
                "OK", reviewService.CreateReview(crReviewDto));
    }

    /*
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/{reviewId}")
    public NeoAdventuraResponse<UsuarioDto> getUsuarioById(@PathVariable Long reviewId)
            throws NeoAdventuraException {
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarioById(reviewId));
    }
    */

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews")
    public NeoAdventuraResponse<List<VwReviewDto>> getReviews()
            throws NeoAdventuraException{
        return new NeoAdventuraResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                reviewService.getReviews());
    }
}
