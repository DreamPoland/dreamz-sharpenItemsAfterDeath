package cc.dreamcode.template.profile

import eu.okaeri.persistence.document.Document

data class Profile(
    var name: String
) : Document()