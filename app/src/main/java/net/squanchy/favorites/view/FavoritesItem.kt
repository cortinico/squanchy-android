package net.squanchy.favorites.view

import net.squanchy.schedule.domain.view.Event
import org.joda.time.LocalDate

sealed class FavoritesItem {

    abstract val id: Long

    abstract val type: Type

    data class Header(val date: LocalDate) : FavoritesItem() {

        override val id = date.toDateTimeAtCurrentTime().millis // We're relatively sure this won't clash with event IDs

        override val type = Type.HEADER
    }

    data class Favorite(val event: Event) : FavoritesItem() {

        override val id = event.numericId

        override val type = Type.FAVOURITE
    }

    enum class Type {
        HEADER,
        FAVOURITE
    }
}
