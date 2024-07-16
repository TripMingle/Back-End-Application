package com.example.tripmingle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
	Optional<Posting> findByIdAndIsDeletedFalse(Long postingId);

	Page<Posting> findAllByCountryAndPostingType(String country, PostingType postingType, Pageable pageable);

	@Query("select p from Posting p where p.postingType = :postingType and lower(p.title) like %:keyword% or lower(p.content) like %:keyword% order by p.createdAt desc")
	Page<Posting> findAllBySearching(String keyword, PostingType postingType, Pageable pageable);

	List<Posting> findTop4ByCountryAndPostingTypeOrderByCreatedAtDesc(String country, PostingType postingType);

	@Query("select p from Posting p where p.country = :country and p.postingType = :postingType order by p.likeCount desc, p.createdAt desc")
	Page<Posting> findAllByCountryAndPostingTypeOrderByLikeCountAndCreatedAt(String country, PostingType postingType,
		Pageable pageable);
}
