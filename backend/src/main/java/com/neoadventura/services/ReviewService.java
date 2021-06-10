package com.neoadventura.services;

import com.neoadventura.dtos.CrReviewDto;
import com.neoadventura.dtos.VwReviewDto;
import com.neoadventura.exceptions.NeoAdventuraException;

import java.util.List;

public interface ReviewService {

    List<VwReviewDto> getReviews() throws NeoAdventuraException;
    VwReviewDto CreateReview(CrReviewDto crReviewDto) throws NeoAdventuraException;
}
