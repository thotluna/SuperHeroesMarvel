package ve.com.teeac.mynewapplication.domain.use_cases

import javax.inject.Inject
import ve.com.teeac.mynewapplication.di.ComicRepository
import ve.com.teeac.mynewapplication.di.EventRepository
import ve.com.teeac.mynewapplication.di.SeriesRepository
import ve.com.teeac.mynewapplication.domain.repositories.ItemsRepository

class GetItemsComics @Inject constructor(
    @ComicRepository private val repository: ItemsRepository
) : GetItems(repository)

class GetItemsEvents @Inject constructor(
    @EventRepository private val repository: ItemsRepository
) : GetItems(repository)

class GetItemsSeries @Inject constructor(
    @SeriesRepository private val repository: ItemsRepository
) : GetItems(repository)
