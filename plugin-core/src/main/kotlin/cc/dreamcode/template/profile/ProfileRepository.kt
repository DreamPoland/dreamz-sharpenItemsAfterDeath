package cc.dreamcode.template.profile

import eu.okaeri.persistence.repository.DocumentRepository
import eu.okaeri.persistence.repository.annotation.DocumentCollection
import java.util.*

@DocumentCollection(path = "profile", keyLength = 36)
interface ProfileRepository : DocumentRepository<UUID, Profile> {

    fun findOrCreate(uuid: UUID, profileName: String?): Profile {

        val profile = this.findOrCreateByPath(uuid)
        if (profile != null && profileName != null) {
            profile.name = profileName
        }

        return profile
    }
}