/*
 * Copyright (C) 2020 Ivan Molto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ivanmolto.music.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.ivanmolto.music.api.TunesService
import net.ivanmolto.music.db.MusicDb
import net.ivanmolto.music.db.SongDao
import net.ivanmolto.music.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideTunesService(): TunesService {
        return Retrofit.Builder()
            .baseUrl(TunesService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(TunesService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): MusicDb {
        return Room
            .databaseBuilder(app, MusicDb::class.java, "music.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSongDao(db: MusicDb): SongDao {
        return db.songDao()
    }
}
