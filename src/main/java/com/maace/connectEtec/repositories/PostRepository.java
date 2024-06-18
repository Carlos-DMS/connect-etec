package com.maace.connectEtec.repositories;

import com.maace.connectEtec.models.EnumTag;
import com.maace.connectEtec.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostModel, UUID> {
    List<PostModel> findByTag (EnumTag tag);
}
