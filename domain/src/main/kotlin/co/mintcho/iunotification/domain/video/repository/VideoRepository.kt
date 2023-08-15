package co.mintcho.iunotification.domain.video.repository

import co.mintcho.iunotification.domain.video.model.VideoDto

interface VideoRepository {
    fun getAll(): List<VideoDto>
}
