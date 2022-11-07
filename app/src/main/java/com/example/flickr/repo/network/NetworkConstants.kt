package com.example.flickr.repo.network

import com.example.flickr.repo.network.models.PhotoResponse
import kotlin.reflect.full.createInstance

object NetworkConstants {

    sealed class APIMethod {

        sealed class PhotosAPIMethod : APIMethod() {
            companion object {
                //https://stackoverflow.com/questions/44784328/how-to-obtain-all-subclasses-of-a-given-sealed-class
                @JvmStatic
                val values =
                    PhotosAPIMethod::class.sealedSubclasses.map { it.createInstance().toString() }
            }

            class Recent() : PhotosAPIMethod() {
                override fun toString() = "flickr.photos.getRecent"
            }

            //object Recent : PhotosAPIMethod() { override fun toString() = "flickr.photos.getRecent" } // had to convert this to class because of: it.createInstance().toString()
            class Search() : PhotosAPIMethod() {
                override fun toString() = "flickr.photos.search"
            }
        }

        sealed class PeopleAPIMethod : APIMethod() {
            object FindByUsername : PeopleAPIMethod() {
                override fun toString() = "flickr.people.findByUsername"
            }
        }
    }

    /*enum class PhotosAPIMethod {
        Recent { override fun toString() = "flickr.photos.getRecent" },
        Search { override fun toString() = "flickr.photos.search" };
    }*/


    enum class TagMode {
        Any {
            override fun toString() = "any"
        },
        All {
            override fun toString() = "all"
        };
    }

    const val tagsDelimiter = " "
    const val tagsJoiner = ","
    const val pageSize = 100

    //todo, put extension functions below into seprate file, unit test them and move whole networking/data stuff to its own module (data)
    val PhotoResponse.photoUrl get() = "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}.jpg"
    val PhotoResponse.ownerProfileUrl get() = "https://farm${farm}.staticflickr.com/${server}/buddyicons/${owner}.jpg"

}