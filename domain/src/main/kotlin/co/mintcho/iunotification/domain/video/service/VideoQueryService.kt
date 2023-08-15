package co.mintcho.iunotification.domain.video.service

import co.mintcho.iunotification.domain.video.model.VideoDto
import co.mintcho.iunotification.domain.video.repository.VideoRepository
import org.springframework.stereotype.Service

@Service
class VideoQueryService(private val videoRepository: VideoRepository) {
    fun getAll(): List<VideoDto> {
        return videoRepository.getAll()
    }
}
