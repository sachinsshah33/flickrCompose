# Flickr Compose Android App #



## 3rd party libraries used ##

Retrofit - Networking
Moshi - Model parsing/JSON-to-string
Glide - Image caching
Hilt - DI
Mockk - testing

- - - -

## Thought process ##

https://www.flickr.com/services/api/
Key:
7621cc4cb5714bd49a2ac518c04535e6
Secret:
f54e90f73d6be8bb

https://github.com/yoavgray/flickr-kotlin
https://www.flickr.com/services/api/flickr.photos.getPopular.html
https://www.flickr.com/services/api/flickr.photos.getRecent.html
https://www.flickr.com/services/api/flickr.photos.search.html
https://www.flickr.com/services/api/flickr.people.findByUsername.html

https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request



https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-4-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-navigation#7

https://medium.com/huawei-developers/what-is-paging3-mvvm-flow-databinding-hilt-d4fe6b1b11ec
https://stackoverflow.com/questions/68483347/android-mvi-using-stateflow-and-paging-3

https://stackoverflow.com/questions/48020377/livedata-update-on-object-field-change

https://github.com/material-components/material-components-android/blob/master/docs/components/Chip.md#input-chip

https://foso.github.io/Jetpack-Compose-Playground/material/badgedbox/
https://www.youtube.com/watch?v=3wheqEl80XI

https://proandroiddev.com/infinite-lists-with-paging-3-in-jetpack-compose-b095533aefe6
https://www.simplifiedcoding.net/pagination-in-jetpack-compose/
https://stackoverflow.com/questions/72537949/jetpack-compose-lazy-column-insertion-and-deletion-animations-with-multiple-item

https://github.com/2307vivek/PagingCompose-Sample

https://stackoverflow.com/questions/67121433/how-to-pass-object-in-navigation-in-jetpack-compose

- - - -

## If I spent more time I'd ##

Figure out how to write some passing and meaningful tests...
Unit test Retrofit URLs
Test some Compose UI
Fix existing ViewModel test and add more (Paging, Error states)

Fix the UI (Compose Navigation Arguments)...
Fix LiveData/MutableStates not refreshing the UI or refreshing the UI incorrectly (i.e. re-composing whole AppBar when not needed)
Add the missing UI to the new screens (mostly a copy of whats already existing)
Make UI much better

Refactor code

Better error handling, loading spinners, etc

Add some animation (photo from list transition to PhotoDetails screen)

Split app into modules (Core, Data, Presentation)