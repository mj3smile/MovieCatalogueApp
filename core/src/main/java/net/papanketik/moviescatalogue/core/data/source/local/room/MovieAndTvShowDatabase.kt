package net.papanketik.moviescatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import net.papanketik.moviescatalogue.core.data.source.local.entity.MovieAndTvShowEntity

@Database(entities = [MovieAndTvShowEntity::class], version = 1)
abstract class MovieAndTvShowDatabase: RoomDatabase() {
    abstract fun movieAndTvDao(): MovieAndTvDao
}